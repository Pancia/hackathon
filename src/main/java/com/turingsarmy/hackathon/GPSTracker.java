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

import static android.location.Location.distanceBetween;


public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    public static final int RAD_DIST = 150;
    public static final int RAD_DIST_SMALL = 100;
    public static final double OAKES_LAT = 36.9889;
    public static final double OAKES_LON = -122.06422;
    public static final double EIGHT_LAT = 36.9917;
    public static final double EIGHT_LON = -122.0645;
    public static final double PORTER_LAT = 36.995;
    public static final double PORTER_LON = -122.065;
    public static final double KRESGE_LAT = 36.9977;
    public static final double KRESGE_LON = -122.066;
    public static final double NINE_LAT = 37.0955;
    public static final double NINE_LON = -122.058;
    public static final double TEN_LAT = 36.999;
    public static final double TEN_LON = -122.0575;
    public static final double CROWN_LAT = 36.9997;
    public static final double CROWN_LON = -122.0555;
    public static final double MERRILL_LAT = 36.9995;
    public static final double MERRILL_LON = -122.05267;
    public static final double STEVEN_LAT = 36.9975;
    public static final double STEVEN_LON = -122.0514;
    public static final double COWELL_LAT = 36.9965;
    public static final double COWELL_LON = -122.0548;
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
        determineCollege();
        return currentCollege;
    }

    public void determineCollege()
    {
        double tryDist, lowDist=Double.MAX_VALUE;
        double lat = getLatitude();
        double lon = getLongitude();

        tryDist = distance(lat, lon, OAKES_LAT, OAKES_LON, 'K');
        if (tryDist < RAD_DIST) {
            lowDist = tryDist;
            currentCollege="Oakes";
        }
        tryDist = distance(lat, lon, EIGHT_LAT, EIGHT_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Eight";
        }
        tryDist = distance(lat, lon, NINE_LAT, NINE_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Nine";
        }
        tryDist = distance(lat, lon, TEN_LAT, TEN_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Ten";
        }
        tryDist = distance(lat, lon, PORTER_LAT, PORTER_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Porter";
        }
        tryDist = distance(lat, lon, KRESGE_LAT, KRESGE_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Kresge";
        }
        tryDist = distance(lat, lon, CROWN_LAT, CROWN_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Crown";
        }
        tryDist = distance(lat, lon, MERRILL_LAT, MERRILL_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Merrill";
        }
        tryDist = distance(lat, lon, STEVEN_LAT, STEVEN_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Stevenson";
        }
        tryDist = distance(lat, lon, COWELL_LAT, COWELL_LON, 'K');
        if (tryDist < RAD_DIST){
            lowDist = tryDist;
            currentCollege="Cowell";
        }
        if (lowDist > RAD_DIST)
            currentCollege = "none";
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
        return (distance[0]);
    }
}
