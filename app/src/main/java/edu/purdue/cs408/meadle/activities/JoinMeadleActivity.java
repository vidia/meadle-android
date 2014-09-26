package edu.purdue.cs408.meadle.activities;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import org.json.JSONObject;

import java.util.List;

import edu.purdue.cs408.meadle.GcmManager;
import edu.purdue.cs408.meadle.MeadleSharer;
import edu.purdue.cs408.meadle.interfaces.GetGcmRegListener;
import edu.purdue.cs408.meadle.interfaces.OnJoinMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;
import edu.purdue.cs408.meadle.tasks.JoinMeetingTask;

/**
 * Created by david on 9/25/14.
 */
public class JoinMeadleActivity extends MeadleActivity implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, GetGcmRegListener, OnJoinMeetingFinishedListener {
    public final String TAG = "JoinMeadleActivity";
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationClient locationClient;
    private String meadleId;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        String scheme = data.getScheme(); // "http"
        String host = data.getHost(); // "twitter.com"
        List<String> params = data.getPathSegments();

         meadleId = params.get(0); // "status"

        locationClient = new LocationClient(this,this,this);
        locationClient.connect();


        //TODO: Join the meadle starting here.
    }

    public void onMeadleJoined() {
        Intent waiting = new Intent(this, WaitingActivity.class);
        startActivity(waiting);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "GPS Connected");
        // Get the GCM RegID, wait for listener to respond
        GcmManager gcmManager = new GcmManager(this);
        gcmManager.getRegID(this);


    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void OnRegIdReceived(String regId) {
        Location l = locationClient.getLastLocation();
        UserLocation location = new UserLocation(regId,l.getLatitude(),l.getLongitude()); //TODO: Create location.
        JoinMeetingTask task = new JoinMeetingTask(meadleId,location,this);
        task.execute();

    }

    @Override
    public void onJoinMeetingFinished(JSONObject jsonResp) {
        Log.d(TAG,"OnJoinMeetingFinished json=" + jsonResp.toString());

    }
}
