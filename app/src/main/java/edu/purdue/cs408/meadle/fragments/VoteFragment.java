package edu.purdue.cs408.meadle.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.adapters.YelpArrayAdapter;
import edu.purdue.cs408.meadle.data.YelpTestData;
import edu.purdue.cs408.meadle.interfaces.GetGcmRegListener;
import edu.purdue.cs408.meadle.interfaces.OnGetMeetingFinishedListener;
import edu.purdue.cs408.meadle.interfaces.OnYelpDataTaskFinishedListener;
import edu.purdue.cs408.meadle.models.YelpLocation;
import edu.purdue.cs408.meadle.tasks.GetMeetingTask;
import edu.purdue.cs408.meadle.tasks.YelpDataTask;
import edu.purdue.cs408.meadle.util.manager.GcmManager;
import edu.purdue.cs408.meadle.util.manager.MeadleDataManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class VoteFragment extends ListFragment implements OnYelpDataTaskFinishedListener, OnGetMeetingFinishedListener,GetGcmRegListener {


    public VoteFragment() {
        setHasOptionsMenu(true);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Set an icon in the ActionBar
        menu.findItem(R.id.action_confirm).setIcon(
                new IconDrawable(getActivity(), Iconify.IconValue.fa_check)
                        .colorRes(R.color.purple)
                        .actionBarSize());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_confirm:
                return true;
        }
        return false;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GcmManager manager = new GcmManager(getActivity());
        manager.getRegID(this);


        new YelpDataTask(this).execute(YelpTestData.IDS);


        DynamicListView listView = (DynamicListView) getListView();
        listView.enableDragAndDrop();
        listView.setDraggableManager(new TouchViewDraggableManager(R.id.list_row_draganddrop_touchview));
//            listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));
//            listView.setOnItemLongClickListener(new MyOnItemLongClickListener(listView));

    }

    @Override
    public void onYelpDataTaskFinished(ArrayList<YelpLocation> locations) {
        BaseAdapter adapter = new YelpArrayAdapter(getActivity(), R.layout.yelp_list_item, R.id.list_row_draganddrop_textview, locations);
        AnimationAdapter animationAdapter = new SwingBottomInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(getListView());
        getListView().setAdapter(animationAdapter);
    }

    @Override
    public void onGetMeetingFinished(JSONObject jsonObject) {
        Log.d("OnGetMeetingFinished", jsonObject.toString());
        JSONArray topLocations = null;
        try {
                topLocations = jsonObject.getJSONArray("topLocations");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String locations[] = new String[topLocations.length()];
        for(int i=0; i<topLocations.length(); i++){
            try {
                locations[i] = (String) topLocations.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        new YelpDataTask(this).execute(locations);

    }

    @Override
    public void OnRegIdReceived(String regId) {
        GetMeetingTask task = new GetMeetingTask(MeadleDataManager.getMeadleId(getActivity()),regId,this);
        task.execute();

    }
}
