package edu.purdue.cs408.meadle.activities;

import edu.purdue.cs408.meadle.util.MeadleSharer;
import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class WaitingActivity extends MeadleActivity {

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("STATE", "Waiting Activity recieved broadcast | " + intent.toString() );
            openNextActivity(intent);
        }
    };

    private void openNextActivity(Intent intent) {
        // Extract data included in the Intent
        String message = intent.getStringExtra("phase");

        Log.d("STATE", "Waiting activity, phase is -> " + message );
        if(message != null) {
            if (message.equals("USER_JOINED")) { //TODO: This should be for ready to vote.
                Log.d("STATE", "Waiting activity, USER JOINED" );
                //Notification has been received that says all people have joined a meadle. Time to vote.
                MeadleDataManager.setMeadleDoneWaiting(this);
                MeadleDataManager.setMeadleVoting(this);

                Intent i = new Intent(this, VoteActivity.class);
                startActivity(i);
            } else if (message.equals("result")) { //TODO: Change this to the new "phase" value
                Log.d("STATE", "Waiting activity. Meadle is ready" );
                //Notification has been received that alerts meadle is complete
                MeadleDataManager.setHaveResult(this);

                Intent i = new Intent(this, ResultsActivity.class);
                startActivity(i);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        //When this activity is opened, it should decide which next activity to open. Using the openNextActivity function.

        if(getIntent() != null) {
            Log.d("STATE", "Waiting activity, received with intent");
            openNextActivity(getIntent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mMessageReceiver, new IntentFilter("waiting_broadcast"));
        MeadleDataManager.setWaitingActivityActive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
        MeadleDataManager.setWaitingActivityActive(false);
    }

}
