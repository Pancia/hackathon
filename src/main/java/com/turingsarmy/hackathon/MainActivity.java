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

import com.google.gson.JsonObject;

public class MainActivity extends Activity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyShrdPrfs.init(this);

        Button login = (Button) findViewById(R.id.activitymain_button_login);
        Button signup = (Button) findViewById(R.id.activitymain_button_signup);
        username = (EditText) findViewById(R.id.activitymain_edittext_username);
        password = (EditText) findViewById(R.id.activitymain_edittext_password);

        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tryToVerifyUsernamePassword();
            }
        });

        signup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SignupActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void tryToVerifyUsernamePassword() {
        String tUsername = "";
        String tPassword = "";
        try {
            //noinspection ConstantConditions
            tUsername = username.getText().toString();
            //noinspection ConstantConditions
            tPassword = password.getText().toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
            createToast("Please fill in all fields");
        }
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(this);
        man.setAction(AsyncJsonRequestManager.Actions.VERIFYUSER);
        man.setRequestBody(new HackMap()
                .setUsername(tUsername)
                .setPassword(tPassword));
        final String finalTUsername = tUsername;
        final String finalTPassword = tPassword;
        man.setCallback(new MyFutureTask() {
            @Override
            public void onCompleted(Exception e, JsonObject json) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                Log.w(TAG, json.toString());
                int status = json.get("response").getAsJsonObject().get("status").getAsInt();
                String college = json.get("response").getAsJsonObject().get("college").getAsString();

                if (finalTUsername!=null && finalTPassword!=null){
                    if (status == 0){
                        MyShrdPrfs.saveObject("USERNAME", finalTUsername);
                        MyShrdPrfs.saveObject("PASSWORD", finalTPassword);
                        MyShrdPrfs.saveObject("COLLEGE", college);
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

    private void createToast (final String string) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
