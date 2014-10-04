package edu.purdue.cs408.meadle.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david on 9/25/14.
 */
public class MeadleDataManager {

    private static boolean WAITING_ACTIVVE = false;

    public static void setWaitingActivityActive(boolean b) {
        WAITING_ACTIVVE = b;
    }

    public static boolean isWaitingActivityActive() {
        return WAITING_ACTIVVE;
    }

    private static SharedPreferences preferences(Context c) {
        return c.getSharedPreferences("current_meadle", Context.MODE_PRIVATE);
    }

    public static void putMeadleId(Context c, String meadleId) {
        preferences(c).edit().putString("meadleId", meadleId).commit();
    }

    public static String getMeadleId(Context c) {
        return preferences(c).getString("meadleId", null);
    }

    public static void putMeadleVotes(Context c, String[] votes) {
        StringBuilder builder = new StringBuilder();
        builder.append(votes[0]);
        for(int i = 1; i < votes.length; i++) {
            builder.append("|");
            builder.append(votes[i]);
        }

        preferences(c).edit().putString("votes", builder.toString()).commit();
    }

    public static String[] getMeadleVotes(Context c) {
        String allvotes = preferences(c).getString("votes", null);
        if (allvotes == null) return null;

        return allvotes.split("|");
    }

    public static void setMeadleWaiting(Context c) {
        preferences(c).edit().putBoolean("waiting", true).commit();
    }

    public static void setMeadleDoneWaiting(Context c) {
        preferences(c).edit().putBoolean("waiting", false).commit();
    }

    public static boolean isMeadleWaiting(Context c) {
        return preferences(c).getBoolean("waiting", false);
    }

    public static void setMeadleVoting(Context c) {
        preferences(c).edit().putBoolean("voting", true).commit();
    }

    public static void setMeadleDoneVoting(Context c) {
        preferences(c).edit().putBoolean("voting", true).commit();
    }

    public static boolean isMeadleVoting(Context c) {
        return preferences(c).getBoolean("voting", false);
    }

    public static void setWaitingResult(Context c) {
        preferences(c).edit().putBoolean("waiting_result", true).commit();
    }

    public static boolean isWaitingResult(Context c) {
        return preferences(c).getBoolean("waiting_result", false);
    }

    public static void setHaveResult(Context c) {
        preferences(c).edit().putBoolean("waiting_result", false).commit();
        preferences(c).edit().putBoolean("meadle_complete", true).commit();
    }

    public static boolean hasResult(Context c) {
        return preferences(c).getBoolean("meadle_complete", false);
    }

    public static void clearCurrentMeadle(Context c) {
        preferences(c).edit().putBoolean("meadle_complete", false).commit();
        preferences(c).edit().remove("meadleId").commit();
    }

    public static void clear(Context c) {
        preferences(c).edit().clear().commit();
        WAITING_ACTIVVE = false;
    }
}
