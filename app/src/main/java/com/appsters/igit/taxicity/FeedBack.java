package com.appsters.igit.taxicity;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;

public class FeedBack extends AppCompatActivity {

    com.like.LikeButton lButt1,lButt2,lButt3;
    FloatingActionButton fb_fab;
    Button submit;
    EditText feedbackComment;
    static String likeValue;
    RecyclerView feedbackRecycler;
    public static ArrayList<Feedbackrow> feedbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        feedbacks=new ArrayList<>();
        feedbackFetcher();
        lButt1= (LikeButton) findViewById(R.id.notHappy);
        lButt2= (LikeButton) findViewById(R.id.likedIt);
        lButt3= (LikeButton) findViewById(R.id.lovedIt);
        lButt1.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeHandler("nothappy",lButt2,lButt3);
                Toast.makeText(FeedBack.this, "Not Happy", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        }) ;
        lButt2.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeHandler("likedit",lButt1,lButt3);
                Toast.makeText(FeedBack.this, "Liked It", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        }) ;
        lButt3.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeHandler("lovedit",lButt1,lButt2);
                Toast.makeText(FeedBack.this, "Loved It", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void unLiked(LikeButton likeButton) {

            }
        }) ;
        fb_fab= (FloatingActionButton) findViewById(R.id.fb_fab);

        fb_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getOpenFacebookIntent(getApplicationContext()));
            }
        });
        submit=(Button)findViewById(R.id.submit_btn);
        feedbackComment=(EditText)findViewById(R.id.feedbackComment);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (feedbackComment.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(FeedBack.this, "Please write a feedback", Toast.LENGTH_SHORT).show();
                else {
                        feedbackInsertion(feedbackComment.getText().toString().trim());
                }
            }
        });

    }
    private void feedbackInsertion(String comment)
    {
        String tag1=getString(R.string.AC_NAME);
        String tag2=getString(R.string.AC_PIC);
        HashMap<String,String> postData=new HashMap<>();
        postData.put("ftext",getSharedPreferences("Account",MODE_PRIVATE).getString(tag1,"Error"));
        postData.put("flike",likeValue);
        postData.put("fcomment",comment);
        postData.put("fimage",getSharedPreferences("Account",MODE_PRIVATE).getString(tag2,"Error"));
        PostResponseAsyncTask task=new PostResponseAsyncTask(FeedBack.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("Error :",s);
                if (s.equalsIgnoreCase("Success")) {
                    Toast.makeText(FeedBack.this, "Thank You", Toast.LENGTH_SHORT).show();
                    startActivity(getIntent());
                }
                else Toast.makeText(FeedBack.this, "Sorry try again!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute("http://taxicity.esy.es/feedbackInsertion.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                e.printStackTrace();
            }
        });
    }
    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/675979709182594"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/officialappsters"));
        }
    }
    private void feedbackFetcher()
    {
        PostResponseAsyncTask task=new PostResponseAsyncTask(FeedBack.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("Json Error :",s);
                if (s.equalsIgnoreCase("error")) {
                    Toast.makeText(FeedBack.this, "Sorry try again!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    try {
                        JSONObject jsono = new JSONObject(s);
                        JSONArray jArray = jsono.getJSONArray("response");
                        for (int i = 0; i < jArray.length(); i++) {
                                JSONObject object = jArray.getJSONObject(i);
                                feedbacks.add(new Feedbackrow(object.getString("name"), object.getString("picurl"), object.getString("rating"), object.getString("comment")));
                        }
                        feedbackRecycler=(RecyclerView)findViewById(R.id.feedbackRecycler);
                        feedbackRecycler.setNestedScrollingEnabled(false);
                        feedbackRecycler.setLayoutManager(new LinearLayoutManager(FeedBack.this));
                        feedbackRecycler.setAdapter(new FeedbackAdapter(feedbacks));
                    }
                    catch (ParseException|JSONException e)
                    {
                        Toast.makeText(FeedBack.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        task.execute("http://taxicity.esy.es/feedback.php");
        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Log.e("IO error", e.toString());
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Log.e("URL error", e.toString());
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Log.e("Protocol error", e.toString());
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Log.e("Encoding exception", e.toString());
            }
        });
    }
    private void likeHandler(String token,LikeButton l2,LikeButton l3)
    {
        likeValue=token;
        l2.setLiked(false);
        l3.setLiked(false);
    }

    @Override
    public void onBackPressed() {
        Intent launchNextActivty;
        launchNextActivty=new Intent(getBaseContext(),DestinationPicker.class);
        launchNextActivty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launchNextActivty.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launchNextActivty.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(launchNextActivty);
    }
}
