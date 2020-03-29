package com.example.zoomwroom;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

// call new Notify().execute() to run
public class  Notify extends AsyncTask<Void,Void,Void>
{
    final private String serverKey = "key=" + "AIzaSyCzF5PRcdDADRymHeRF2piC4TOUDIflvZM";
    final private String contentType = "application/json";
    private String token;
    private String message;

    Notify(String token, String message){
        this.message = message;
        this.token = token;
    }
    @Override
    protected Void doInBackground(Void... voids) {


        try {
            Log.d("getToken", token);
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", serverKey);
            conn.setRequestProperty("Content-Type", contentType);

            JSONObject json = new JSONObject();

            json.put("to", token);

            JSONObject info = new JSONObject();
            info.put("title", "Request Accepted");   // Notification title
            info.put("body", message); // Notification body

            json.put("notification", info);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            conn.getInputStream();

        }
        catch (Exception e)
        {
            Log.d("Error",""+e);
        }

        return null;
    }
}