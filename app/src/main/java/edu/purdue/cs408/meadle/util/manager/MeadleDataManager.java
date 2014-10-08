package edu.purdue.cs408.meadle.util.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by david on 9/25/14.
 */
public class MeadleDataManager {

    private static boolean WAITING_ACTIVE = false;

    public static void setWaitingActivityActive(boolean b) {
        WAITING_ACTIVE = b;
    }

    public static boolean isWaitingActivityActive() {
        return WAITING_ACTIVE;
    }


    public static enum MeadleState {
        NONE,
        WAITING,
        VOTING,
        WAITING_RESULT,
        HAS_RESULT
    }

    public static MeadleState getCurrentState(Context c) {

        if( getMeadleId(c) == null ) {
            return MeadleState.NONE;
        } else if( isWaitingResult(c) ) {
            return MeadleState.WAITING;
        } else if( isMeadleVoting(c) ) {
            return MeadleState.VOTING;
        } else if( isWaitingResult(c) ) {
            return MeadleState.WAITING_RESULT;
        } else if( hasResult(c) ) {
            return MeadleState.HAS_RESULT;
        } else return MeadleState.NONE;

    }

    public static void enterMeadleWaiting(Context c) {
        if( getMeadleId(c) == null) throw new IllegalStateException("There must be a saved meadle to enter waiting");

        if( getCurrentState(c) == MeadleState.NONE ) {
            setMeadleWaiting(c);
        } else throw new IllegalStateException("Cannot move to waiting from a state that is not NONE");
    }

    public static void startVoting(Context c) {
        if( getCurrentState(c) == MeadleState.WAITING ) {
            setMeadleDoneWaiting(c);
            setMeadleVoting(c);
        } else throw new IllegalStateException("Cannot more to voting from state other than waiting");
    }

    public static void startWaitingForResult(Context c) {
        if( getCurrentState(c) == MeadleState.VOTING ) {
            setMeadleDoneVoting(c);
            setWaitingResult(c);
        } else throw new IllegalStateException("Cannot more to voting from state other than voting");
    }

    public static void setHasResult(Context c) {
        if( getCurrentState(c) == MeadleState.WAITING_RESULT ) {
            setHaveResult(c);
        } else throw new IllegalStateException("Cannot more to voting from state other than waiting result");
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

    private static void setMeadleWaiting(Context c) {
        preferences(c).edit().putBoolean("waiting", true).commit();
    }

    private static void setMeadleDoneWaiting(Context c) {
        preferences(c).edit().putBoolean("waiting", false).commit();
    }

    private static boolean isMeadleWaiting(Context c) {
        return preferences(c).getBoolean("waiting", false);
    }

    private static void setMeadleVoting(Context c) {
        preferences(c).edit().putBoolean("voting", true).commit();
    }

    private static void setMeadleDoneVoting(Context c) {
        preferences(c).edit().putBoolean("voting", true).commit();
    }

    private static boolean isMeadleVoting(Context c) {
        return preferences(c).getBoolean("voting", false);
    }

    private static void setWaitingResult(Context c) {
        preferences(c).edit().putBoolean("waiting_result", true).commit();
    }

    private static boolean isWaitingResult(Context c) {
        return preferences(c).getBoolean("waiting_result", false);
    }

    private static void setHaveResult(Context c) {
        preferences(c).edit().putBoolean("waiting_result", false).commit();
        preferences(c).edit().putBoolean("meadle_complete", true).commit();
    }

    private static boolean hasResult(Context c) {
        return preferences(c).getBoolean("meadle_complete", false);
    }

    public static void clearCurrentMeadle(Context c) {
        preferences(c).edit().putBoolean("meadle_complete", false).commit();
        preferences(c).edit().remove("meadleId").commit();
    }

    public static void clear(Context c) {
        preferences(c).edit().clear().commit();
        WAITING_ACTIVE = false;
    }
}
