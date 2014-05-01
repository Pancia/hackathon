package com.turingsarmy.hackathon.server;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.turingsarmy.hackathon.HackMap;
import com.turingsarmy.hackathon.MyFutureTask;

import java.util.Map;

public class AsyncJsonRequestManager extends AsyncTask<String, String, String> {

    private Activity myActivity;
    private String TAG = "AJRM";
    private Actions myAction;

    public enum Actions {
        ADDUSER, UPDATEUSER, POSTGAMEMOVE, GETGAMESTATUS, JOINGAME, RETDEF, GETCOLLEGEINFO, VERIFYUSER, UPDATECOLLEGEINFO
    }

//    public enum Keys {
//        USERNAME, PASSWORD, EMAIL, COLLEGE, LOCATION, GAMEMOVE, GAMEMODE//, WINS, LOSSES, POINTS
//    }

    //receive values: USERNAME, GAMEMOVE

    //localhost = http://localhost:10080/ when using GAE
    //remote    = http://ucscslugger.appspot.com/test.py/
    private String baseUrl = "http://ucsc-hacksassins.appspot.com/";
    private String endUrl = "test.py";

    private HackMap myHackMap;
    private MyFutureTask myFutureTask;

    public AsyncJsonRequestManager(Activity activity) {
        this.myActivity = activity;
    }

    public AsyncJsonRequestManager setAction(Actions action) {
        endUrl = action.toString().toLowerCase() + ".py";
        this.myAction = action;
        return this;
    }

    public AsyncJsonRequestManager setCallback(MyFutureTask listnener) {
        this.myFutureTask = listnener;
        return this;
    }

    public AsyncJsonRequestManager setRequestBody(HackMap hackMap) {
        this.myHackMap = hackMap;
        return this;
    }

    @Override
    protected String doInBackground(String... args) {
        JsonObject params = new JsonObject();
        if (!myHackMap.isEmpty()) {
            for (Map.Entry<String, String> entry : myHackMap.entrySet()) {
                params.addProperty(entry.getKey(), entry.getValue());
            }
        }
        Log.d(TAG+"#params", params.toString());

        Ion.with(myActivity)
                .load(baseUrl + endUrl)
                .setLogging(TAG+"#ion", Log.INFO)
                .setJsonObjectBody(params)
                .asJsonObject()
                .setCallback(myFutureTask);

        return null;
    }

}
