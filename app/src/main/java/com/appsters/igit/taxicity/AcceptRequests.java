package com.appsters.igit.taxicity;

import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AcceptRequests extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Accept> accepts;
    private static AcceptRequests instance;
    public static AcceptRequests getInstance()
    {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_requests);
        accepts=new ArrayList<>();
        instance=AcceptRequests.this;
        recyclerView=(RecyclerView)findViewById(R.id.acceptRequestRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(AcceptRequests.this));
        AR_function();

    }
    private void AR_function()
    {
        SharedPreferences pref1=getSharedPreferences("Account",MODE_PRIVATE);
        SharedPreferences pref2=getSharedPreferences("GroupMode",MODE_PRIVATE);
        HashMap<String,String> postData=new HashMap<>();
        postData.put("ac_name",pref1.getString("ac_name",null));
        postData.put("ac_date",pref2.getString("date_var",null));
        PostResponseAsyncTask task=new PostResponseAsyncTask(AcceptRequests.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("accept Error :",s);
                try {
                    JSONObject jsono = new JSONObject(s);
                    JSONArray jArray = jsono.getJSONArray("response");
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.hidden2);
                    if (jArray.length()==0) layout.setVisibility(View.VISIBLE);
                    else {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject object = jArray.getJSONObject(i);
                            accepts.add(new Accept(object.getString("sender"), object.getString("reciever"), object.getString("date_var"),
                                    object.getString("source"), object.getString("destination"), object.getString("guests"), object.getString("train"), object.getString("picurl")));
                        }
                        layout.setVisibility(View.INVISIBLE);
                        adapter=new AcceptAdapter(accepts);
                        recyclerView.setAdapter(adapter);
                    }

                }
                catch (ParseException |JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(AcceptRequests.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute("http://taxicity.esy.es/acceptorVision.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void acceptConfirm(String sender)
    {
        SharedPreferences pref1=getSharedPreferences("Account",MODE_PRIVATE);
        SharedPreferences pref2=getSharedPreferences("GroupMode",MODE_PRIVATE);
        HashMap<String,String> postData=new HashMap<>();
        postData.put("sender",sender);
        postData.put("acceptor",pref1.getString("ac_name","Error"));
        postData.put("picurl",pref1.getString("ac_pic","Error"));
        postData.put("date_var",pref2.getString("date_var","Error"));
        postData.put("phoneno",pref1.getString("ac_phoneno","Error"));
        PostResponseAsyncTask task=new PostResponseAsyncTask(AcceptRequests.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("acceptor wala", s);
                if (!s.contains("Success"))
                    Toast.makeText(AcceptRequests.this, "Sorry Some error occured", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AcceptRequests.this, "Contact sent succesfully", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute("http://taxicity.esy.es/contactsInsertion.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
