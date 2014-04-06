package com.turingsarmy.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends ActionBarActivity {

    private Button challenges, stats, leaderboard, map, play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        challenges = (Button) findViewById(R.id.menu_button_challenges);
        stats = (Button) findViewById(R.id.menu_button_stats);
        leaderboard =(Button)findViewById(R.id.menu_button_leaderboard);
        map = (Button)findViewById(R.id.menu_button_map);
        play = (Button) findViewById(R.id.menu_button_play);

        play.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MenuActivity.this, PlayActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        map.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MenuActivity.this, MapActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        challenges.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MenuActivity.this, ChallengesActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        stats.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MenuActivity.this, StatsActivity.class);
                MenuActivity.this.startActivity(myIntent);
            }
        });
        leaderboard.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MenuActivity.this, LeaderActivity.class);
                MenuActivity.this.startActivity(myIntent);
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