package com.turingsarmy.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class SignupActivity extends Activity {

    private static final String TAG = SignupActivity.class.getSimpleName();
    private Spinner collegeSpinner;
    private EditText et_username, et_password, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        setContentView(R.layout.signup);

        collegeSpinner = (Spinner) findViewById(R.id.signup_spinner_college);
        et_username = (EditText) findViewById(R.id.signup_edittext_username);
        et_password = (EditText) findViewById(R.id.signup_edittext_password);
        et_email = (EditText) findViewById(R.id.signup_edittext_email);
        collegeSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.row,
                R.id.row_textview_spinnerelement, res.getStringArray(R.array.signup_colleges)));

        (findViewById(R.id.signup_button_submit)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (et_email.getText() != null && et_password.getText() != null
                        && et_username.getText() != null && collegeSpinner.getSelectedItem() != null) {
                    String userEmail = et_email.getText().toString();
                    if (userEmail.contains("@ucsc.edu")) {
                        tryToCreateUser(et_username.getText().toString(), et_password.getText().toString(),
                                et_email.getText().toString(), collegeSpinner.getSelectedItem().toString());

                        Intent myIntent = new Intent(SignupActivity.this, MenuActivity.class);
                        SignupActivity.this.startActivity(myIntent);
                    } else {
                        Toast.makeText(SignupActivity.this, "Please enter a valid UCSC email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });    }

    private void tryToCreateUser(String username, String password, String email, String college){
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(this);
        man.setAction(AsyncJsonRequestManager.Actions.ADDUSER);
                man.setRequestBody(new HackMap()
                        .setUsername(username)
                        .setPassword(password)
                        .setEmail(email)
                        .setCollege(college));
                man.setCallback(new MyFutureTask() {
                    @Override
                    public void onCompleted(Exception e, JsonObject json) {
                        Log.i(TAG, json.toString());
                    }
                })
                .execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
