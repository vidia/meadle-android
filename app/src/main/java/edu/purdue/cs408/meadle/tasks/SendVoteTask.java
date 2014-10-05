package edu.purdue.cs408.meadle.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.BaseAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import edu.purdue.cs408.meadle.Constants;
import edu.purdue.cs408.meadle.interfaces.OnSendVoteFinishedListener;
import edu.purdue.cs408.meadle.models.YelpLocation;

/**
 * Created by tylorgarrett on 10/4/14.
 */
public class SendVoteTask extends AsyncTask<Void, String, String> {
    private final static String TAG = "SendVoteTask";
    private OnSendVoteFinishedListener listener;
    private String meadleId;
    private String userId;
    private List<YelpLocation> locations;

    public SendVoteTask(String meadleId, String userId, List<YelpLocation> locations, OnSendVoteFinishedListener listener){
        this.listener = listener;
        this.meadleId = meadleId;
        this.userId   = userId;
        this.locations = locations;
    }


    @Override
    protected String doInBackground(Void... params) {
        HttpClient client = new DefaultHttpClient();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority(Constants.BASEURL).appendPath("meeting").appendPath(meadleId).appendPath("vote").appendQueryParameter("userId", userId);
        HttpPut putRequest = new HttpPut(builder.toString());
        JSONObject jObject = new JSONObject();
        StringEntity se = null;
        JSONArray ranked = new JSONArray();
        try { // this format may be incorrect to match the array input. idk how to do that...
            for(int i=0; i<=5; i++){
                ranked.put(locations.get(i).id);
            }
            jObject.put("ranked",ranked);
            se = new StringEntity(jObject.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG,jObject.toString());
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
    protected void onPostExecute(String result) {
        if(listener != null){
            Log.d(TAG, "result="+result);
            listener.OnSendVoteFinishedListener(result);

        }
    }
}
