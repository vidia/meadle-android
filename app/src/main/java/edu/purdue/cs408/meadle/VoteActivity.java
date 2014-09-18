package edu.purdue.cs408.meadle;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.TouchViewDraggableManager;

import java.util.Arrays;

import edu.purdue.cs408.meadle.adapters.StableArrayAdapter;


public class VoteActivity extends MeadleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends ListFragment {

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

    private static class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener {

        private final DynamicListView mListView;

        MyOnItemLongClickListener(final DynamicListView listView) {
            mListView = listView;
        }

        @Override
        public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
            if (mListView != null) {
                mListView.startDragging(position - mListView.getHeaderViewsCount());
            }
            return true;
        }
    }

//    private class MyOnItemMovedListener implements OnItemMovedListener {
//
//        private final ArrayAdapter<String> mAdapter;
//
//        private Toast mToast;
//
//        MyOnItemMovedListener(final ArrayAdapter<String> adapter) {
//            mAdapter = adapter;
//        }
//
//        @Override
//        public void onItemMoved(final int originalPosition, final int newPosition) {
//            if (mToast != null) {
//                mToast.cancel();
//            }
//
//            mToast = Toast.makeText(getApplicationContext(), getString(R.string.moved, mAdapter.getItem(newPosition), newPosition), Toast.LENGTH_SHORT);
//            mToast.show();
//        }
//    }
}
