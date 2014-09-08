package edu.purdue.cs408.meadle;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;


public class NumberPadActivity extends MeadleActivity implements View.OnClickListener {

    private static final int[] numberButtonRes = new int[] {
            R.id.numberpad_0,
            R.id.numberpad_1,
            R.id.numberpad_2,
            R.id.numberpad_3,
            R.id.numberpad_4,
            R.id.numberpad_5,
            R.id.numberpad_6,
            R.id.numberpad_7,
            R.id.numberpad_8,
            R.id.numberpad_9,
    };

    private EditText typeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_pad);

        for(int res : numberButtonRes) {
            findViewById(res).setOnClickListener(this);
        }
        findViewById(R.id.btn_delete).setOnClickListener(this);

        typeView  = (EditText) findViewById(R.id.textBox);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.numberpad_0:
                typeCharacter('0');
                break;
            case R.id.numberpad_1:
                typeCharacter('1');
                break;
            case R.id.numberpad_2:
                typeCharacter('2');
                break;
            case R.id.numberpad_3:
                typeCharacter('3');
                break;
            case R.id.numberpad_4:
                typeCharacter('4');
                break;
            case R.id.numberpad_5:
                typeCharacter('5');
                break;
            case R.id.numberpad_6:
                typeCharacter('6');
                break;
            case R.id.numberpad_7:
                typeCharacter('7');
                break;
            case R.id.numberpad_8:
                typeCharacter('8');
                break;
            case R.id.numberpad_9:
                typeCharacter('9');
                break;

            case R.id.btn_delete:
                typeCharacter((char)8); //delete
        }
    }

    private void typeCharacter(char c) {
        if(c != (char)8) {
            typeView.getText().append(c);
        } else {
            Log.d("TYPING", "Deleting");
            String s = typeView.getText().toString();
            if(s.length() > 0) {
                s = s.substring(0, s.length() - 1);
                typeView.setText(s);
            }
        }
    }
}
