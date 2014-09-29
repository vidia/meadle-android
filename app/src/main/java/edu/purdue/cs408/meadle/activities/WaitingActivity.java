package edu.purdue.cs408.meadle.activities;

import edu.purdue.cs408.meadle.util.MeadleSharer;
import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class WaitingActivity extends MeadleActivity {

    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");

            if(message.equals("ready")) {
                //Notification has been recieved that says all people have joined a meadle. Time to vote.
                MeadleDataManager.setMeadleDoneWaiting(WaitingActivity.this);
                MeadleDataManager.setMeadleVoting(WaitingActivity.this);

                Intent i = new Intent(WaitingActivity.this, VoteActivity.class);
                startActivity(i);
            } else if(message.equals("finished")) {
                //Notification has been recieved that alerts meadle is complete
                MeadleDataManager.setHaveResult(WaitingActivity.this);

                Intent i = new Intent(WaitingActivity.this, ResultsActivity.class);
                startActivity(i);
            }

            //do other stuff here
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.waiting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {

            MeadleSharer.getInstance(this).shareCurrentMeadle();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    //TODO: This should recieve a GCM notification
}
