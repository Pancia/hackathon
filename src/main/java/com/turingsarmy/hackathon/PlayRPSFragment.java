package com.turingsarmy.hackathon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayRPSFragment extends Fragment {

    private static final String TAG = PlayRPSFragment.class.getSimpleName();
    private String p1name;
    private String p2name;
    private String gamemode;

    public PlayRPSFragment(String p1name, String p2name, String gameMode) {
        this.p1name = p1name;
        this.p2name = p2name;
        this.gamemode = gameMode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.playrps_fragment, container, false);
        assert v != null;
        if (savedInstanceState != null){
            p1name = savedInstanceState.getString("p1name");
            p2name = savedInstanceState.getString("p2name");
            gamemode = savedInstanceState.getString("gamemode");
        }
        setupGame(v, p1name, p2name, gamemode);
        return v;
    }

    private void setupGame(View v, String p1name, String p2name, String gamemode){
        //
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("p1name", p1name);
        outState.putString("p2name", p2name);
        outState.putString("gamemode", gamemode);
        super.onSaveInstanceState(outState);
    }
}
