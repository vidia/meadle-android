package edu.purdue.cs408.meadle.tasks;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.purdue.cs408.meadle.Constants;
import edu.purdue.cs408.meadle.interfaces.OnJoinMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;

/**
 * Created by kyle on 9/17/14.
 * Task to have a user join a meeting on the server
 */
public class JoinMeetingTask extends AsyncTask<Void, Void, String> {
    private OnJoinMeetingFinishedListener listener ;
    private String userId;
    private String meadleId;
    private double lat;
    private double lng;

    public JoinMeetingTask(String meadleId,UserLocation location,OnJoinMeetingFinishedListener listener){
        this.listener = listener;
        this.userId = location.getUserId();
        this.meadleId = meadleId;
        this.lat = location.getLat();
        this.lng = location.getLng();
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        Uri.Builder builder  = new Uri.Builder();
        builder.scheme("http").authority(Constants.BASEURL).appendPath("meeting").appendPath(meadleId).appendPath("join");
        HttpPut putRequest = new HttpPut(builder.toString());
        JSONObject jObject = new JSONObject();
        StringEntity se = null;
        try {
            jObject.put("userId", userId);
            jObject.put("lat",lat);
            jObject.put("lng",lng);
            se = new StringEntity(jObject.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        putRequest.setHeader("Content-type", "application/json");
        putRequest.setEntity(se);
        HttpResponse response = null;
        String jsonResp = null;
        try{
            response = client.execute(putRequest);
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
            listener.onJoinMeetingFinished(jsonObject);

        }

    }
}
