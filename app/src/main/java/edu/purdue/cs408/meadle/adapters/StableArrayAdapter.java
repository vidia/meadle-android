package edu.purdue.cs408.meadle.adapters;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 9/18/14.
 */
public class StableArrayAdapter<T> extends ArrayAdapter<T> implements Swappable {

    private ArrayList<T> mItems;

    public StableArrayAdapter(Context context, int listItem, int textView, List<T> data) {
        super(context, listItem, textView);
        mItems = new ArrayList<T>(data.size());
        mItems.addAll(data);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getPosition(T item) {
        return mItems.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public void swapItems(int ifirst, int isecond) {
        //TODO: Change to costom sort
        Collections.swap(mItems, ifirst, isecond);
    }
}
