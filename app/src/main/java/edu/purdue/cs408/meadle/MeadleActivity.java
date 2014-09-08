package edu.purdue.cs408.meadle;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

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
}
