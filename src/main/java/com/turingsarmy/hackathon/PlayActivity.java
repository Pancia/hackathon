package com.turingsarmy.hackathon;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class PlayActivity extends ActionBarActivity {

    private Button playfriends, fight;
    private TextView location;
    private String home = "Oakes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        GPSTracker track = new GPSTracker(this);
        playfriends = (Button) findViewById(R.id.play_button_playfriends);
        fight = (Button) findViewById(R.id.play_button_fight);
        location = (TextView) findViewById(R.id.play_textview_location);
        if (track.getCurrentCollege().toString().equals("none")){
            location.setText("You are currently not in any college");
        }
        else {
            location.setText("You are currently in " + track.getCurrentCollege().toString());
            if (track.getCurrentCollege().toString().equals(home)){
                fight.setText("Defend " + track.getCurrentCollege().toString());
            }
            else {
                fight.setText("Fight against " + track.getCurrentCollege().toString());
            }
        }
    }

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
