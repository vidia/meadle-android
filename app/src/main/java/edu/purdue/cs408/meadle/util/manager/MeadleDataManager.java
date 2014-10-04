package edu.purdue.cs408.meadle.util.manager;

import android.content.Context;

/**
 * Created by david on 9/25/14.
 */
public class MeadleDataManager {

    public static void putMeadleId(Context c, String meadleId) {
        c.getSharedPreferences("current_meadle", Context.MODE_PRIVATE).edit().putString("meadleId", meadleId).commit();
    }

    public static String getMeadleId(Context c) {
        return c.getSharedPreferences("current_meadle", Context.MODE_PRIVATE).getString("meadleId", null);
    }
}
