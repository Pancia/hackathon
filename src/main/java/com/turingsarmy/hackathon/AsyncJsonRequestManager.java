package com.turingsarmy.hackathon;


import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 2
 * <br> CREATED: 7/11/13
 * <br> LAST MODIFIED 11/23/13 BY: Anthony
 */
public class AsyncJsonRequestManager extends AsyncTask<String, String, String> {

    private Activity myActivity;
    private String TAG;

    //localhost = http://localhost:10080/ when using GAE
    //remote    = http://ucscslugger.appspot.com/test.py/
    private String url = "http://ucscslugger.appspot.com/test.py";

    private HashMap<String, String> myHashMap = new HashMap<String, String>();
    private boolean hasCallback = false;
    private MyFutureTask myFutureTask;

    public AsyncJsonRequestManager(Activity activity) {
        this.myActivity = activity;
        this.TAG = "AJRM#";
    }

    public AsyncJsonRequestManager setCallback(MyFutureTask listnener) {
        this.myFutureTask = listnener;
        this.hasCallback = true;
        return this;
    }

    public AsyncJsonRequestManager setRequestBody(HashMap<String, String> hashMap) {
        this.myHashMap = hashMap;
        return this;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        if (!MyConnectivity.isOnline()) {
//            cancel(true);
//            showGetInternetDialog();
//        }
    }

    @Override
    protected String doInBackground(String... arg0) {
        //if (isCancelled()) //change to check periodically?
        //    return null;

        JsonObject json = new JsonObject();
        if (!myHashMap.isEmpty()) {
            for (Map.Entry<String, String> entry : myHashMap.entrySet()) {
                json.addProperty(entry.getKey(), entry.getValue());
            }
        }

        Ion.with(myActivity, url)
                .setLogging(TAG, Log.INFO)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        myFutureTask.onRequestCompleted(result);
                    }
                });

        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        super.onPostExecute(file_url);
    }

}
