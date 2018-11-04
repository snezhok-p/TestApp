package com.mchgmail.pavel.testapp.helper;

import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Ajax extends AsyncTask<Void, Void, String> {

    protected String TAG = this.getClass().getSimpleName();

    private String apikey = "C9wlfytKXEVbG3i12ibldVkerxYWxXTK";
    private String host = "https://api.giphy.com";
    private String searchPath = "/v1/gifs/search";
    private String trendingPath = "/v1/gifs/trending";

    public interface AsyncResponse {
        public void processFinish(String output);
    }

    private String getdata = "";
    private String path = "";

    public AsyncResponse delegate = null;

    public Ajax(AsyncResponse delegate, String type, String text, int limit, int offset) {
        this.delegate = delegate;

        limit = limit * 3;
        if (type.equals("search")) {
            getdata = "api_key="+apikey+"&limit="+limit+"&offset="+offset+"&q="+text;
            path = host+searchPath;
        } else if (type.equals("trending")) {
            getdata = "api_key="+apikey+"&limit="+limit+"&offset="+offset;
            path = host+trendingPath;
        }

    }

    @Override
    protected String doInBackground(Void... params) {
//
        try {
            URL url = new URL(path+"?"+getdata);

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String response = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }
            bufferedReader.close();
            inputStream.close();
            return response;
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }
        return "";
    }

    @Override
    protected void onPostExecute(final String message) {
        delegate.processFinish(message);
    }

    @Override
    protected void onCancelled() {

    }
}
