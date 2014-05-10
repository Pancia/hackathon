package com.turingsarmy.hackathon.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.turingsarmy.hackathon.R;
import com.turingsarmy.hackathon.server.AsyncJsonRequestManager;
import com.turingsarmy.hackathon.server.HackMap;
import com.turingsarmy.hackathon.server.MyFutureTask;
import com.turingsarmy.hackathon.ui.activities.MainActivity;
import com.turingsarmy.hackathon.utils.UIThread;
import com.turingsarmy.hackathon.utils.storage.MyShrdPrfs;

public class LoginFragment extends Fragment {
    private static final String TAG = LoginFragment.class.getSimpleName();
    private EditText username, password;
    private Button login, signup;

    public LoginFragment (){}

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
                assert getActivity() != null;
                ((MainActivity)getActivity()).changeFragment(new PlayMMFragment("Oakes")); //TODO change to signup fragment when setup
            }
        });
    }

    private void tryToVerifyUsernamePassword() {
        String tUsername;
        String tPassword;
        try {
            //noinspection ConstantConditions
            tUsername = username.getText().toString();
            //noinspection ConstantConditions
            tPassword = password.getText().toString();

            if (tUsername == null || tPassword == null) {
                UIThread.createToast(getActivity(), "Please enter a username and password");
                return;
            } else if (tUsername.isEmpty() || tPassword.isEmpty()) {
                UIThread.createToast(getActivity(), "Please enter in all fields");
                return;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            UIThread.createToast(getActivity(), "Please fill in all fields");
            return;
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
                    UIThread.createToast(getActivity(), "Network caused login to fail\nplease try again!");
                    return;
                }
                Log.d(TAG, json.toString());
                JsonObject response = json.get("response").getAsJsonObject();
                if (response.get("status").getAsInt() != 0) {
                    UIThread.createToast(getActivity(), "Failed login!");
                    return;
                }
                int status = response.get("status").getAsInt();
                String college = response.get("college").getAsString();

                if (status == 0){
                    MyShrdPrfs.saveObject("USERNAME", finalTUsername);
                    MyShrdPrfs.saveObject("PASSWORD", finalTPassword);
                    MyShrdPrfs.saveObject("COLLEGE", college);

                    assert getActivity() != null;
                    ((MainActivity)getActivity()).changeFragment(new PlayMMFragment("Oakes")); //TODO change to menu fragment when setup
                }
                else {
                    UIThread.createToast(getActivity(), "Username and password don't match\n please try again");
                }
            }

        }).execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putString("location", password.getText().toString());
        outState.putString("username", username.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
