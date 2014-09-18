package edu.purdue.cs408.meadle.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.adapters.StableArrayAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends ListFragment {

    public PlaceholderFragment() {
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

        Handler handler=new Handler();

        final Runnable r = new Runnable()
        {
            public void run()
            {
                AnimationAdapter ada = new SwingBottomInAnimationAdapter( new StableArrayAdapter<String>(getActivity(), R.layout.list_row_draganddrop, R.id.list_row_draganddrop_textview, new String[] {"Hello", "I", "Am", "A", "List", "of", "Locations"}));
                ada.setAbsListView(getListView());
                getListView().setAdapter(ada);
            }
        };

        handler.postDelayed(r, 1000);

        DynamicListView listView = (DynamicListView) getListView();
        listView.enableDragAndDrop();
        listView.setDraggableManager(new TouchViewDraggableManager(R.id.list_row_draganddrop_touchview));
//            listView.setOnItemMovedListener(new MyOnItemMovedListener(adapter));
//            listView.setOnItemLongClickListener(new MyOnItemLongClickListener(listView));
    }
}
