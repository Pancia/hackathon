package com.turingsarmy.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends ActionBarActivity {

    private Button playfriends, fight;
    private TextView location;
    private String home = "Oakes"; //TODO IN FUTURE PING SERVER FOR YOUR HOME COLLEGE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        final GPSTracker track = new GPSTracker(this);
        playfriends = (Button) findViewById(R.id.play_button_playfriends);
        location = (TextView) findViewById(R.id.play_textview_location);
        fight = (Button) findViewById(R.id.play_button_fight);
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

        fight.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (track.getCurrentCollege().toString().equals("none")) {
                    Toast.makeText(getApplicationContext(), "Option currently unavailable, move to the nearest college to enable", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityRPS.class);
                    PlayActivity.this.startActivity(myIntent);
                }
            }
        });
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
