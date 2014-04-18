package com.turingsarmy.hackathon;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        ADDUSER, UPDATEUSER, POSTGAMEMOVE, GETGAMESTATUS, JOINGAME, RETDEF, GETCOLLEGEINFO, VERIFYUSER, UPDATECOLLEGEINFO
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

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (!myHashMap.isEmpty()) {
            for (Map.Entry<String, String> entry : myHashMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        Log.d(TAG, params.toString());

        String response = new JsonHttpRequester(baseUrl + endUrl, params)
                               .makeJsonHttpRequest(JsonHttpRequester.POST);
        Log.i(TAG, response);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myFutureTask.onRequestCompleted(jsonObject);

        return null;
    }

//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//    }

}
