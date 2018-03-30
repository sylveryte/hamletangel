package com.codedleaf.sylveryte.hamletangel;


import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by sylveryte on 30/3/18.
 * Yay!
 */

class Postmaster {
    //login
    private static final String USERNAME="username";
    private static final String PASSWORD="password";

    //auth
    private static final String ACC_KEY="x-api-user";
    private static final String API_TOKEN="x-api-key";

    //add task
    private static final String TEXT="text";
    private static final String TYPE="type";
    private static final String NOTES="notes";
    private static final String DATE="date";
    private static final String PRIORITY="priority";


    static String uploadTask(HamletTask task,String accKey, String apiToken)
    {
        final RequestParams requestParams=new RequestParams();
        requestParams.put(TEXT,task.getTaskText());
        requestParams.put(TYPE,task.getTaskType());
        requestParams.put(NOTES,task.getNotes());
        requestParams.put(DATE,task.getDate());
        requestParams.put(PRIORITY,String.valueOf(task.getHabiticaDifficulty()));

        SyncHttpClient client=new SyncHttpClient();
        client.addHeader(ACC_KEY,accKey);
        client.addHeader(API_TOKEN,apiToken);

        final String[] results=new String[2];

        client.post("https://habitica.com/api/v3/tasks/user", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    results[0]=response.getString("success");
                    results[1]=response.getJSONObject("data").getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                results[0]="false";
                results[1]=null;
            }
        });
        if (results[0].equals("true"))
            return results[1];
        else return null;
    }


    static String[] userSignIn(String name, String password) {

        final RequestParams requestParams=new RequestParams();
        requestParams.put(USERNAME,name);
        requestParams.put(PASSWORD,password);

        final String[] results = new String[3];
        results[0]="None";
        results[1]="None";
        results[2]="None";
        SyncHttpClient client=new SyncHttpClient();

        client.post("https://habitica.com/api/v3/user/auth/local/login", requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    String  id = dataObject.getString("id");
                    String apiToken = dataObject.getString("apiToken");

                    results[0] = String.valueOf(true);
                    results[1] = id;
                    results[2] = apiToken;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                results[0] = String.valueOf(false);
                results[1] = errorResponse.toString();
            }
        });
        return results;
    }
}
