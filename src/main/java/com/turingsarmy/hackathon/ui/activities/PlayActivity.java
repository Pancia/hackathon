package com.turingsarmy.hackathon.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.turingsarmy.hackathon.server.AsyncJsonRequestManager;
import com.turingsarmy.hackathon.location.GPSTracker;
import com.turingsarmy.hackathon.server.HackMap;
import com.turingsarmy.hackathon.server.MyFutureTask;
import com.turingsarmy.hackathon.storage.MyShrdPrfs;
import com.turingsarmy.hackathon.R;

public class PlayActivity extends Activity {

    private static final String TAG = PlayActivity.class.getSimpleName();
    private String playerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);
        String home = MyShrdPrfs.myShrdPrfs.getString("COLLEGE", "");
        final GPSTracker track = new GPSTracker(this);
        //Button playfriends = (Button) findViewById(R.id.play_button_playfriends);
        TextView location = (TextView) findViewById(R.id.play_textview_location);
        Button fight = (Button) findViewById(R.id.play_button_fight);
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
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(PlayActivity.this);
                man.setAction(AsyncJsonRequestManager.Actions.JOINGAME);
                man.setRequestBody(new HackMap()
                        .setUsername(MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""))
                        .setGamemode(playerType)
                );
                man.setCallback(new MyFutureTask() {
                    @Override
                    public void onCompleted(Exception e, JsonObject json) {
                        if (e != null) {
                            e.printStackTrace();
                            return;
                        }
                        Log.w(TAG, json.toString());
                        int status = json.get("response").getAsJsonObject().get("status").getAsInt();
                        if (status == 1) {
                            Log.w(TAG, "try again!");
                            createToast("failed");
                        }
                        /**Defender*/
                        else if (playerType.equals("defender") && status == 0) {
                            createToast("defending!"); Log.d(TAG, "added to defender");
                            Intent myIntent = new Intent(PlayActivity.this, DefenseLobbyActivity.class);
                            PlayActivity.this.startActivity(myIntent);
                        }
                        /**Attacker*/
                        else if (playerType.equals("attacker") && status == 0) {
                            String p2_username = json.get("response").getAsJsonObject().get("p2_username").getAsString();
                            if (p2_username.equals("pve")) {
                                Log.d(TAG, "pve");
                                Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityMM.class);
                                PlayActivity.this.startActivity(myIntent);
                            } else {
                                Log.d(TAG, "pvp!");
                                Intent myIntent = new Intent(PlayActivity.this, PlayGameActivityRPS.class);
                                myIntent.putExtra("p1name", MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""));
                                myIntent.putExtra("p2name", p2_username);
                                myIntent.putExtra("gamemode", playerType);
                                PlayActivity.this.startActivity(myIntent);
                            }
                        } else {
                            String message = json.get("response").getAsJsonObject().get("message").getAsString();
                            Log.e(TAG, message);
                            createToast(message);
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
