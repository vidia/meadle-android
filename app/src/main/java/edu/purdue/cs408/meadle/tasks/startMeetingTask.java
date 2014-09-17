package edu.purdue.cs408.meadle.tasks;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;

/**
 * Created by kyle on 9/17/14.
 */
public class startMeetingTask extends AsyncTask<Void,Void,Void>{
    public static String BASEURL = "http://meadle.herokuapp.com/";
    private OnStartMeetingFinishedListener listener ;

    public startMeetingTask(OnStartMeetingFinishedListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(BASEURL+"/meeting");
        HttpResponse response = null;
        try{
            response = client.execute(getRequest);
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
