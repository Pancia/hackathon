package com.turingsarmy.hackathon.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.turingsarmy.hackathon.R;
import com.turingsarmy.hackathon.location.GPSTracker;
import com.turingsarmy.hackathon.server.AsyncJsonRequestManager;
import com.turingsarmy.hackathon.server.HackMap;
import com.turingsarmy.hackathon.server.MyFutureTask;
import com.turingsarmy.hackathon.storage.MyShrdPrfs;
import com.turingsarmy.hackathon.ui.activities.DefenseLobbyActivity;
import com.turingsarmy.hackathon.ui.activities.GameActivity;
import com.turingsarmy.hackathon.ui.activities.PlayGameActivityRPS;

public class PlayMenuFragment extends Fragment {

    private static final String TAG = PlayMenuFragment.class.getSimpleName();
    private String location;
    private boolean home;
    private Activity myActivity;
    private String playerType;

    public PlayMenuFragment(){
        GPSTracker tracker = new GPSTracker(getActivity());
        location = tracker.getCurrentCollege();
        if (location.equals(MyShrdPrfs.myShrdPrfs.getString("HOME", "")))
            home = true;
        else
            home = false;
    }

    public PlayMenuFragment(String location, boolean home) {
        this.location = location;
        this.home = home;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_playmenu, container, false);
        assert v != null;
        if (savedInstanceState != null){
            location = savedInstanceState.getString("location");
            home = savedInstanceState.getBoolean("home");
        }
        setupMenu(v, location, home);
        return v;
    }

    private void setupMenu(View v, String location, boolean home){
        Button fight = (Button) v.findViewById(R.id.play_button_fight);
        TextView tv = (TextView) v.findViewById(R.id.play_textview_location);

        String college = MyShrdPrfs.myShrdPrfs.getString("COLLEGE", "");
        final GPSTracker track = new GPSTracker(getActivity());
        //Button playfriends = (Button) findViewById(R.id.play_button_playfriends);
        if (track.getCurrentCollege().equals("none")){
            tv.setText("You are currently not in any college");
        }
        else {
            tv.setText("You are currently in " + track.getCurrentCollege());
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

        fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity)myActivity).changeFragment(new PlayMMFragment("Oakes"));
            }
        });
    }

    private void tryToJoinGame() {
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(getActivity());
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
                } else if (getActivity() == null) {
                    Log.e(TAG, "activity was null");
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
                    Intent myIntent = new Intent(getActivity(), DefenseLobbyActivity.class);
                    getActivity().startActivity(myIntent);
                }
                /**Attacker*/
                else if (playerType.equals("attacker") && status == 0) {
                    String p2_username = json.get("response").getAsJsonObject().get("p2_username").getAsString();
                    if (p2_username.equals("pve")) {
                        Log.d(TAG, "pve");
                        ((GameActivity)getActivity()).changeFragment(new PlayMMFragment());
                    } else {
                        Log.d(TAG, "pvp!");
                        Intent myIntent = new Intent(getActivity(), PlayGameActivityRPS.class);
                        myIntent.putExtra("p1name", MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""));
                        myIntent.putExtra("p2name", p2_username);
                        myIntent.putExtra("gamemode", playerType);
                        getActivity().startActivity(myIntent);
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
        if (getActivity() == null) {
            Log.e(TAG, "activity was null!");
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.myActivity = activity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("location", location);
        outState.putBoolean("home", home);
        super.onSaveInstanceState(outState);
    }
}
