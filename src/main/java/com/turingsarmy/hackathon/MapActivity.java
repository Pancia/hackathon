package com.turingsarmy.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import static android.location.Location.distanceBetween;


public class MapActivity extends Activity implements GoogleMap.OnMapClickListener {

    private TextView tappedCircle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapres);

        // Get a handle to the Map Fragment
        GoogleMap ucsc_map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.ucsc_map)).getMap();

        LatLng ucscruz = new LatLng(36.989, -122.06);
        LatLng oakesLtLn = new LatLng(36.9889, -122.06422);
        LatLng eightLtLn = new LatLng(36.9917, -122.0645);
        LatLng porterLtLn = new LatLng(36.995, -122.065);
        LatLng kresgeLtLn = new LatLng(36.9977, -122.066);
        LatLng nineLtLn = new LatLng(37.00251, -122.058);
        LatLng tenLtLn = new LatLng(37.00019, -122.058);
        LatLng crownLtLn = new LatLng(36.9997, -122.05513);
        LatLng crownAptLtLn = new LatLng(37.0019, -122.0536);
        LatLng merrillLtLn = new LatLng(36.9995, -122.05279);
        LatLng stevenLtLn = new LatLng(36.997, -122.0514);
        LatLng cowellLtLn = new LatLng(36.9965, -122.0548);
        LatLng brdwlkLtLn = new LatLng(36.960586, -122.020597);
        LatLng dwntwnLtLn = new LatLng(36.973067, -122.026563);

        ucsc_map.setMyLocationEnabled(true);
        ucsc_map.moveCamera(CameraUpdateFactory.newLatLngZoom(ucscruz, 14));


        CircleOptions oakesOptions = new CircleOptions()
                .center(oakesLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle oakesCircle = ucsc_map.addCircle(oakesOptions);

        CircleOptions eightOptions = new CircleOptions()
                .center(eightLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle eightCircle = ucsc_map.addCircle(eightOptions);

        CircleOptions porterOptions = new CircleOptions()
                .center(porterLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle porterCircle = ucsc_map.addCircle(porterOptions);

        CircleOptions kresgeOptions = new CircleOptions()
                .center(kresgeLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle kresgeCircle = ucsc_map.addCircle(kresgeOptions);

        CircleOptions nineOptions = new CircleOptions()
                .center(nineLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle nineCircle = ucsc_map.addCircle(nineOptions);

        CircleOptions tenOptions = new CircleOptions()
                .center(tenLtLn)
                .radius(GPSTracker.RAD_DIST_SMALL);
        Circle tenCircle = ucsc_map.addCircle(tenOptions);

        CircleOptions crownOptions = new CircleOptions()
                .center(crownLtLn)
                .radius(GPSTracker.RAD_DIST_SMALL);
        Circle crownCircle = ucsc_map.addCircle(crownOptions);

        CircleOptions crownAptOptions = new CircleOptions()
                .center(crownAptLtLn)
                .radius(GPSTracker.RAD_DIST_MED);
        Circle crownAptCircle = ucsc_map.addCircle(crownAptOptions);

        CircleOptions merrillOptions = new CircleOptions()
                .center(merrillLtLn)
                .radius(GPSTracker.RAD_DIST_SMALL);
        Circle merrillCircle = ucsc_map.addCircle(merrillOptions);

        CircleOptions stevenOptions = new CircleOptions()
                .center(stevenLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle stevenCircle = ucsc_map.addCircle(stevenOptions);

        CircleOptions cowellOptions = new CircleOptions()
                .center(cowellLtLn)
                .radius(GPSTracker.RAD_DIST);
        Circle cowellCircle = ucsc_map.addCircle(cowellOptions);

        CircleOptions brdwlkOptions = new CircleOptions()
                .center(brdwlkLtLn)
                .radius(GPSTracker.RAD_DIST_LRG);
        Circle brdwlkCircle = ucsc_map.addCircle(brdwlkOptions);

        CircleOptions dwntwnOptions = new CircleOptions()
                .center(dwntwnLtLn)
                .radius(GPSTracker.RAD_DIST_LRG);
        Circle dwntwnCircle = ucsc_map.addCircle(dwntwnOptions);

        ucsc_map.setOnMapClickListener(this);

        /*ucsc_map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(ucscruz));*/
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        float[] distance = new float[10];
        distanceBetween(lat1, lon1, lat2, lon2, distance);
        return (distance[0]);
    }

    @Override
    public void onMapClick(LatLng p) {
        Double lat = p.latitude;
        Double lon = p.longitude;
        Double tryDist;
        String tappedCollege="NOTHING";

        tryDist = distance(lat, lon, GPSTracker.OAKES_LAT, GPSTracker.OAKES_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST) {
            tappedCollege="Oakes";
        }
        tryDist = distance(lat, lon, GPSTracker.EIGHT_LAT, GPSTracker.EIGHT_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Eight";
        }
        tryDist = distance(lat, lon, GPSTracker.NINE_LAT, GPSTracker.NINE_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Nine";
        }
        tryDist = distance(lat, lon, GPSTracker.TEN_LAT, GPSTracker.TEN_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Ten";
        }
        tryDist = distance(lat, lon, GPSTracker.PORTER_LAT, GPSTracker.PORTER_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Porter";
        }
        tryDist = distance(lat, lon, GPSTracker.KRESGE_LAT, GPSTracker.KRESGE_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Kresge";
        }
        tryDist = distance(lat, lon, GPSTracker.CROWN_LAT, GPSTracker.CROWN_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Crown";
        }
        tryDist = distance(lat, lon, GPSTracker.CROWNAPT_LAT, GPSTracker.CROWNAPT_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST_MED){
            tappedCollege="Crown";
        }
        tryDist = distance(lat, lon, GPSTracker.MERRILL_LAT, GPSTracker.MERRILL_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Merrill";
        }
        tryDist = distance(lat, lon, GPSTracker.STEVEN_LAT, GPSTracker.STEVEN_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Stevenson";
        }
        tryDist = distance(lat, lon, GPSTracker.COWELL_LAT, GPSTracker.COWELL_LON, 'K');
        if (tryDist < GPSTracker.RAD_DIST){
            tappedCollege="Cowell";
        }

        Toast.makeText(getApplicationContext(), "YOU CLICKED: " + tappedCollege, Toast.LENGTH_LONG).show();
    }
}
