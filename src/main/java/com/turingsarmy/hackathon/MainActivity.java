package com.turingsarmy.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends Activity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private Button login, signup;
    private EditText username, password;
    private boolean verified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyShrdPrfs.init(this);

        login = (Button) findViewById(R.id.activitymain_button_login);
        signup = (Button) findViewById(R.id.activitymain_button_signup);
        username = (EditText) findViewById(R.id.activitymain_edittext_username);
        password = (EditText) findViewById(R.id.activitymain_edittext_password);

        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v)
            {
                tryToVerifyUsernamePassword();
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

    private void tryToVerifyUsernamePassword(){
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(this);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("USERNAME".toLowerCase(), username.getText().toString());
        map.put("PASSWORD".toLowerCase(), password.getText().toString());

        man.setAction(AsyncJsonRequestManager.Actions.VERIFYUSER);
        man.setRequestBody(map);
        man.setCallback(new MyFutureTask() {
            @Override
            public void onRequestCompleted(JSONObject json) {
                String response = json.optString("response");
                String college = json.optString("college");

                if (!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
                    if (response.equals("success")){
                        MyShrdPrfs.saveObject("USERNAME", username.getText().toString());
                        MyShrdPrfs.saveObject("PASSWORD", password.getText().toString());
                        MyShrdPrfs.saveObject("COLLEGE", college);
                        Log.w("College Check", college);
                        Intent myIntent = new Intent(MainActivity.this, MenuActivity.class);
                        MainActivity.this.startActivity(myIntent);
                    }
                    else {
                    createToast("Username and password don't match, try again");
                    }
                }
                else {
                    createToast("Please fill in all fields");
                }
            }

        }).execute();
    }

    private void createToast (final String s){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            }
        });
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
