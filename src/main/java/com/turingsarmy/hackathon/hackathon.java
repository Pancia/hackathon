package com.turingsarmy.hackathon;

import android.app.Application;

public class hackathon extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyShrdPrfs.init(this);
    }

}
