package com.turingsarmy.hackathon;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignupActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        setContentView(R.layout.signup);

        final Spinner collegeSpinner = (Spinner) findViewById(R.id.signup_spinner_college);
        final Button submit = (Button) findViewById(R.id.signup_button_submit);
        final EditText username = (EditText) findViewById(R.id.signup_edittext_username);
        final EditText password = (EditText) findViewById(R.id.signup_edittext_password);
        final EditText email = (EditText) findViewById(R.id.signup_edittext_email);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.row, R.id.row_textview_spinnerelement, res.getStringArray(R.array.signup_colleges));
        collegeSpinner.setAdapter(adapter);

        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty() && !username.toString().isEmpty()){
                    String userEmail = email.getText().toString();
                    if (userEmail.contains("@ucsc.edu")){
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
