package edu.purdue.cs408.meadle.util.manager;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

/**
 * Created by kyle on 9/25/14.
 */
public class UserLocationManager {
    public static final String TAG ="LocationManager";
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationManager locationManager;
    private Activity activity;


    public UserLocationManager(Activity activity){
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

    }

    public void getLocation(){
       Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(TAG,"lat="+l.getLatitude()+" lng="+l.getLongitude());
    }

}

