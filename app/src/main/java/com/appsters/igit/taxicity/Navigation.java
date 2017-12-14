package com.appsters.igit.taxicity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.appsters.igit.taxicity.R.id.map;

public class Navigation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng talcher_road = new LatLng(20.8868796, 85.208386);
        LatLng talcher_thermal=new LatLng(20.909048, 85.208831);
        LatLng talcher_main=new LatLng(20.9528401, 85.2334301);
        LatLng igit=new LatLng(20.9337629, 85.262832);
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(12);
        mMap.addMarker(new MarkerOptions().position(talcher_road).title("Talcher Road"));
        mMap.addMarker(new MarkerOptions().position(talcher_thermal).title("Talcher Thermal"));
        mMap.addMarker(new MarkerOptions().position(talcher_main).title("Talcher Main"));
        mMap.addMarker(new MarkerOptions().position(igit).title("IGIT Sarang"));
        mMap.addCircle(new CircleOptions().center(igit).fillColor(Color.CYAN).strokeColor(Color.BLUE).strokeWidth(1.0f).radius(500));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(igit));
        mMap.animateCamera(zoom);

    }
}
