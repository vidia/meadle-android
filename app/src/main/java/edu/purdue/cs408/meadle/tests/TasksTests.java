package edu.purdue.cs408.meadle.tests;

import android.test.InstrumentationTestCase;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import edu.purdue.cs408.meadle.interfaces.OnGetMeetingFinishedListener;
import edu.purdue.cs408.meadle.interfaces.OnJoinMeetingFinishedListener;
import edu.purdue.cs408.meadle.interfaces.OnStartMeetingFinishedListener;
import edu.purdue.cs408.meadle.models.UserLocation;
import edu.purdue.cs408.meadle.tasks.GetMeetingTask;
import edu.purdue.cs408.meadle.tasks.JoinMeetingTask;
import edu.purdue.cs408.meadle.tasks.StartMeetingTask;


import java.util.concurrent.CountDownLatch;



/**
 * Created by kyle on 9/23/14.
 */
public class TasksTests extends InstrumentationTestCase implements OnStartMeetingFinishedListener,OnGetMeetingFinishedListener,OnJoinMeetingFinishedListener {
    CountDownLatch signal;
    private String mid;

    public void test_StartMeetingTask() throws Exception{
        signal = new CountDownLatch(1);
        UserLocation location = new UserLocation("android_test_user",50.60,90.28);
        final StartMeetingTask task = new StartMeetingTask(location,this);
        try {
            this.runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    task.execute();

                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        signal.await();

    }

    public void test_GetMeetingTask(){
        signal = new CountDownLatch(1);
        //String meetingId = getInstrumentation().getContext().getSharedPreferences("test_prefs",0).getString("test_meeting_id","");
        // I have the code for getting the value from Shared Preferences from the previous Test, but it doesn't seem to work
        // So for right now just use a static id we now exists in the db (not a good idea)
        final GetMeetingTask task = new GetMeetingTask("23aszrehtzkt9","android_test_user",this);
        try{
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    task.execute();
                }
            });
        } catch(Throwable throwable){

        }

    }

    public void test_JoinMeetingTask(){
        signal = new CountDownLatch(1);
        final JoinMeetingTask task = new JoinMeetingTask("23aszrehtzkt9",new UserLocation("android_join_test_user",50.50,60.60),this);
        try{
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    task.execute();
                }
            });

        }catch(Throwable throwable){

        }
    }


    @Override
    public void onStartMeetingFinished(JSONObject jsonObject) {
        assertNotNull(jsonObject);
        String meetingId = null;
        try {
            meetingId = jsonObject.getString("meetingId");
            Log.d("test_startMeetingTask","meetingId="+meetingId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assertNotNull(meetingId);
        assertFalse(meetingId.length() == 0);
        /*
        SharedPreferences.Editor prefsEditor = getInstrumentation().getContext().getSharedPreferences("test_prefs",0).edit();
        prefsEditor.putString("test_meeting_id",meetingId);
        prefsEditor.commit();
        */
        signal.countDown();
    }

    @Override
    public void onGetMeetingFinished(JSONObject jsonObject) {
        assertNotNull(jsonObject);
        try {
            assertEquals("23aszrehtzkt9",jsonObject.getString("meetingId"));
            assertNotNull(jsonObject.getString("datetime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        signal.countDown();


    }

    @Override
    public void onJoinMeetingFinished(JSONObject jsonResp) {
        //assertNotNull(jsonResp);
        signal.countDown();

    }
}
