package edu.purdue.cs408.meadle.tasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.purdue.cs408.meadle.Constants;
import edu.purdue.cs408.meadle.interfaces.OnGetMeetingFinishedListener;

/**
 * Created by kyle on 9/17/14.
 * Task to retrieve information from server about a meeting
 * Returns a JSONObject with meeting information
 */
public class GetMeetingTask extends AsyncTask<Void, Void, String> {
    private OnGetMeetingFinishedListener listener ;
    private String meadleId;
    private String userId;
    private Context c;

    public GetMeetingTask(String meadleId,String userId,OnGetMeetingFinishedListener listener){
        this.listener = listener;
        this.userId = userId;
        this.meadleId = meadleId;
    }

    @Override
    protected String doInBackground(Void... voids) {
        HttpClient client = new DefaultHttpClient();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority(Constants.BASEURL).appendPath("meeting").appendPath(meadleId).appendQueryParameter("userId",userId);
        HttpGet getRequest = new HttpGet(builder.toString());
        HttpResponse response = null;
        String jsonResp = null;
        try{
            response = client.execute(getRequest);
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
            listener.onGetMeetingFinished(jsonObject);

        }

    }
}
