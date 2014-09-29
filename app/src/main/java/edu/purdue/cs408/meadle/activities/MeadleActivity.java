package edu.purdue.cs408.meadle.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;

/**
 * Created by david on 9/8/14.
 */
public class MeadleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        ActionBar actionBar = getActionBar();
        if(actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
            actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000ff")));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rootmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_clear:
                MeadleDataManager.clear(this);
                return true;
        }
        return false;
    }
}
