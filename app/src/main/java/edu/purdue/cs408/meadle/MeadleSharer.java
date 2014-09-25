package edu.purdue.cs408.meadle;

import android.content.Context;
import android.content.Intent;

/**
 * Created by david on 9/25/14.
 */
public class MeadleSharer {

    private static MeadleSharer INSTANCE;

    public static MeadleSharer getInstance(Context c) {
        if(INSTANCE == null) {
            INSTANCE = new MeadleSharer(c);
        }
        return INSTANCE;
    }

    private Context c;
    private MeadleSharer(Context c) {

        this.c = c;

    }

    public void shareCurrentMeadle() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Meadle Invitation");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join my Meadle! " + "http://www.meadle.me/CODE");
        sendIntent.setType("text/plain");
        c.startActivity(sendIntent);
    }
}
