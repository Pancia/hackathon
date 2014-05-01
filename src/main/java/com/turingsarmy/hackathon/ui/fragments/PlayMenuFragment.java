package com.turingsarmy.hackathon.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.turingsarmy.hackathon.location.GPSTracker;
import com.turingsarmy.hackathon.storage.MyShrdPrfs;
import com.turingsarmy.hackathon.R;
import com.turingsarmy.hackathon.ui.activities.GameActivity;

public class PlayMenuFragment extends Fragment {

    private static final String TAG = PlayMenuFragment.class.getSimpleName();
    private String location;
    private boolean home;
    private Activity myActivity;

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

        tv.setText("You are currently in " + location);
        if(home)
            fight.setText("Defend " + location);
        else
            fight.setText("Fight against " + location);

        fight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((GameActivity)myActivity).changeFragment(new PlayMMFragment("Oakes"));
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
