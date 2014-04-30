package com.turingsarmy.hackathon;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayMMFragment extends Fragment {

    private static final String TAG = PlayMMFragment.class.getSimpleName();
    private String home;

    public PlayMMFragment(String home) {
        this.home = home;
    }

    public PlayMMFragment () {
        home = MyShrdPrfs.myShrdPrfs.getString("HOME", home);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.playmm_fragment, container, false);
        assert v != null;
        if (savedInstanceState != null){
            home = savedInstanceState.getString("home");
        }
        setupGame(v, home);
        return v;
    }

    private void setupGame(View v, String home){
        //
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("home", home);
        super.onSaveInstanceState(outState);
    }
}
