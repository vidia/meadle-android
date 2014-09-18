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

import edu.purdue.cs408.meadle.interfaces.OnGetMeetingFinishedListener;
import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;

/**
 * Created by kyle on 9/17/14.
 */
public class GetMeetingTask extends AsyncTask<Void,Void,Void>{
    public static String BASEURL = "http://meadle.herokuapp.com/";
    private OnGetMeetingFinishedListener listener ;
    private String meadleId;
    private String userId;
    private Context c;

    public GetMeetingTask(OnGetMeetingFinishedListener listener, Context c, String meadleId,String userId){
        this.listener = listener;
        this.c = c;
        this.userId = userId;
        this.meadleId = meadleId;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(BASEURL+"/meeting/"+meadleId+"?userId="+userId);
        HttpResponse response = null;
        String jsonResp = null;
        try{
            response = client.execute(getRequest);
            jsonResp = EntityUtils.toString(response.getEntity());


        }catch(Exception e){
            e.printStackTrace();

        }

        if(listener != null){
            listener.OnGetMeetingFinished(jsonResp);
        }
        return null;
    }
}
