package com.appsters.igit.taxicity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class Notifier extends AppCompatActivity {

    public  static ArrayList<Request> requests;
    SharedPreferences preferences;
    private static Notifier instance;
    Button contact;
    Button accept;
    RecyclerView.Adapter adapter;
    RecyclerView showRecycler;

    public static Notifier getInstance()
    {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifier);
        accept= (Button) findViewById(R.id.acc1);
      accept.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(getBaseContext(),AcceptRequests.class));
          }
      });
        contact= (Button) findViewById(R.id.acc2);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),ContactsRecieved.class));
            }
        });
        instance=Notifier.this;
        requests=new ArrayList<>();
        preferences = getSharedPreferences("GroupMode", Context.MODE_PRIVATE);
        Notif();
        showRecycler=(RecyclerView)findViewById(R.id.showRecycler);
        showRecycler.setLayoutManager(new LinearLayoutManager(Notifier.this));

    }
    private void Notif()
    {
        HashMap<String,String> postData=new HashMap<>();
        postData.put("destination",preferences.getString("destination",null));
        postData.put("date_var",preferences.getString("date_var",null));
        postData.put("guests",preferences.getString("guests",null));
        postData.put("name",preferences.getString("name",null));
        PostResponseAsyncTask task=new PostResponseAsyncTask(Notifier.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("error",s);
                try {
                    JSONObject jsono = new JSONObject(s);
                    JSONArray jArray = jsono.getJSONArray("response");
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.hidden1);
                    if (jArray.length() == 0) {
                        layout.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject object = jArray.getJSONObject(i);
                            requests.add(new Request(object.getString("name"), object.getString("source"), object.getString("destination"),
                                    object.getString("guests"), object.getString("train"), object.getString("date_var"), object.getString("picurl")));
                        }
                        adapter = new RequestAdapter(requests);
                        layout.setVisibility(View.INVISIBLE);
                        showRecycler.setAdapter(adapter);
                    }
                }
                catch (ParseException |JSONException e)
                {
                    Toast.makeText(Notifier.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }

            }
        });
        task.execute("http://taxicity.esy.es/requestViewer.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                e.printStackTrace();

            }
        });
    }
    public  void sendRequest(String recieverName)
    {
        SharedPreferences pref1=getSharedPreferences("Account",MODE_PRIVATE);
        SharedPreferences pref2=getSharedPreferences("GroupMode",MODE_PRIVATE);
        HashMap<String,String> postData=new HashMap<>();
        postData.put("sender",pref1.getString("ac_name",null));
        postData.put("picurl",pref1.getString("ac_pic",null));
        postData.put("source",pref2.getString("source",null));
        postData.put("destination",pref2.getString("destination",null));
        postData.put("train",pref2.getString("train",null));
        postData.put("date_var",pref2.getString("date_var",null));
        postData.put("guests",pref2.getString("guests",null));
        postData.put("reciever",recieverName);
        PostResponseAsyncTask task=new PostResponseAsyncTask(Notifier.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("kuch",s);
                if (s.contains("Success"))
                    Toast.makeText(Notifier.this, "Request Succesfully Sent", Toast.LENGTH_SHORT).show();
            }
        });
        task.execute("http://taxicity.esy.es/acceptorInsertion.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
