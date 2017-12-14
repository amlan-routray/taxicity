package com.appsters.igit.taxicity;

import android.animation.Animator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import io.codetail.animation.ViewAnimationUtils;

public class DestinationPicker extends AppCompatActivity {

    Button trigger_arrival,trigger_departure,departureButton,arrivalButton,trigger_cancel_trip;
    RelativeLayout departure_layout,arrival_layout;
    LinearLayout selector_layout,mRevealView;
    ImageView fab1,fab2,fab3,fab4,fab5,fab6;
    LinearLayout revealLayout;
    TextView eview1,eview2;
    Spinner view1,view2,view3,view4;
    RadioGroup rview1,rview2,rview3,rview4;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date,date2;
    String source="",destination="",train="",guests="",date_var="";
    private Boolean exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_picker);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final String[] HOSTELS = new String[] {
               "Select Hostel", "Akash Bhawan", "Aryabhatta Bhawan", "Bhaskar Bhawan","Brahmos Bhawan", "Surya Bhawan", "Prithwi Bhawan","Rohini Bhawan"
        };
        final String[] TRAINS = new String[] {
                "Select Train", "18418/Bhubaneswar - Rourkela Rajya Rani Express","18407/Puri-Sai Nagar Shirdi Weekly Express","18421/Puri - Ajmer Bi-Weekly Express (PT)","18417/Rourkela - Bhubaneswar Rajya Rani Express","18426/Durg - Puri Express","22803/Howrah - Sambalpur (Weekly) SF Express","12879/Mumbai LTT - Bhubaneswar SF Express (PT)","22865/Mumbai LTT - Puri (Weekly) SF Express","12831/Dhanbad - Bhubaneswar Garib Rath Express","58421/Angul - Puri Fast Passenger (UnReserved)","18474/Jodhpur - Puri Express (PT)","14709/Bikaner - Puri Express","22909/Valsad - Puri SF Express","12893/Bhubaneswar - Balangir Express","18303/Sambalpur - Puri Intercity Express","12880/Bhubaneswar - Mumbai LTT SF Express","22910/Puri - Valsad SF Express","22866/Puri - Mumbai LTT (Weekly) SF Express","14710/Puri - Bikaner Express","18105/Rourkela - Bhubaneswar Intercity Express","58132/Puri - Rourkela Passenger (UnReserved)","12993/Gandhidham - Puri (Weekly) SF Express","22828/Surat - Puri Weekly SF Express","22806/Anand Vihar - Bhubaneswar Weekly SF Express","18422/Ajmer - Puri Bi-Weekly Express","22805/Bhubaneswar - Anand Vihar Weekly SF Express","18106/Bhubaneswar - Rourkela Intercity Express","58131/Rourkela - Puri Passenger (UnReserved)","18408/Sai Nagar Shirdi-Puri Weekly Express"," 18473/Puri - Jodhpur Express (PT)","18304/Puri - Sambalpur Intercity Express","12894/Balangir - Bhubaneswar Express","58422/Puri - Angul Fast Passenger (UnReserved)","12832/Bhubaneswar - Dhanbad Garib Rath Express","12994/Puri-Gandhidham Weekly SF Express","22827/Puri - Surat Weekly SF Express"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, HOSTELS);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, TRAINS);

        departureButton= (Button) findViewById(R.id.departureButton);
        arrivalButton= (Button) findViewById(R.id.arrivalButton);
        trigger_cancel_trip=(Button)findViewById(R.id.trigger_cancel_trip);
        rview1=(RadioGroup)findViewById(R.id.rview1);
        rview2=(RadioGroup)findViewById(R.id.rview2);
        rview3=(RadioGroup)findViewById(R.id.rview3);
        rview4=(RadioGroup)findViewById(R.id.rview4);
        view1 = (Spinner)
                findViewById(R.id.spinner1_1);
        view2 = (Spinner)
                findViewById(R.id.spinner1_2);
        view3 = (Spinner)
                findViewById(R.id.spinner2_1);
        view4 = (Spinner)
                findViewById(R.id.spinner2_2);
        assert view1 != null;
        view1.setAdapter(adapter);
        assert view4 != null;
        view2.setAdapter(adapter2);
        assert view3 != null;
        view3.setAdapter(adapter);
        assert view2 != null;
        view4.setAdapter(adapter2);
        eview1=(TextView)findViewById(R.id.eview1);
        eview2=(TextView)findViewById(R.id.eview2);
        fab1=(ImageView) findViewById(R.id.profile);
        fab2=(ImageView) findViewById(R.id.history);
        fab3=(ImageView) findViewById(R.id.about);
        fab4=(ImageView) findViewById(R.id.notifier);
        fab5=(ImageView)findViewById(R.id.feedback);
        fab6=(ImageView)findViewById(R.id.share);
        trigger_cancel_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> postData=new HashMap<String, String>();
                postData.put("name",getSharedPreferences("GroupMode",MODE_PRIVATE).getString("name","error"));
                postData.put("date_var",getSharedPreferences("GroupMode",MODE_PRIVATE).getString("date_var","error"));
                PostResponseAsyncTask task=new PostResponseAsyncTask(DestinationPicker.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        Log.e("Any Error",s);
                        if (s.equalsIgnoreCase("Success"))
                        {
                            getSharedPreferences("GroupMode",MODE_PRIVATE).edit().clear().apply();
                            Toast.makeText(DestinationPicker.this, "Trip request succesfully removed", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(DestinationPicker.this, "Sorry some error occured", Toast.LENGTH_SHORT).show();
                    }
                });
                task.execute("http://taxicity.esy.es/canceltrip.php");
                task.setExceptionHandler(new ExceptionHandler() {
                    @Override
                    public void handleException(Exception e) {
                        e.printStackTrace();
                    }
                });
                getSharedPreferences("GroupMode",MODE_PRIVATE).edit().clear().apply();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(DestinationPicker.this, Sherlock.class);
                    startActivity(i);
                }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetConnection.isEnabled(DestinationPicker.this)) {
                    Intent i = new Intent(DestinationPicker.this, Navigation.class);
                    startActivity(i);
                }
                else Toast.makeText(DestinationPicker.this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DestinationPicker.this,AboutActivity.class);
                startActivity(i);
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SharedPreferences preferences = getSharedPreferences("GroupMode", MODE_PRIVATE);
            if (preferences.getString("date_var", "chutiya").equalsIgnoreCase("chutiya"))
                Toast.makeText(DestinationPicker.this, "Post a request first", Toast.LENGTH_SHORT).show();
            else {
                if (InternetConnection.isEnabled(DestinationPicker.this)) {
                    startActivity(new Intent(getBaseContext(), Notifier.class));
                }
                else Toast.makeText(DestinationPicker.this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            }
            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetConnection.isEnabled(DestinationPicker.this)) startActivity(new Intent(getBaseContext(),FeedBack.class));
                else Toast.makeText(DestinationPicker.this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
            }
        });
        fab6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.appsters.igit.taxicity&hl=en");
                startActivity(Intent.createChooser(sharingIntent,"All my friends are in:"));
            }
        });

        trigger_arrival=(Button)findViewById(R.id.trigger_arrival);
        trigger_departure=(Button)findViewById(R.id.trigger_departure);
        selector_layout=(LinearLayout)findViewById(R.id.selector_layout);
        arrival_layout=(RelativeLayout)findViewById(R.id.arrival_layout);
        departure_layout=(RelativeLayout)findViewById(R.id.departure_layout);
        trigger_arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector_layout.setVisibility(View.INVISIBLE);
                arrival_layout.setVisibility(View.VISIBLE);
            }
        });
        trigger_departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector_layout.setVisibility(View.INVISIBLE);
                departure_layout.setVisibility(View.VISIBLE);
            }
        });
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        mRevealView.setVisibility(View.INVISIBLE);
        departureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validator(2))
                {
                    AppCompatRadioButton selectedRadioButton = (AppCompatRadioButton) findViewById(rview3.getCheckedRadioButtonId());
                    destination=selectedRadioButton.getText().toString();
                    AppCompatRadioButton selectedGuestsRadioButton = (AppCompatRadioButton) findViewById(rview4.getCheckedRadioButtonId());
                    guests=String.valueOf(6-Integer.parseInt(selectedGuestsRadioButton.getText().toString().trim()));
                    date_var=eview2.getText().toString();
                    source=view3.getSelectedItem().toString();
                    train=view4.getSelectedItem().toString();
                    Log.e("Values: ",destination+"  "+guests+" "+date_var+" "+source+" "+train);
                    SharedPreferences preferences=getSharedPreferences("Account",MODE_PRIVATE);
                    HashMap<String, String> postData = new HashMap<>();
                    postData.put("source",source);
                    postData.put("destination",destination);
                    postData.put("date_var",date_var);
                    postData.put("train",train);
                    postData.put("guests",guests);
                    postData.put("picurl",preferences.getString("ac_pic","fuck"));
                    postData.put("name",preferences.getString("ac_name",null));
                    SharedPreferences group=getSharedPreferences("GroupMode",MODE_PRIVATE);
                    SharedPreferences.Editor gedit=group.edit();
                    gedit.putString("source",source);
                    gedit.putString("destination",destination);
                    gedit.putString("date_var",date_var);
                    gedit.putString("train",train);
                    gedit.putString("guests",guests);
                    gedit.putString("name",preferences.getString("ac_name",null));
                    gedit.apply();
                    Log.e("shared error",group.getString("destination",null));
                    requestSender(postData);
                }
            }
        });
        arrivalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validator(1))
                {
                    AppCompatRadioButton selectedRadioButton = (AppCompatRadioButton) findViewById(rview1.getCheckedRadioButtonId());
                    source=selectedRadioButton.getText().toString();
                    AppCompatRadioButton selectedGuestsRadioButton = (AppCompatRadioButton) findViewById(rview2.getCheckedRadioButtonId());
                    guests=String.valueOf(6-Integer.parseInt(selectedGuestsRadioButton.getText().toString().trim()));
                    date_var=eview1.getText().toString();
                    destination=view1.getSelectedItem().toString();
                    Log.e("no..",guests);
                    train=view2.getSelectedItem().toString();
                    SharedPreferences preferences=getSharedPreferences("Account",MODE_PRIVATE);
                    HashMap<String, String> postData = new HashMap<>();
                    postData.put("source",source);
                    postData.put("destination",destination);
                    postData.put("date_var",date_var);
                    postData.put("train",train);
                    postData.put("guests",guests);
                    postData.put("name",preferences.getString("ac_name",null));
                    postData.put("picurl",preferences.getString("ac_pic","snjdnj"));
                    requestSender(postData);
                    SharedPreferences group=getSharedPreferences("GroupMode",MODE_PRIVATE);
                    SharedPreferences.Editor gedit=group.edit();
                    gedit.putString("source",source);
                    gedit.putString("destination",destination);
                    gedit.putString("date_var",date_var);
                    gedit.putString("train",train);
                    gedit.putString("guests",guests);
                    gedit.putString("name",preferences.getString("ac_name",null));
                    gedit.apply();
                }
            }
        });
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.before(System.currentTimeMillis());
                updateLabel();
            }
        };
        date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.before(System.currentTimeMillis());
                updateLabel2();
            }
        };
        eview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DestinationPicker.this,R.style.EditTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        eview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DestinationPicker.this,R.style.EditTheme, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel()
    {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eview1.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2()
    {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eview2.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            if (selector_layout.getVisibility() == View.INVISIBLE) {
                selector_layout.setVisibility(View.VISIBLE);
                arrival_layout.setVisibility(View.INVISIBLE);
                departure_layout.setVisibility(View.INVISIBLE);
            }
        }
        if (item.getItemId()==R.id.action_auto_no) {
            startActivity(new Intent(getBaseContext(),AutoWalas.class));
        }
            if (item.getItemId()== R.id.action_about_us)
            {
                revealLayout=(LinearLayout)findViewById(R.id.reveal_items);
                if (revealLayout.getVisibility()==View.INVISIBLE)
                {
                    revealLayout.setVisibility(View.VISIBLE);
                    int cx = (revealLayout.getLeft() + revealLayout.getRight()) / 2;
                    int cy = (revealLayout.getTop() + revealLayout.getBottom()) / 2;

                    // get the final radius for the clipping circle
                    int dx = Math.max(cx, revealLayout.getWidth() - cx);
                    int dy = Math.max(cy, revealLayout.getHeight() - cy);
                    float finalRadius = (float) Math.hypot(dx, dy);

                    // Android native animator
                    Animator animator =
                            ViewAnimationUtils.createCircularReveal(revealLayout, cx, cy, 0,finalRadius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(800);
                    animator.start();
                }
                else
                {
                    int cx = (revealLayout.getLeft() + revealLayout.getRight()) / 2;
                    int cy = (revealLayout.getTop() + revealLayout.getBottom()) / 2;

                    // get the final radius for the clipping circle
                    int dx = Math.max(cx, revealLayout.getWidth() - cx);
                    int dy = Math.max(cy, revealLayout.getHeight() - cy);
                    float finalRadius = (float) Math.hypot(dx, dy);

                    // Android native animator
                    Animator animator =
                            ViewAnimationUtils.createCircularReveal(revealLayout, cx, cy, finalRadius,0);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(800);
                    animator.start();
                    animator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            revealLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
                }
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        revealLayout=(LinearLayout)findViewById(R.id.reveal_items);
        int cx = (revealLayout.getLeft() + revealLayout.getRight()) / 2;
        int cy = (revealLayout.getTop() + revealLayout.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, revealLayout.getWidth() - cx);
        int dy = Math.max(cy, revealLayout.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        // Android native animator
        Animator animator =
                ViewAnimationUtils.createCircularReveal(revealLayout, cx, cy, finalRadius,0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(800);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                revealLayout.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.destination_menu,menu);
        return true;
    }



    @Override
    public void onBackPressed()
    {
        if(arrival_layout.getVisibility()==View.VISIBLE || departure_layout.getVisibility()==View.VISIBLE)
        {
            arrival_layout.setVisibility(View.INVISIBLE);
            departure_layout.setVisibility(View.INVISIBLE);
            selector_layout.setVisibility(View.VISIBLE);
        }
        else {
            if (exit) {
                finish();
            } else {
                Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
                exit=true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit=false;
                    }
                },2000);
            }
        }
    }
    private boolean validator(int TYPE)
    {
        if (TYPE==1)
        {
            return !(eview1.getText().toString().trim().equalsIgnoreCase("select date") || rview1.getCheckedRadioButtonId() == -1 || rview2.getCheckedRadioButtonId() == -1 || view1.getSelectedItemPosition() == 0 || view2.getSelectedItemPosition() == 0);
        }
        if (TYPE==2)
        {
            return !(eview2.getText().toString().trim().equalsIgnoreCase("select date") || rview3.getCheckedRadioButtonId() == -1 || rview4.getCheckedRadioButtonId() == -1 || view3.getSelectedItemPosition() == 0 || view4.getSelectedItemPosition() == 0);
        }
        return false;
    }
    public void requestSender(HashMap<String,String> postData)
    {
        PostResponseAsyncTask task=new PostResponseAsyncTask(DestinationPicker.this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                Log.d("server_res:",s);
                    if (s.equalsIgnoreCase("Success")) Snackbar.make(getWindow().getDecorView().getRootView(),"Request Successful",Snackbar.LENGTH_LONG).show();
                    else Snackbar.make(getWindow().getDecorView().getRootView(),"Failed to post request",Snackbar.LENGTH_LONG).show();
            }
        });
        task.execute("http://taxicity.esy.es/requestHandler.php");
    }
}
