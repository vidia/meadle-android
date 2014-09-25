package edu.purdue.cs408.meadle.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONObject;

import edu.purdue.cs408.meadle.GcmManager;
import edu.purdue.cs408.meadle.MeadleSharer;
import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.interfaces.GetGcmRegListener;
import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;
import edu.purdue.cs408.meadle.tasks.StartMeetingTask;

public class CreateMeadleActivity extends Activity implements GetGcmRegListener, OnStartMeetingFinishedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meadle);


        // Get the GCM RegID, wait for listener to respond
        GcmManager gcmManager = new GcmManager(this);
        gcmManager.getRegID(this);


        //TODO: Call task to create meadle.
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_meadle, menu);
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

    public void onShare(View view) {

        MeadleSharer.getInstance(this.getApplicationContext()).shareCurrentMeadle();


        Intent waiting = new Intent(this, WaitingActivity.class);
        startActivity(waiting);
    }

    /*
           Once we have received the gcm id we can now start a meeting
     */
    @Override
    public void OnRegIdReceived(String regId) {
        UserLocation location = new UserLocation(regId,100.29,89.05); //TODO: Create location.
        StartMeetingTask task = new StartMeetingTask(location,this);
        task.execute();

    }

    @Override
    public void onStartMeetingFinished(JSONObject jsonObject) {
        Log.d("CREATEMEETINGACTIVITY", "OnStartMeetingFinsihed" + jsonObject.toString());

        //TODO: Turn off progress bar and fill in meadle id text/share
        //TODO: Save meadle ID into shared prefs.



    }
}
