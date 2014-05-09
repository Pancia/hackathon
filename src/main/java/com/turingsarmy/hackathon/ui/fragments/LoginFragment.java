package com.turingsarmy.hackathon.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.turingsarmy.hackathon.R;
import com.turingsarmy.hackathon.server.AsyncJsonRequestManager;
import com.turingsarmy.hackathon.server.HackMap;
import com.turingsarmy.hackathon.server.MyFutureTask;
import com.turingsarmy.hackathon.storage.MyShrdPrfs;
import com.turingsarmy.hackathon.ui.activities.MainActivity;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private Activity myActivity;
    private EditText username, password;
    private Button login, signup;

    public LoginFragment (){    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        assert v != null;
        setupMenu(v);
        return v;
    }

    private void setupMenu(View v){
        login = (Button) v.findViewById(R.id.activitymain_button_login);
        signup = (Button) v.findViewById(R.id.activitymain_button_signup);
        username = (EditText) v.findViewById(R.id.activitymain_edittext_username);
        password = (EditText) v.findViewById(R.id.activitymain_edittext_password);

        login.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                tryToVerifyUsernamePassword();
            }
        });

        signup.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)myActivity).changeFragment(new PlayMMFragment("Oakes")); //TODO change to signup fragment when setup
            }
        });
    }

    private void createToast (final String string){
        if (getActivity() == null) {
            Log.e(TAG, "activity was null!");
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
            }
        });
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
        AsyncJsonRequestManager man = new AsyncJsonRequestManager(getActivity());
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

                        ((MainActivity)myActivity).changeFragment(new PlayMMFragment("Oakes")); //TODO change to menu fragment when setup
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.myActivity = activity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("location", password.getText().toString());
        outState.putString("username", username.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
