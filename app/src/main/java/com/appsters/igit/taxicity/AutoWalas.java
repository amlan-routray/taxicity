package com.appsters.igit.taxicity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AutoWalas extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_walas);
        final String autowallas[]={"Ranjan", "Gagan", "Babuli", "Kumar", "Kuna", "Ranjan", "Abhi", "Ainthu", "Pari", "Dhira", "Naresh", "Rinku", "Praveen", "Pintu","Kishore","Bishnu","Kalia","Lipun","Jitu","Pintu","Ajaya","Hari","Naresh", "Prakash","Sidheswar","Anand", "Babuli","Santanu","Chita"};
        final String autophone[]={ "9861175798", "9778307395", "9583721351", "9861849974","9938153258","9861183119","9861182534","9583787228","9439669534","9437601130","9776080900","9777389597","9583179574","9692881110","8763073394","9583701559","8763101084","9556582609","9668394028","9556509234","9438626639","8455899691","9776919930","9437548481","9861246499","9583269023","9938108479","7873269370","9777791603"};

        listView= (ListView) findViewById(R.id.auto_listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,autowallas);
        listView.setAdapter(adapter);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel: "+autophone[i]));
               startActivity(intent);

           }
       });

    }
}
