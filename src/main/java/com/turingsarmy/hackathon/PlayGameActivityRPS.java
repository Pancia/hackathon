package com.turingsarmy.hackathon;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayGameActivityRPS extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);

        //Display Names
        final String p1Name = "Jeff";
        TextView tvUsername = (TextView) findViewById(R.id.activity_rps_p1);
        tvUsername.setText(p1Name);
        final String p2Name = "Sally";
        TextView tvEnemyName = (TextView) findViewById(R.id.activity_rps_p2);
        tvEnemyName.setText(p2Name);

        //Buttons
        final Button rock = (Button) findViewById(R.id.activity_rps_rock);
        final Button paper = (Button) findViewById(R.id.activity_rps_paper);
        final Button scissor = (Button) findViewById(R.id.activity_rps_scissor);
        final Button submit = (Button) findViewById(R.id.activity_rps_submit);

        rock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Throw Rock
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Throw Paper
            }
        });
        scissor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Throw Scissor
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Submit toss
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
