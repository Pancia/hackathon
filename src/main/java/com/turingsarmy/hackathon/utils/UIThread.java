package com.turingsarmy.hackathon.utils;

import android.app.Activity;
import android.widget.Toast;

public class UIThread {

    public static void createToast(final Activity activity, final String string) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
