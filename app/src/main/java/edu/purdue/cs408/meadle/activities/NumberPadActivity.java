package edu.purdue.cs408.meadle.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import org.json.JSONObject;

import edu.purdue.cs408.meadle.Constants;
import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.interfaces.GetGcmRegListener;
import edu.purdue.cs408.meadle.interfaces.OnJoinMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;
import edu.purdue.cs408.meadle.tasks.JoinMeetingTask;
import edu.purdue.cs408.meadle.util.manager.GcmManager;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;


public class NumberPadActivity extends MeadleActivity implements View.OnClickListener,View.OnLongClickListener, GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, GetGcmRegListener, OnJoinMeetingFinishedListener {
    private static  final String TAG = "NumperPadActivity";
    private  LocationClient locationClient;
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int[] numberButtonRes = new int[] {
            R.id.numberpad_0,
            R.id.numberpad_1,
            R.id.numberpad_2,
            R.id.numberpad_3,
            R.id.numberpad_4,
            R.id.numberpad_5,
            R.id.numberpad_6,
            R.id.numberpad_7,
            R.id.numberpad_8,
            R.id.numberpad_9,
    };

    private EditText typeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_number_pad);

        findViewById(R.id.accept_button).setOnClickListener(this);

        for(int res : numberButtonRes) {
            findViewById(res).setOnClickListener(this);
        }
        findViewById(R.id.btn_num_pad_delete).setOnClickListener(this);

        typeView  = (EditText) findViewById(R.id.textBox);

        invalidateCheckButton();

        findViewById(R.id.btn_num_pad_delete).setOnLongClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.number_pad, menu);
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
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.numberpad_0:
                typeCharacter('0');
                typeCharacter('0');
                break;
            case R.id.numberpad_1:
                typeCharacter('1');
                break;
            case R.id.numberpad_2:
                typeCharacter('2');
                typeCharacter('2');
                break;
            case R.id.numberpad_3:
                typeCharacter('3');
                break;
            case R.id.numberpad_4:
                typeCharacter('4');
                typeCharacter('4');
                break;
            case R.id.numberpad_5:
                typeCharacter('5');
                break;
            case R.id.numberpad_6:
                typeCharacter('6');
                typeCharacter('6');
                break;
            case R.id.numberpad_7:
                typeCharacter('7');
                break;
            case R.id.numberpad_8:
                typeCharacter('8');
                typeCharacter('8');
                break;
            case R.id.numberpad_9:
                typeCharacter('9');
                break;

            case R.id.btn_num_pad_delete:
                //TODO: Allow the back button to be spammed
                typeCharacter((char)8); //delete
                break;

            case R.id.accept_button:
                joinMeadle();
                break;


        }
    }

    private void typeCharacter(char c) {
        char DELETE_KEY = (char) 8;
        if(c != DELETE_KEY) {
            if(typeView.getText().length() < Constants.MEADLE_CODE_LENGTH) {
                typeView.getText().append(c);
            }
        } else {
            Log.d("TYPING", "Deleting");
            String s = typeView.getText().toString();
            if (s.length() > 0) {
                s = s.substring(0, s.length() - 1);
                typeView.setText(s);
            }
            invalidateCheckButton();
        }
    }

    private void invalidateCheckButton() {
        int color;
        if(typeView.getText().length() == Constants.MEADLE_CODE_LENGTH)
            color = R.color.purple;
        else
            color = android.R.color.darker_gray;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            findViewById(R.id.accept_button).setBackground(new IconDrawable(this, Iconify.IconValue.fa_check).colorRes(color));
        } else {
            findViewById(R.id.accept_button).setBackgroundDrawable(new IconDrawable(this, Iconify.IconValue.fa_check).colorRes(color));
        }
    }

    private void joinMeadle(){
        // Starting a meeting will begin once GPS has connected
        locationClient = new LocationClient(this,this,this);
        locationClient.connect();

    }

    @Override
    public boolean onLongClick(View v) {
        switch(v.getId()) {
            case R.id.btn_num_pad_delete:
                typeView.setText("");
                invalidateCheckButton();
                return true;
        }
        return false;
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

    @Override
    public void OnRegIdReceived(String regId) {
        EditText textBox = (EditText) findViewById(R.id.textBox);
        String meadleId = textBox.getText().toString();
        Location l = locationClient.getLastLocation();
        UserLocation location = new UserLocation(regId,l.getLatitude(),l.getLongitude()); //TODO: Create location.
        JoinMeetingTask task = new JoinMeetingTask(this, meadleId,location,this);
        task.execute();


    }

    @Override
    public void onJoinMeetingFinished(JSONObject jsonResp) {
        Log.d("STATE", "Meadle joined" );
        MeadleDataManager.enterMeadleWaiting(this);
        Intent waiting = new Intent(this, WaitingActivity.class);
        startActivity(waiting);
    }
}
