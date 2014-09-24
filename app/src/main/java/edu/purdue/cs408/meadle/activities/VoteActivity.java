package edu.purdue.cs408.meadle.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.fragments.VoteFragment;


public class VoteActivity extends MeadleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new VoteFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }


}
