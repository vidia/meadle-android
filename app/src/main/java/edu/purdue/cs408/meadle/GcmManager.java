package edu.purdue.cs408.meadle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;

import edu.purdue.cs408.meadle.interfaces.GetGcmRegListener;
import edu.purdue.cs408.meadle.interfaces.OnGetGcmRegIdTaskFinishedListener;
import edu.purdue.cs408.meadle.tasks.GetGcmRegIdTask;

/**
 * Created by kyle on 9/22/14.
 */
public class GcmManager implements OnGetGcmRegIdTaskFinishedListener {
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private final static String TAG = "GcmManager";

    private ArrayList<GetGcmRegListener> getRegIdListeners;

    private Activity activity;
    private GoogleCloudMessaging gcm;

    public GcmManager(Activity activity) {
        this.activity = activity;
        getRegIdListeners = new ArrayList<GetGcmRegListener>();

    }

    /*
            Check if the Phone has google play services installed on it
     */

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                //finish();
            }
            return false;
        }
        return true;
    }

    /*
            Get the sharedPreferences used for storing registration Id
     */
    private SharedPreferences getGCMPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return activity.getSharedPreferences("GcmPref", Context.MODE_PRIVATE);
    }

    /*
            Store the registration Id in shared Prefs
     */

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    /*
            Get the regId from shared Preferences
     */


    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }



    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }


    /*
        Used to retrieve the GCmId from shared preferences
        If the ID already exists, then call the listener with the registration ID
        If the ID does not exist, then we need to create it, and the listener will be called once the Async Task which
        get the GCM ID returns.
     */

    public void getRegID(GetGcmRegListener listener){
        String regId = getRegistrationId(activity);
        if(regId.equals("")){
            getRegIdListeners.add(listener);
            register();
        } else {
            listener.OnRegIdReceived(regId);
        }

    }


    /*
        Used to start a task to retrieve the GCM Registration Id from online and store it in shared preferences
     */


    public void register(){
        String regid = null;
        if(checkPlayServices()) {
            regid = getRegistrationId(activity);
            // no id, so create it
            if (regid.equals("")) {
                GetGcmRegIdTask task = new GetGcmRegIdTask(Constants.GCMSENDERID, activity, this);
                task.execute();
            } else {
                return;
            }
        }
    }

       /*
            When GetGCmRegId is finished, this is called
            We should store the id in shared preferences and alert any listeners that we now have a regId
        */
    @Override
    public void onGetGcmRegIdFinished(String regId) {
        Log.d(TAG,"OnGetGcmRegIdFinished regId="+regId);
        storeRegistrationId(activity,regId);
        for(GetGcmRegListener listener : getRegIdListeners){
            listener.OnRegIdReceived(regId);
        }


    }
}
