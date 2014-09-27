package edu.purdue.cs408.meadle.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.logging.ConsoleHandler;

import edu.purdue.cs408.meadle.Constants;
import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;

/**
 * Created by kyle on 9/17/14.
 * Task used to initiated a meeting on the server
 */
public class StartMeetingTask extends AsyncTask<Void, Void, String> {
    private OnStartMeetingFinishedListener listener ;
    private String userId;
    private double lat;
    private double lng;
    private Context c;

    public StartMeetingTask(UserLocation location,OnStartMeetingFinishedListener listener){
        this.listener = listener;
        this.userId = location.getUserId();
        this.lat = location.getLat();
        this.lng = location.getLng();
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority(Constants.BASEURL).appendPath("meeting");
        Log.d("StartMeetingTask","URL="+builder.toString());
        HttpPost postRequest = new HttpPost(builder.toString());
        JSONObject jObject = new JSONObject();


       // http://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(tz);
        String datetime = df.format(new Date());

        StringEntity se = null;

        try {
            UUID uuid =  UUID.randomUUID();
            jObject.put("userId", uuid.toString());
            jObject.put("gcm",userId);
            jObject.put("lat",lat);
            jObject.put("lng",lng);
            jObject.put("datetime",datetime);
            Log.d("OnStartMeetingTask", "json="+jObject.toString());
            se = new StringEntity(jObject.toString(),"UTF8");

        }catch(Exception e){
            e.printStackTrace();
        }
        postRequest.setEntity(se);
        postRequest.setHeader("Content-type", "application/json");
        HttpResponse response = null;
        String jsonResp = null;
        try{
            response = client.execute(postRequest);
            jsonResp = EntityUtils.toString(response.getEntity());


        }catch(IOException e){
            e.printStackTrace();

        }

        return jsonResp;
    }



    @Override
    protected void onPostExecute(String result){
        if(listener != null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listener.onStartMeetingFinished(jsonObject);

        }

    }
}
