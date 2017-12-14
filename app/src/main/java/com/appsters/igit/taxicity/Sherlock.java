package com.appsters.igit.taxicity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class Sherlock extends AppCompatActivity{

    EditText phone;
    ImageView button;
    SharedPreferences pref;
    String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sherlock);
            pref=getSharedPreferences("Account",MODE_PRIVATE);
            CircleImageView profilePic=(CircleImageView) findViewById(R.id.profilePic);
            TextView profileName,profileEmail;
            phone=(EditText)findViewById(R.id.noChangerButtonEditText);
            button = (ImageView) findViewById(R.id.noChangerButton);
             button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = phone.getText().toString().trim();
                if (text.length() == 10) phoneNumberUpdater(text);
                else
                    Toast.makeText(Sherlock.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            }
        });
            profileName=(TextView)findViewById(R.id.profileName);
            profileEmail=(TextView)findViewById(R.id.profileEmail);
            String s1,s2,s3,s4;
            s1=getString(R.string.AC_NAME);
            s2=getString(R.string.AC_EMAIL);
            s3=getString(R.string.AC_PHONE);
            s4=getString(R.string.AC_PIC);
            profileName.setText(pref.getString(s1,null));
            profileEmail.setText(pref.getString(s2,null));
            phone.setHint(pref.getString(s3,null));
            Log.d("hint_catch",pref.getString(s4,null));
            Log.d("hint_catch2",pref.getString(s3,"nothing"));
            Picasso.with(getApplicationContext()).load(pref.getString(s4,null)).placeholder(R.mipmap.placeholder).into(profilePic);
        Button logOut=(Button)findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref2 = getSharedPreferences("App_Use" , MODE_PRIVATE);
                SharedPreferences.Editor editor=pref2.edit();
                editor.putBoolean("First_Use",true);
                editor.apply();
                startActivity(new Intent(getBaseContext(),SignInActivity.class));
            }
        });
    }
    private void phoneNumberUpdater(String text)
    {
        no=text;
        String tag=getString(R.string.AC_UID);
        HashMap<String,String> postData=new HashMap<>();
        postData.put("phoneno",text);
        postData.put("uid",getSharedPreferences("Account",MODE_PRIVATE).getString(tag,"null"));
        PostResponseAsyncTask task=new PostResponseAsyncTask(Sherlock.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("phone",s);
                if (s.equalsIgnoreCase("Success")) {
                    Toast.makeText(Sherlock.this, "Updated Succefully!", Toast.LENGTH_SHORT).show();
                    getSharedPreferences("Account",MODE_PRIVATE).edit().putString("ac_phoneno",no).apply();
                    Log.e("Shared :",no);
                    Log.e("Shared 2:",getSharedPreferences("Account",MODE_PRIVATE).getString("ac_phoneno","Error"));
                }
                else Toast.makeText(Sherlock.this, "Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute("http://taxicity.esy.es/updatephone.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
               e.printStackTrace();
            }
        });
    }
}
