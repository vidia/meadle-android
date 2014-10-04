package edu.purdue.cs408.meadle.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.purdue.cs408.meadle.Constants;
import edu.purdue.cs408.meadle.interfaces.OnSendVoteFinishedListener;

/**
 * Created by tylorgarrett on 10/4/14.
 */
public class SendVoteTask extends AsyncTask<Void, String, String> {
    private OnSendVoteFinishedListener listener;
    private String meadleId;
    private String userId;
    private BaseAdapter adapter;

    public SendVoteTask(String meadleId, String userId, BaseAdapter adapter, OnSendVoteFinishedListener listener){
        this.listener = listener;
        this.meadleId = meadleId;
        this.userId   = userId;
        this.adapter  = adapter;
    }


    @Override
    protected String doInBackground(Void... params) {
        HttpClient client = new DefaultHttpClient();
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http").authority(Constants.BASEURL).appendPath("meeting").appendPath(meadleId).appendPath("vote").appendQueryParameter("userId", userId);
        HttpPut putRequest = new HttpPut(builder.toString());
        JSONObject jObject = new JSONObject();
        StringEntity se = null;
        try { // this format may be incorrect to match the array input. idk how to do that...
            jObject.put("yelp-id1", adapter.getItem(0));
            jObject.put("yelp-id2", adapter.getItem(1));
            jObject.put("yelp-id3", adapter.getItem(2));
            se = new StringEntity(jObject.toString());
        } catch (Exception e){
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
    protected void onPostExecute(String result) {
        if(listener != null){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listener.OnSendVoteFinishedListener(jsonObject);

        }
    }
}
