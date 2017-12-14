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

import static com.appsters.igit.taxicity.R.id.contactRecieverRecycler;

public class ContactsRecieved extends AppCompatActivity {

    ArrayList<Contacts> contacts;
    RecyclerView contRecycler;
    RelativeLayout layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_recieved);
        layout = (RelativeLayout) findViewById(R.id.hidden3);
        layout.setVisibility(View.VISIBLE);
        contacts=new ArrayList<>();
        anonymous();

    }
    private void anonymous()
    {
        SharedPreferences pref1=getSharedPreferences("Account",MODE_PRIVATE);
        SharedPreferences pref2=getSharedPreferences("GroupMode",MODE_PRIVATE);
        HashMap<String,String> postData=new HashMap<>();
        postData.put("ac_name",pref1.getString("ac_name","Error"));
        postData.put("ac_date",pref2.getString("date_var","Error"));
        PostResponseAsyncTask task=new PostResponseAsyncTask(ContactsRecieved.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.e("contact wala", s);
                try {

                    JSONObject jsono = new JSONObject(s);
                    JSONArray jArray = jsono.getJSONArray("response");
                    if (jArray.length() == 0) layout.setVisibility(View.VISIBLE);
                    else {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject object = jArray.getJSONObject(i);
                            contacts.add(new Contacts(object.getString("acceptor"), object.getString("date_var"), object.getString("phoneno"), object.getString("picurl")));
                        }
                        contRecycler = (RecyclerView) findViewById(contactRecieverRecycler);
                        contRecycler.setLayoutManager(new LinearLayoutManager(ContactsRecieved.this));
                        contRecycler.setAdapter(new ContactAdapter(contacts));
                        layout.setVisibility(View.INVISIBLE);
                    }
                }
                catch (ParseException |JSONException e)
                {
                    Toast.makeText(ContactsRecieved.this, "Some Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute("http://taxicity.esy.es/contactsView.php");
        task.setExceptionHandler(new ExceptionHandler() {
            @Override
            public void handleException(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
