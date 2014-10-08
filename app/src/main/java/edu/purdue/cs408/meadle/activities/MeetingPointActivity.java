package edu.purdue.cs408.meadle.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.adapters.YelpArrayAdapter;
import edu.purdue.cs408.meadle.interfaces.OnYelpDataTaskFinishedListener;
import edu.purdue.cs408.meadle.models.YelpLocation;
import edu.purdue.cs408.meadle.tasks.YelpDataTask;

public class MeetingPointActivity extends MeadleActivity implements OnYelpDataTaskFinishedListener {
    private final static String TAG = "MeetingPointActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_point);
        String location = getIntent().getExtras().getString("location");
        TextView tv = (TextView) findViewById(R.id.meetingPointTextView);

        String[] locations = new String[1];
        locations[0] = location;

        YelpDataTask task = new YelpDataTask(this);
        task.execute(locations);
        tv.setText(location);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.meeting_point, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onYelpDataTaskFinished(ArrayList<YelpLocation> locations) {
        Log.d(TAG,"YelpDataTaskFinished");
        YelpLocation location = locations.get(0);
        TextView tv  = (TextView)findViewById(R.id.meetingPointTextView);
        tv.setText(location.name);



        JSONObject jLocation = location.location;
        JSONArray displayAddress = null;
        try {
            displayAddress= jLocation.getJSONArray("display_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG,displayAddress.toString());


        String street = null;
        String city = null;
        try {
            street = displayAddress.getString(0);
            city = displayAddress.getString(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView addressTextView = (TextView) findViewById(R.id.meeting_point_address_text_view);
        addressTextView.setText(street+ " " + city);



        double lat = 0;
        double lng = 0;

        try {
            JSONObject coordinates = jLocation.getJSONObject("coordinate");
            lat = coordinates.getDouble("latitude");
            lng = coordinates.getDouble("longitude");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "http://maps.google.com/maps/api/staticmap?center=" + lat + "," + lng + "&zoom=16&scale=2&size=800x500&maptype=roadmap"+"&markers=color:blue%7C"+lat+","+lng;
        //Log.d("url",url);

        SmartImageView imageView = (SmartImageView) findViewById(R.id.meeting_point_map_image_view);
        imageView.setImageUrl(url);



    }
}
