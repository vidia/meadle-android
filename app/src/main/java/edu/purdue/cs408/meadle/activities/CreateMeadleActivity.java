package edu.purdue.cs408.meadle.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import org.json.JSONException;
import org.json.JSONObject;

import edu.purdue.cs408.meadle.util.manager.GcmManager;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;
import edu.purdue.cs408.meadle.util.MeadleSharer;
import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.interfaces.GetGcmRegListener;
import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;
import edu.purdue.cs408.meadle.tasks.StartMeetingTask;

public class CreateMeadleActivity extends MeadleActivity implements GetGcmRegListener, OnStartMeetingFinishedListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
    public final String TAG = "CreateMeadleActivity";
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationClient locationClient;
    private String meetingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meadle);

        // Starting a meeting will begin once GPS has connected
        locationClient = new LocationClient(this,this,this);
        locationClient.connect();





        //TODO: Call task to create meadle.
    }



    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        locationClient.disconnect();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_meadle, menu);
        return super.onCreateOptionsMenu(menu);
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

    public void onShare(View view) {

        MeadleSharer.getInstance(this).shareCurrentMeadle();

    }

    /*
           Once we have received the gcm id we can now start a meeting
     */
    @Override
    public void OnRegIdReceived(String regId) {
        Location l = locationClient.getLastLocation();
        UserLocation location = new UserLocation(regId,l.getLatitude(),l.getLongitude()); //TODO: Create location.
        StartMeetingTask task = new StartMeetingTask(location,this);
        task.execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 123) {
            Intent waiting = new Intent(this, WaitingActivity.class);
            startActivity(waiting);
        }
    }

    @Override
    public void onStartMeetingFinished(JSONObject jsonObject) {
        Log.d("CREATEMEETINGACTIVITY", "OnStartMeetingFinsihed" + jsonObject.toString());

        //TODO: Turn off progress bar and fill in meadle id text/share
        //TODO: Save meadle ID into shared prefs.

        (findViewById(R.id.meadle_create_info)).setVisibility(View.VISIBLE);
        (findViewById(R.id.progress)).setVisibility(View.GONE);


        try {
            meetingId = jsonObject.getString("meetingId");

            MeadleDataManager.putMeadleId(this, meetingId);

            ((TextView)findViewById(R.id.meadle_id)).setText(meetingId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
            Once the GPS is connected, we should get the GCM RegId, so we know when the callback occurs the GPS can get
            the location.
     */
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "GPS Connected");
        // Get the GCM RegID, wait for listener to respond
        GcmManager gcmManager = new GcmManager(this);
        gcmManager.getRegID(this);

    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "GPS Disconnected");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.d(TAG, "Unrecoverable GPS error");
        }

    }
}
