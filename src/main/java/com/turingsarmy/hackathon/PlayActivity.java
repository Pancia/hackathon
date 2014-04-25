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

import com.google.gson.JsonObject;

import java.util.HashMap;

public class PlayActivity extends Activity {

    private Button playfriends, fight;
    private String playerType;
    private TextView location;
    private String home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        home = MyShrdPrfs.myShrdPrfs.getString("COLLEGE", "");//TODO NPE HERE?
        final GPSTracker track = new GPSTracker(this);
        playfriends = (Button) findViewById(R.id.play_button_playfriends);
        location = (TextView) findViewById(R.id.play_textview_location);
        fight = (Button) findViewById(R.id.play_button_fight);
        if (track.getCurrentCollege().equals("none")){
            location.setText("You are currently not in any college");
        }
        else {
            location.setText("You are currently in " + track.getCurrentCollege());
            if (track.getCurrentCollege().equals(home)){
                fight.setText("Defend " + track.getCurrentCollege());
                playerType = "defender";
            }
            else {
                fight.setText("Fight against " + track.getCurrentCollege());
                playerType = "attacker";
            }
        }

        fight.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tryToJoinGame();
            }
        });
    }

    private void tryToJoinGame() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""));
        map.put("gamemode", playerType);
        final GPSTracker track = new GPSTracker(this);

        AsyncJsonRequestManager man = new AsyncJsonRequestManager(PlayActivity.this);
                man.setAction(AsyncJsonRequestManager.Actions.JOINGAME);
                man.setRequestBody(new HackMap()
                        .setUsername(MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""))
                        .setGamemode(playerType)
                );
                man.setCallback(new MyFutureTask() {
                    @Override
                    public void onCompleted(Exception e, JsonObject json) {
                        String response = String.valueOf(json.get("response"));
                        String p2_username = String.valueOf(json.get("p2_username"));
                        createToast(response);
                        createToast(p2_username);

                        //invariant, response or p2_username will be null

                        if (response.equals("try again")) {
                            //tryToJoinGame();
                            createToast("try again");
                        }
                        /**Defender*/
                        else if (track.getCurrentCollege().equals(home) && response.equals("added to game")){
                            Intent myIntent = new Intent(PlayActivity.this, DefenseLobbyActivity.class);
                            PlayActivity.this.startActivity(myIntent);
                        }
                        /**Attacker*/
                        else if (!track.getCurrentCollege().equals(home) && !p2_username.equals("")){
                            Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityRPS.class);
                            myIntent.putExtra("p1name", MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""));
                            myIntent.putExtra("p2name", p2_username);
                            myIntent.putExtra("gamemode", "attacker");
                            PlayActivity.this.startActivity(myIntent);
                        } else if (response.equals("game already exists")){
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
                        }
                    }
                }).execute();
    }

    private void createToast (final String string){
        PlayActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PlayActivity.this, string, Toast.LENGTH_SHORT).show();

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
