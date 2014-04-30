package com.turingsarmy.hackathon;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;

public interface MyFutureTask extends FutureCallback<JsonObject> {
    @Override
    void onCompleted(Exception e, JsonObject result);
}
