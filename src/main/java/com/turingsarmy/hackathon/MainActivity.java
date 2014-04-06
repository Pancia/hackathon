package com.turingsarmy.hackathon;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

public class MainActivity extends ActionBarActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        setContentView(R.layout.activity_main);

        new AsyncJsonRequestManager(this)
                .setCallback(new MyFutureTask() {
                    @Override
                    public void onRequestCompleted(JsonObject json) {
                        Log.i(TAG, json.toString());
                    }
                })
                .execute();

        final Button login = (Button) findViewById(R.id.activitymain_button_login);
        final Button signup = (Button) findViewById(R.id.activitymain_button_signup);
        final EditText username = (EditText) findViewById(R.id.activitymain_edittext_username);
        final EditText password = (EditText) findViewById(R.id.activitymain_edittext_password);

        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                //perform action
            }
        });

        signup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                Intent myIntent = new Intent(MainActivity.this, SignupActivity.class);
                MainActivity.this.startActivity(myIntent);
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
