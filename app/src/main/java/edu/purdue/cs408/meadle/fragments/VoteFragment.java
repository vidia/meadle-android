package edu.purdue.cs408.meadle.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import java.util.ArrayList;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.adapters.YelpArrayAdapter;
import edu.purdue.cs408.meadle.data.YelpTestData;
import edu.purdue.cs408.meadle.interfaces.OnYelpDataTaskFinishedListener;
import edu.purdue.cs408.meadle.models.YelpLocation;
import edu.purdue.cs408.meadle.tasks.YelpDataTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class VoteFragment extends ListFragment implements OnYelpDataTaskFinishedListener {

    public VoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new YelpDataTask(this).execute(YelpTestData.IDS);


        DynamicListView listView = (DynamicListView) getListView();
        listView.enableDragAndDrop();
        listView.setDraggableManager(new TouchViewDraggableManager(R.id.list_row_draganddrop_touchview));
//            listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));
//            listView.setOnItemLongClickListener(new MyOnItemLongClickListener(listView));
    }

    @Override
    public void OnYelpDataTaskFinished(ArrayList<YelpLocation> locations) {
        BaseAdapter ada = new YelpArrayAdapter(getActivity(), R.layout.yelp_list_item, R.id.list_row_draganddrop_textview, locations);
        AnimationAdapter aada = new SwingBottomInAnimationAdapter(ada);
        aada.setAbsListView(getListView());
        getListView().setAdapter(aada);
    }
}
