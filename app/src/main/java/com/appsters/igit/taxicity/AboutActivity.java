package com.appsters.igit.taxicity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fab_sukalp,fab_amlan,fab_uma,fab_himanshu;
    String url="",url2="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        fab_sukalp= (FloatingActionButton) findViewById(R.id.fab_sukalp);
        fab_amlan= (FloatingActionButton) findViewById(R.id.fab_amlan);
        fab_uma= (FloatingActionButton) findViewById(R.id.fab_uma);
        fab_himanshu= (FloatingActionButton) findViewById(R.id.fab_himanshu);

        fab_sukalp.setOnClickListener(this);
        fab_amlan.setOnClickListener(this);
        fab_uma.setOnClickListener(this);
        fab_himanshu.setOnClickListener(this);

    }

    public static Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri1 = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {

                uri1 = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.fab_sukalp:
                    url2="fb://profile/1141034759313826";
                    url="http://www.facebook.com/shanu1899";
                break;
            case R.id.fab_uma:
                url2="fb://profile/1201345453271442";
                url="http://www.facebook.com/umasnayak.usn";
                break;
            case R.id.fab_amlan:
                url2="fb://profile//1162603183819878";
                url="http://www.facebook.com/amlan.routary";
                break;
            case R.id.fab_himanshu:
                url2="fb://profile/1850087795205244";
                url="http://www.facebook.com/himanshu.samal.370";
                break;
        }
        startActivity(newFacebookIntent(getPackageManager(),url));
    }
}
