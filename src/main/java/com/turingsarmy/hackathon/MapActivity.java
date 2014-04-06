package com.turingsarmy.hackathon;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;



public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapres);

        // Get a handle to the Map Fragment
        GoogleMap ucsc_map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.ucsc_map)).getMap();

        LatLng ucscruz = new LatLng(36.989, -122.06);

        LatLng oakesLtLn = new LatLng(36.989, -122.0633);
        LatLng eightLtLn = new LatLng(36.9914, -122.0645);

        ucsc_map.setMyLocationEnabled(true);
        ucsc_map.moveCamera(CameraUpdateFactory.newLatLngZoom(ucscruz, 14));

        CircleOptions oakesOptions = new CircleOptions()
                .center(/*new LatLng(36.9893, -122.063)*/oakesLtLn)
                .radius(GPSTracker.RAD_DIST); // In meters
        CircleOptions eightOptions = new CircleOptions()
                .center(/*new LatLng(36.9893, -122.063)*/eightLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle oakesCircle = ucsc_map.addCircle(eightOptions);
        Circle eightCircle = ucsc_map.addCircle(oakesOptions);

        /*ucsc_map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(ucscruz));*/
    }
}