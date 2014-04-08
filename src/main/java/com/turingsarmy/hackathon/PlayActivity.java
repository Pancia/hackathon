package com.turingsarmy.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class PlayActivity extends Activity {

    private Button playfriends, fight;
    private String playerType;
    private TextView location;
    private String home; //TODO IN FUTURE PING SERVER FOR YOUR HOME COLLEGE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        home = MyShrdPrfs.myShrdPrfs.getString("COLLEGE", "");
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
                playerType = "defender";
            }
            else {
                fight.setText("Fight against " + track.getCurrentCollege().toString());
                playerType = "attacker";
            }
        }

        fight.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (track.getCurrentCollege().toString().equals("none")) {
                    Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityMM.class);
                    PlayActivity.this.startActivity(myIntent);
                    Toast.makeText(getApplicationContext(), "Option currently unavailable, move to the nearest college to enable", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityMM.class);
                    PlayActivity.this.startActivity(myIntent);
                    tryToJoinGame();
                }
            }
        });
    }

    private void tryToJoinGame() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""));
        map.put("gamemode", playerType);
        final GPSTracker track = new GPSTracker(this);

        AsyncJsonRequestManager man = new AsyncJsonRequestManager(PlayActivity.this);  //TODO do logic for which game to play
                man.setAction(AsyncJsonRequestManager.Actions.JOINGAME);
                man.setRequestBody(map);
                man.setCallback(new MyFutureTask() {
                    @Override
                    public void onRequestCompleted(JSONObject json) {
                        String response = json.optString("response");
                        String p2_username = json.optString("p2_username");
                        createToast(response);
                        createToast(p2_username);

                        //invariant, response or p2_username will be null

                        if (response.equals("try again")) {
                            tryToJoinGame();
                        } else if (track.getCurrentCollege().equals(home) && response.equals("added to game")){
                            Intent myIntent = new Intent(PlayActivity.this, DefenseLobbyActivity.class);
                            PlayActivity.this.startActivity(myIntent);
                        }
                        else if (!track.getCurrentCollege().equals(home) && p2_username != ""){
                            Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityRPS.class);
                            PlayActivity.this.startActivity(myIntent);
                        }
                        else if (response.equals("game already exists")){
                            createToast("You are currently in a game. Please wait until your current game ends before starting a new one.");
                        } else if (response.equals("UserDatabase is null!")){
                            createToast("Your data base is empty.");
                            createToast("base null");
                            Intent myIntent = new Intent(PlayActivity.this, MainActivity.class);
                            PlayActivity.this.startActivity(myIntent);
                        } else if (p2_username.equals("pve")){
                            createToast("pve");
                            Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityMM.class);
                            PlayActivity.this.startActivity(myIntent);
                        } else if (!p2_username.equals("")){
                            Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityRPS.class);
                            PlayActivity.this.startActivity(myIntent);
                        }
                    }
                }).execute();
    }

    private void createToast (final String s){
        PlayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

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
