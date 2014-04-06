package com.turingsarmy.hackathon;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class SignupActivity extends ActionBarActivity {

    private static final String TAG = SignupActivity.class.getSimpleName();
    private Spinner collegeSpinner;
    private Button submit;
    private EditText username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        setContentView(R.layout.signup);

        collegeSpinner = (Spinner) findViewById(R.id.signup_spinner_college);
        submit = (Button) findViewById(R.id.signup_button_submit);
        username = (EditText) findViewById(R.id.signup_edittext_username);
        password = (EditText) findViewById(R.id.signup_edittext_password);
        email = (EditText) findViewById(R.id.signup_edittext_email);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.row, R.id.row_textview_spinnerelement, res.getStringArray(R.array.signup_colleges));
        collegeSpinner.setAdapter(adapter);

        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !username.toString().isEmpty()){
                    String userEmail = email.getText().toString();
                    if (userEmail.contains("@ucsc.edu")){

                        tryToCreateUser();


                        Intent myIntent = new Intent(SignupActivity.this, MenuActivity.class);
                        SignupActivity.this.startActivity(myIntent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter a valid UCSC email", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });    }

    private void tryToCreateUser(){
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(this);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("USERNAME".toLowerCase(), username.getText().toString());
        map.put("PASSWORD".toLowerCase(), password.getText().toString());
        map.put("EMAIL".toLowerCase(), email.getText().toString());
        map.put("COLLEGE".toLowerCase(), collegeSpinner.getSelectedItem().toString());
        Log.w(TAG, map.toString());

        man.setAction(AsyncJsonRequestManager.Actions.ADDUSER);
                man.setRequestBody(map);
                man.setCallback(new MyFutureTask() {
                    @Override
                    public void onRequestCompleted(JSONObject json) {
                        Log.i(TAG, json.toString());
                    }
                })
                .execute();
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
