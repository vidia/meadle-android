package edu.purdue.cs408.meadle.tasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

import edu.purdue.cs408.meadle.interfaces.OnGetGcmRegIdTaskFinishedListener;

/**
 * Created by kyle on 9/22/14.
 */
public class GetGcmRegIdTask extends AsyncTask<Void,Void,String> {
    private String senderId;
    private Activity activity;
    private OnGetGcmRegIdTaskFinishedListener listener;
    private GoogleCloudMessaging gcm;

    public GetGcmRegIdTask(String senderId, Activity activity, OnGetGcmRegIdTaskFinishedListener listener){
        this.senderId = senderId;
        this.activity = activity;
        this.listener = listener;

    }
    @Override
    protected String doInBackground(Void... voids) {

        String msg = "";
        String regid = null;
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(activity);
            }
            regid = gcm.register(senderId);
            msg = "Device registered, registration ID=" + regid;
            // store regid

        } catch (IOException e) {
            e.printStackTrace();
        }
        return regid;

    }

    @Override
    protected void onPostExecute(String regId){
        if(listener != null){
            listener.onGetGcmRegIdFinished(regId);
        }
    }
}
