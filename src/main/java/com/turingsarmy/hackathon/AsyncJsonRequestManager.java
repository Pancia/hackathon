package com.turingsarmy.hackathon;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.HashMap;
import java.util.Map;


//EXAMPLE CODE!
//new AsyncJsonRequestManager(this)
//        .setAction(Actions.ADDUSER)
//        .setRequestBody(<PUT A HASHMAP HERE WITH AJRM.Keys and AJRM.Values MAKE THEM ALL LOWERCASE>)
//        .setCallback(new MyFutureTask() {
//              @Override
//              public void onRequestCompleted(JsonObject json) {
//                  Log.i(TAG, json.toString());
//              }
//
//              @Override
//              public void onRequestFailed(Exception e) {
//                  e.printStackTrace();
//              }
//              })
//          .execute();
public class AsyncJsonRequestManager extends AsyncTask<String, String, String> {

    private Activity myActivity;
    private String TAG;

    public enum Actions {
        ADDUSER, UPDATEUSER, POSTGAMEMOVE, GETGAMESTATUS, JOINGAME
    }

    public enum Keys {
        USERNAME, PASSWORD, EMAIL, COLLEGE, LOCATION, GAMEMOVE, GAMEMODE//, WINS, LOSSES, POINTS
    }

    public enum Values {
        DEFENDER, ATTACKER
    }

    //receive values: USERNAME, GAMEMOVE

    //localhost = http://localhost:10080/ when using GAE
    //remote    = http://ucscslugger.appspot.com/test.py/
    private String baseUrl = "http://ucscslugger.appspot.com/";
    private String endUrl = "test.py";

    private HashMap<String, String> myHashMap = new HashMap<String, String>();
    private boolean hasCallback = false;
    private MyFutureTask myFutureTask;

    public AsyncJsonRequestManager(Activity activity) {
        this.myActivity = activity;
        this.TAG = "AJRM#";
    }

    public AsyncJsonRequestManager setAction(Actions action) {
        endUrl = action.toString().toLowerCase() + ".py";
        return this;
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

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }

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

        Ion.with(myActivity, baseUrl + endUrl)
                .setLogging(TAG, Log.INFO)
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null)
                            myFutureTask.onRequestCompleted(result);
                        else
                            myFutureTask.onRequestFailed(e);
                    }
                });

        return null;
    }

//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }

}
