package com.codedleaf.sylveryte.hamletangel;


import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by sylveryte on 30/3/18.
 * Yay!
 */

public class Postmaster {
    static final String USERNAME="username";
    static final String PASSWORD="password";

    public static class HabiticaRestClient {
        private static final String BASE_URL = "https://habitica.com/api/v3/";

        private static AsyncHttpClient client = new AsyncHttpClient();

        public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.get(getAbsoluteUrl(url), params, responseHandler);
        }

        public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.post(getAbsoluteUrl(url), params, responseHandler);
        }

        private static String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }
    }

    public static void login(String username,String password) throws JSONException
    {
        final RequestParams requestParams=new RequestParams();
        requestParams.put(USERNAME,username);
        requestParams.put(PASSWORD,password);
        HabiticaRestClient.post("user/auth/local/login", requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String str = new String(responseBody);

                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(str);

                JSONObject dataObject = mainObject.getJSONObject("data");
                String  id = dataObject.getString("id");
                String apiToken = dataObject.getString("apiToken");

                    Log.d("maaan","id "+id+" token "+apiToken);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(responseBody!=null)
                    Log.d("maaan",responseBody.toString());
            }
        });
    }
}
