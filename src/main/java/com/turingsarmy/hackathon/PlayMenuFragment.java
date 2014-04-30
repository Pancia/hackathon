package com.turingsarmy.hackathon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayMenuFragment extends Fragment {

    private static final String TAG = PlayMenuFragment.class.getSimpleName();
    private String location;
    private boolean home;

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
        //
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("location", location);
        outState.putBoolean("home", home);
        super.onSaveInstanceState(outState);
    }
}
