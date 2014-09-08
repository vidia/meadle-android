package edu.purdue.cs408.meadle;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class NumberPadActivity extends MeadleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_pad);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            findViewById(R.id.btn_delete).setBackground(new IconDrawable(this, Iconify.IconValue.fa_times_circle).colorRes(R.color.purple));
        else
            findViewById(R.id.btn_delete).setBackgroundDrawable(new IconDrawable(this, Iconify.IconValue.fa_times_circle).colorRes(R.color.purple));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.number_pad, menu);
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
}
