package edu.purdue.cs408.meadle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import edu.purdue.cs408.meadle.util.manager.GcmManager;
import edu.purdue.cs408.meadle.R;


public class MainActivity extends MeadleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MeadleTheme);
        GcmManager gcmManager = new GcmManager(this);
        gcmManager.register();
        setContentView(R.layout.activity_start);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_dev_vote:
                Intent voteIntent = new Intent(this, VoteActivity.class);
                startActivity(voteIntent);
                return true;
            case (R.id.action_waiting):
                openWaitingActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openWaitingActivity() {
        //TODO: Check if meadle is in shared prefs then run this method if exists.
        Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
        return;
    }

    public void onShare(View view) {
        Intent i = new Intent(this, CreateMeadleActivity.class);
        startActivity(i);
    }

    public void onJoin(View view) {
        Intent joinIntent = new Intent(this, NumberPadActivity.class);
        startActivity(joinIntent);
    }
}
