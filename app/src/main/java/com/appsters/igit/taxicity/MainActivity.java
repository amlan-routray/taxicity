package com.appsters.igit.taxicity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;

import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends FragmentActivity {

    ScreenSlidePager adapter;
    ViewPager layout;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        circleIndicator=(CircleIndicator)findViewById(R.id.cIindicator);
        adapter=new ScreenSlidePager(getSupportFragmentManager());
        layout=(ViewPager)findViewById(R.id.intro_pager);
        layout.setAdapter(adapter);
        circleIndicator.setViewPager(layout);
        SharedPreferences pref2 = getSharedPreferences("App_Use" , MODE_PRIVATE);
        boolean condition=pref2.getBoolean(getString(R.string.FIRST_USE),true);
        if(!condition)
        {
            Intent launchNextActivty;
            launchNextActivty=new Intent(getBaseContext(),DestinationPicker.class);
            launchNextActivty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchNextActivty.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            launchNextActivty.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(launchNextActivty);
        }
    }
}
