package edu.purdue.cs408.meadle.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import edu.purdue.cs408.meadle.interfaces.OnJoinMeetingFinishedListener;
import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;

/**
 * Created by kyle on 9/17/14.
 */
public class JoinMeetingTask extends AsyncTask<Void,Void,Void>{
    public static String BASEURL = "http://meadle.herokuapp.com/";
    private OnJoinMeetingFinishedListener listener ;
    private String userId;
    private String meadleId;
    private long lat;
    private long lng;
    private Context c;

    public JoinMeetingTask(OnJoinMeetingFinishedListener listener, Context c, String meadleId,String userId, long lat, long lng){
        this.listener = listener;
        this.c = c;
        this.userId = userId;
        this.meadleId = meadleId;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        HttpPut putRequest = new HttpPut(BASEURL+"/meeting/"+meadleId+"/join?meetingId="+meadleId);
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
        putRequest.setEntity(se);
        HttpResponse response = null;
        String jsonResp = null;
        try{
            response = client.execute(putRequest);
            jsonResp = EntityUtils.toString(response.getEntity());


        }catch(Exception e){
            e.printStackTrace();

        }

        if(listener != null){
            listener.OnJoinMeetingFinished(jsonResp);
        }
        return null;
    }
}
