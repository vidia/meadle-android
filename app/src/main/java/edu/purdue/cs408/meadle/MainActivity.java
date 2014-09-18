package edu.purdue.cs408.meadle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends MeadleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MeadleTheme);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onShare(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Meadle Invitation");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join my Meadle! " + "http://www.meadle.com/CODE");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void onJoin(View view) {
        Intent joinIntent = new Intent(this, NumberPadActivity.class);
        startActivity(joinIntent);
    }

    //For testing.
    public void onVote(View view) {
        Intent voteIntent = new Intent(this, VoteActivity.class);
        startActivity(voteIntent);
    }
}
