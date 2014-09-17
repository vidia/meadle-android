package edu.purdue.cs408.meadle.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;

/**
 * Created by kyle on 9/17/14.
 */
public class StartMeetingTask extends AsyncTask<Void,Void,Void>{
    public static String BASEURL = "http://meadle.herokuapp.com/";
    private OnStartMeetingFinishedListener listener ;
    private String userId;
    private long lat;
    private long lng;
    private Context c;

    public StartMeetingTask(OnStartMeetingFinishedListener listener, Context c, String userId, long lat, long lng){
        this.listener = listener;
        this.c = c;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        HttpPost postRequest = new HttpPost(BASEURL+"/meeting");
        JSONObject jObject = new JSONObject();

        Time now = new Time();
        now.setToNow();
        String datetime = now.format("%d.%m.%Y %H.%M.%S");
        StringEntity se = null;

        try {
            jObject.put("userId", userId);
            jObject.put("lat",lat);
            jObject.put("lng",lng);
            jObject.put("datetime",datetime);
            StringEntity sEnt = new StringEntity(jObject.toString());

        }catch(Exception e){
            e.printStackTrace();
        }
        postRequest.setEntity(se);
        HttpResponse response = null;
        try{
            response = client.execute(postRequest);
            String jsonResp = EntityUtils.toString(response.getEntity());


        }catch(Exception e){
            e.printStackTrace();

        }

        if(listener != null){
            listener.onStartMeetingFinished();
        }
        return null;
    }
}
