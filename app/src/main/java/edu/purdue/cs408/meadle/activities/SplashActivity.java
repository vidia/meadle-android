package edu.purdue.cs408.meadle.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;

public class SplashActivity extends MeadleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = new Bundle();
        Class<? extends Activity> activity = MainActivity.class;


        switch(MeadleDataManager.getCurrentState(this)) {
            case NONE:

                //There is no current meadle
                activity = MainActivity.class;
                break;

            case WAITING:

                //Meadle is waiting on members to join.
                extras.putString("waitingfor", "USER_JOINED");
                activity = WaitingActivity.class;
                break;

            case VOTING:

                //Current user is voting and has not submitted his/her votes.
                activity = VoteActivity.class;
                break;

            case WAITING_RESULT:

                //This user has voted and is waiting on this meadle's result
                extras.putString("waitingfor", "result");
                activity = WaitingActivity.class;
                break;

            case HAS_RESULT:
                //This meadle has been complete and the user has not cleared the data.
                activity = ResultsActivity.class;
                break;

        }

        Log.d("STATE", "From Splash: Opening activity " + activity.getName() );

        Intent intent = new Intent(this, activity);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
