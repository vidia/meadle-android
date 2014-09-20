package edu.purdue.cs408.meadle.adapters;

import android.content.Context;

import java.util.List;

import edu.purdue.cs408.meadle.models.YelpLocation;

/**
 * Created by david on 9/19/14.
 */
public class YelpArrayAdapter extends StableArrayAdapter<YelpLocation> {
    public YelpArrayAdapter(Context context, int listItem, int textView, List<YelpLocation> data) {
        super(context, listItem, textView, data);
    }
}
