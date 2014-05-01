package com.turingsarmy.hackathon;

import android.app.Application;

import com.turingsarmy.hackathon.storage.MyShrdPrfs;

public class hackathon extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyShrdPrfs.init(this);
    }

}
