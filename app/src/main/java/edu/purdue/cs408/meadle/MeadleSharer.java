package edu.purdue.cs408.meadle;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by david on 9/25/14.
 */
public class MeadleSharer {

    private static MeadleSharer INSTANCE;

    public static MeadleSharer getInstance(Activity activity) {
        if(INSTANCE == null) {
            INSTANCE = new MeadleSharer(activity);
        }
        return INSTANCE;
    }

    private Activity activity;
    private MeadleSharer(Activity c) {

        this.activity = c;

    }

    public void shareCurrentMeadle() {

        String meadleId = MeadleDataManager.getMeadleId(activity);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Meadle Invitation");
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Join my Meadle! " + "http://www.meadle.me/" + meadleId);
        sendIntent.setType("text/plain");
        activity.startActivityForResult(sendIntent, 123);

    }
}
