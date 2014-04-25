package com.turingsarmy.hackathon;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class JsonHttpRequester {
    private static final String TAG = JsonHttpRequester.class.getSimpleName();
    public static final int GET = 0;
    public static final int POST = 1;
    private final List<NameValuePair> params;
    private String url;

    public JsonHttpRequester(String url, List<NameValuePair> params) {
        this.url = url;
        this.params = params;
    }

    public String makeJsonHttpRequest(int method) {
        return getStringFromHttp(makeHttpRequest(method));
    }

    private InputStream makeHttpRequest(int method) {
        Log.i(TAG, this.url);
        InputStream inpStr = null;
        try {
            if (method == POST) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(this.url);
                httpPost.setEntity(new UrlEncodedFormEntity(this.params));

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inpStr = httpEntity.getContent();
            } else if (method == GET) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                this.url += "?" + URLEncodedUtils.format(this.params, "utf-8");
                HttpGet httpGet = new HttpGet(this.url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                inpStr = httpEntity.getContent();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inpStr;
    }

    private String getStringFromHttp(InputStream inpStr) {
        String jsonString = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inpStr, "iso-8859-1"), 8);
            StringBuilder strBldr = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                strBldr.append(line).append("\n");
            }
            inpStr.close();
            jsonString = strBldr.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
