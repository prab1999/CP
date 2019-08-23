package com.example.cp;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;

public class JsonParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    HttpURLConnection urlConnection;
    String userid;

    // constructor
    public JsonParser(String id) {
        this.userid=id;

    }
    public JSONObject getJSONFromUrl() {

        // Making HTTP request
        try {
            // defaultHttpClient
            URL url=new URL("https://codeforces.com/api/user.status?handle="+userid);
            urlConnection = (HttpURLConnection)url.openConnection();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


            if(urlConnection.getErrorStream()==null){
                try {
            InputStream i=urlConnection.getInputStream();

            InputStream in = new BufferedInputStream(i);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            json=result.toString();
            try {
                jObj = new JSONObject(json);

            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }}
        return jObj;

    }
}

