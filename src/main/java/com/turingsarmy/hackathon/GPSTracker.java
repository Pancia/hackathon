package com.turingsarmy.hackathon;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import static android.location.Location.distanceBetween;


public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    private static final int RAD_DIST = 800;
    private static final double OAKES_LAT = 36.99405;
    private static final double OAKES_LON = -122.063;
    private static final double EIGHT_LAT = 36.99143;
    private static final double EIGHT_LON = -122.065;
    private static final double PORTER_LAT = 36.9943;
    private static final double PORTER_LON = -122.065;
    private static final double KRESGE_LAT = 36.9977;
    private static final double KRESGE_LON = -122.066;
    private static final double NINE_LAT = 37.0;
    private static final double NINE_LON = -122.0594;
    private static final double TEN_LAT = 37.0;
    private static final double TEN_LON = -122.0588;
    private static final double CROWN_LAT = 36.9997;
    private static final double CROWN_LON = -122.0555;
    private static final double MERRILL_LAT = 36.9995;
    private static final double MERRILL_LON = -122.05267;
    private static final double STEVEN_LAT = 36.9975;
    private static final double STEVEN_LON = -122.0514;
    private static final double COWELL_LAT = 36.9965;
    private static final double COWELL_LON = -122.0548;
    private String currentCollege = "none";

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 100 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
            .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
            }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public String getCurrentCollege()
    {
        return currentCollege;
    }

    public void determineCollege()
    {
        if (distance(getLatitude(), getLongitude(), OAKES_LAT, OAKES_LON, 'K') < RAD_DIST) {
            currentCollege="oakes";
        } else if (distance(getLatitude(), getLongitude(), EIGHT_LAT, EIGHT_LON, 'K') < RAD_DIST){
            currentCollege="eight";
        } else if (distance(getLatitude(), getLongitude(), NINE_LAT, NINE_LON, 'K') < RAD_DIST){
            currentCollege="nine";
        } else if (distance(getLatitude(), getLongitude(), TEN_LAT, TEN_LON, 'K') < RAD_DIST){
            currentCollege="ten";
        } else if (distance(getLatitude(), getLongitude(), PORTER_LAT, PORTER_LON, 'K') < RAD_DIST){
            currentCollege="porter";
        } else if (distance(getLatitude(), getLongitude(), KRESGE_LAT, KRESGE_LON, 'K') < RAD_DIST){
            currentCollege="kresge";
        } else if (distance(getLatitude(), getLongitude(), CROWN_LAT, CROWN_LON, 'K') < RAD_DIST){
            currentCollege="crown";
        } else if (distance(getLatitude(), getLongitude(), MERRILL_LAT, MERRILL_LON, 'K') < RAD_DIST){
            currentCollege="merrill";
        } else if (distance(getLatitude(), getLongitude(), STEVEN_LAT, STEVEN_LON, 'K') < RAD_DIST){
            currentCollege="stevenson";
        } else if (distance(getLatitude(), getLongitude(), COWELL_LAT, COWELL_LON, 'K') < RAD_DIST){
            currentCollege="cowell";
        }
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
        }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        float[] distance = new float[10];
        distanceBetween(lat1, lon1, lat2, lon2, distance);
        Toast.makeText(mContext, "DIST: " + String.valueOf(distance[0]), Toast.LENGTH_LONG).show();
        return (distance[0]);
    }
}
