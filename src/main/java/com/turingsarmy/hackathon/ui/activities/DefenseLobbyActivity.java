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

import com.google.gson.JsonObject;
import com.turingsarmy.hackathon.server.AsyncJsonRequestManager;
import com.turingsarmy.hackathon.location.GPSTracker;
import com.turingsarmy.hackathon.server.HackMap;
import com.turingsarmy.hackathon.server.MyFutureTask;
import com.turingsarmy.hackathon.utils.storage.MyShrdPrfs;
import com.turingsarmy.hackathon.R;

public class DefenseLobbyActivity extends Activity {

    private static final String TAG = DefenseLobbyActivity.class.getSimpleName();
    private TextView numDefense, prompt;
    private Button exit;
    private boolean shouldPingServer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defense_lobby);

        numDefense = (TextView) findViewById(R.id.defenselobby_textview_numdefense);
        prompt = (TextView) findViewById(R.id.defenselobby_textview_prompt);
        exit = (Button) findViewById(R.id.defenselobby_button_exit);

        tryToFindNumDef();

        exit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                tryToLeaveLobby();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        pingServer();
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

    private void tryToFindNumDef() {
        GPSTracker track = new GPSTracker(this);
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(DefenseLobbyActivity.this);
        man.setAction(AsyncJsonRequestManager.Actions.GETCOLLEGEINFO);
        man.setRequestBody(new HackMap().setCollege(track.getCurrentCollege()));
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                String defender_count = json.get("response").getAsJsonObject().get("defender_count").getAsString();
                updateTextView(numDefense, defender_count);
            }
        }).execute();
    }

    private void updateTextView(final TextView tv, final String string) {
        DefenseLobbyActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(string);

            }
        });
    }

    private void tryToLeaveLobby() {
        shouldPingServer = false;
        GPSTracker track = new GPSTracker(this);
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(DefenseLobbyActivity.this);
        man.setAction(AsyncJsonRequestManager.Actions.UPDATECOLLEGEINFO);
        man.setRequestBody(new HackMap()
                .setUsername(MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""))
                .setCollege(track.getCurrentCollege()));
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                if (e != null) {
                    e.printStackTrace();
                    return; //maybe try to leave again?
                }
                Log.d(TAG, json.toString());

                int status = json.get("response").getAsJsonObject().get("status").getAsInt();
                if (status != 0) {
                    return; //try to leave again?
                }
                Intent myIntent = new Intent(DefenseLobbyActivity.this, GameActivity.class);
                DefenseLobbyActivity.this.startActivity(myIntent);
            }

        }).execute();
    }

    private void pingServer() {
        //GPSTracker track = new GPSTracker(this);
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(DefenseLobbyActivity.this);
        man.setAction(AsyncJsonRequestManager.Actions.JOINGAME);
        man.setRequestBody(new HackMap()
                .setUsername(MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""))
                .setGamemode("defender"));
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                Log.d(TAG, json.toString());

                int status = json.get("response").getAsJsonObject().get("status").getAsInt();
                if (status == 1){
                    if (shouldPingServer) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }
                        pingServer();
                    }
                } else if (status == 0) {
                    String p2_username = json.get("response").getAsJsonObject().get("p2_username").getAsString();
                    Intent myIntent = new Intent(DefenseLobbyActivity.this, PlayGameActivityRPS.class);
                    myIntent.putExtra("p1name", MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""));
                    myIntent.putExtra("p2name", p2_username);
                    myIntent.putExtra("gamemode", "defender");
                    DefenseLobbyActivity.this.startActivity(myIntent);
                } else {
                    Log.e(TAG, json.get("response").toString());
                }
            }

        }).execute();
    }
}
