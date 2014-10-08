package edu.purdue.cs408.meadle;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import edu.purdue.cs408.meadle.activities.VoteActivity;
import edu.purdue.cs408.meadle.activities.WaitingActivity;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;

/**
 * Created by etemplin on 9/25/14.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        // The getMessageType() intent parameter must be the intent you received in the BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // isEmpty() has effect of unparcelling Bundle.
            if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // Post notification of received message

                Log.i("GcmIntentService", "Received: " + extras.toString());



                MeadleDataManager.startVoting(this);
                //TODO: If the state is waiting for users.
                if(MeadleDataManager.isWaitingActivityActive()) {
                    broadcastToWaitingActivity(intent, extras);
                } else {
                    sendNotification(extras.getString("phase"));
                }


            } else {
                // Error
                Log.e("GcmIntentService", "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void broadcastToWaitingActivity(Intent intent, Bundle extras) {
        Intent i = new Intent("waiting_broadcast");
        i.putExtras(extras);
        sendBroadcast(i);
    }

    // Put the message into a notificaiton and post if.
    private void sendNotification(String phase) {
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, WaitingActivity.class), 0);

        String title = "Something happened.";
        if(phase.equals("USER_JOINED")) {
            title = "A friend has joined your meadle";
        }
        //TODO: Add more of the phases here.



        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo_flat)
                .setContentTitle("Meadle updates")
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(title))
                .setContentText(title);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
