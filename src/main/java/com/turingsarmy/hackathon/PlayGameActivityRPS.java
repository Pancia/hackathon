package com.turingsarmy.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class PlayGameActivityRPS extends ActionBarActivity {

    private String move = "SELECT!";
    private String p2sel = "CASTING";
    private String time = "0:15";
    private int tTotal = 15;
    private int timer;
    private boolean win = false;
    private TextView tvP1Choice;
    private TextView vicTim;
    private Button rock;
    private Button paper;
    private Button scissor;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);


        //Timer and Victory
        vicTim = (TextView) findViewById(R.id.activity_rps_time);
        vicTim.setText(time);

        //Player selections
        tvP1Choice = (TextView) findViewById(R.id.activity_rps_p1_sel);
        tvP1Choice.setText(move);
        TextView tvp2Choice = (TextView) findViewById(R.id.activity_rps_p2_sel);
        tvp2Choice.setText(p2sel);

        //Display Names
        final String p1Name = getIntent().getStringExtra("p1name");
        TextView tvUsername = (TextView) findViewById(R.id.activity_rps_p1);
        tvUsername.setText(p1Name);
        final String p2Name = getIntent().getStringExtra("p2name");
        TextView tvEnemyName = (TextView) findViewById(R.id.activity_rps_p2);
        tvEnemyName.setText(p2Name);

        //Buttons
        rock = (Button) findViewById(R.id.activity_rps_rock);
        paper = (Button) findViewById(R.id.activity_rps_paper);
        scissor = (Button) findViewById(R.id.activity_rps_scissor);
        submit = (Button) findViewById(R.id.activity_rps_submit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = tTotal;

        rock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                move = "ROCK";
                tvP1Choice.setText(move);
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                move = "PAPER";
                tvP1Choice.setText(move);
            }
        });
        scissor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                move = "SCISSOR";
                tvP1Choice.setText(move);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitMove(move);
            }
        });

        new CountDownTimer(tTotal * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer--;
                if (timer < 10) {
                    time = "0:0" + Integer.toString(timer);
                    vicTim.setText(time);
                } else {
                    time = "0:" + Integer.toString(timer);
                    vicTim.setText(time);
                }
            }
            public void onFinish() {
                vicTim.setText("0:00");//change to win/lose
                timer = 0;
                submitMove(move);
            }
        }.start();
    }

    private void submitMove(String move) {
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(this);
        man.setAction(AsyncJsonRequestManager.Actions.POSTGAMEMOVE);
        man.setRequestBody(new HackMap()
                .setUsername(MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""))
                .setGamemode(getIntent().getStringExtra("gamemode"))
                .setMove(move));
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                String response = String.valueOf(json.get("response"));
                if (response.equals("success")) {
                    pingServer();
                } else {
                    Toast.makeText(PlayGameActivityRPS.this, response, Toast.LENGTH_SHORT).show();
                    PlayGameActivityRPS.this.startActivity(new Intent(PlayGameActivityRPS.this, PlayActivity.class));
                }
            }

        }).execute();
    }

    private void pingServer() {
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(this);
        man.setAction(AsyncJsonRequestManager.Actions.GETGAMESTATUS);
        man.setRequestBody(new HackMap()
                .setUsername(MyShrdPrfs.myShrdPrfs.getString("USERNAME", ""))
                .setGamemode(getIntent().getStringExtra("gamemode")));
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                String response = String.valueOf(json.get("response"));
                if (response.equals("try again")) {
                    pingServer();
                } else {
                    createToast(response);
                    PlayGameActivityRPS.this.startActivity(new Intent(PlayGameActivityRPS.this, PlayActivity.class));
                }

                String p2_gamemove = String.valueOf(json.get("gamemove"));
                updateTextView(vicTim, move.equals(p2_gamemove)?"draw":getWinStatus(p2_gamemove));
            }

            private String getWinStatus(String p2_gamemove) {
                if (move.equals("ROCK") && p2_gamemove.equals("PAPER")) {
                    return "win";
                } else if (move.equals("PAPER") && p2_gamemove.equals("PAPER")) {
                    return "win";
                } else if (move.equals("SCISSOR") && p2_gamemove.equals("PAPER")) {
                    return "win";
                } else {
                    return "lose";
                }
            }

        }).execute();
    }

    private void createToast (final String string) {
        PlayGameActivityRPS.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(PlayGameActivityRPS.this, string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTextView(final TextView tv, final String string) {
        PlayGameActivityRPS.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(string);

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
