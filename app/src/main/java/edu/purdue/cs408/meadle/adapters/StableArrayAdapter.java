package edu.purdue.cs408.meadle.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.purdue.cs408.meadle.models.YelpLocation;

/**
 * Created by david on 9/18/14.
 */
public class StableArrayAdapter extends ArrayAdapter<YelpLocation> {

    final int INVALID_ID = -1;

    HashMap<YelpLocation, Integer> mIdMap = new HashMap<YelpLocation, Integer>();

    public StableArrayAdapter(Context context, int listItem, List<YelpLocation> objects) {
        super(context, listItem, objects);
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        YelpLocation item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
