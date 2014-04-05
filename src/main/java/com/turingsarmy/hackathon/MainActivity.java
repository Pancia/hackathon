package com.turingsarmy.hackathon;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        setContentView(R.layout.activity_main);

        /**********CAMERONS TEST CODE***************************************************************
        GPSTracker gps = new GPSTracker(this);
        TextView gpsEnable = (TextView)findViewById(R.id.gps_enable);
        TextView gpsLat = (TextView)findViewById(R.id.gps_lat);
        TextView gpsLong = (TextView)findViewById(R.id.gps_long);
        addListenerOnButton(gps, gpsEnable, gpsLat, gpsLong);
        *******************************************************************************************/

        final Button login = (Button) findViewById(R.id.activitymain_button_login);
        final Button signup = (Button) findViewById(R.id.activitymain_button_signup);
        final EditText username = (EditText) findViewById(R.id.activitymain_edittext_username);
        final EditText password = (EditText) findViewById(R.id.activitymain_edittext_password);

        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                //perform action
            }
        });

        signup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainActivity.this, SignupActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    /**********CAMERONS TEST CODE*******************************************************************
    public void addListenerOnButton(final GPSTracker gps, final TextView ena, final TextView lat, final TextView lon) {

        Button gpsButton = (Button) findViewById(R.id.gps_button);
        gpsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final double latitude=gps.getLatitude();
                final double longitude=gps.getLongitude();

                if(gps.canGetLocation()){
                    ena.setText("GPS AVAILABLE");
                    lat.setText("Latitude: " + Double.toString(latitude));
                    lon.setText("Longitude: " + Double.toString(longitude));
                } else {
                    ena.setText("GPS UNAVAILABLE");
                    lat.setText("Latitude:N/A");
                    lon.setText("Longitude:N/A");
                }
                gps.stopUsingGPS();
            }
        });
    }
    ***********************************************************************************************/


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
