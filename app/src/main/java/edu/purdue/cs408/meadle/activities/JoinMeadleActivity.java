package edu.purdue.cs408.meadle.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.List;

import edu.purdue.cs408.meadle.MeadleSharer;
import edu.purdue.cs408.meadle.tasks.JoinMeetingTask;

/**
 * Created by david on 9/25/14.
 */
public class JoinMeadleActivity extends MeadleActivity {

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

        String meadleId = params.get(0); // "status"


        //TODO: Join the meadle starting here.
    }

    public void onMeadleJoined() {
        Intent waiting = new Intent(this, WaitingActivity.class);
        startActivity(waiting);
    }
}
