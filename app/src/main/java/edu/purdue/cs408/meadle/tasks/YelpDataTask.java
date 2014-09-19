package edu.purdue.cs408.meadle.tasks;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import edu.purdue.cs408.meadle.interfaces.OnYelpDataTaskFinishedListener;
import edu.purdue.cs408.meadle.models.YelpLocation;
import edu.purdue.cs408.meadle.util.yelp.YelpAPI;

/**
 * Created by jeremy on 9/18/14.
 */
public class YelpDataTask extends AsyncTask<String, Integer, ArrayList<YelpLocation>> {
    private OnYelpDataTaskFinishedListener listener;
    private Context c;

    public YelpDataTask(OnYelpDataTaskFinishedListener listener){
        this.listener = listener;
    }

    @Override
    protected ArrayList<YelpLocation> doInBackground(String... ids) {
        int count = ids.length;
        ArrayList<YelpLocation> locations = new ArrayList<YelpLocation>();

        for (int i = 0; i < count; i++) {
            String id = ids[i];
            JSONObject jsonResp = null;

            try {
                String res = YelpAPI.getInstance().searchByBusinessId(id);
                jsonResp = new JSONObject(res);
                locations.add(new YelpLocation(jsonResp));
            } catch(Exception e){
                e.printStackTrace();
            }

            publishProgress((int) ((i / (float) count) * 100));

            if (isCancelled()) break;
        }

        return locations;
    }

    @Override
    protected void onPostExecute(ArrayList<YelpLocation> locations) {
        if(listener != null) {
            listener.OnYelpDataTaskFinished(locations);
        }
    }
}