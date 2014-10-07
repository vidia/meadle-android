package edu.purdue.cs408.meadle.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.List;
import java.util.zip.Inflater;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.models.YelpLocation;

/**
 * Created by david on 9/19/14.
 */
public class  YelpArrayAdapter extends StableArrayAdapter{
    private List<YelpLocation> data;
    private Context context;
    public YelpArrayAdapter(Context context, int listItem, List<YelpLocation> data) {
        super(context,listItem, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //convertView = super.getView(position, convertView, parent);
        if(convertView == null){
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.yelp_list_item,null);
        }

        SmartImageView image = (SmartImageView) convertView.findViewById(R.id.yelp_image_view);
        if(getItem(position).image_url != null)
            image.setImageUrl(getItem(position).image_url);


        TextView tv = (TextView) convertView.findViewById(R.id.list_row_draganddrop_textview);
        tv.setText(getItem(position).name);
        return convertView;
    }

    public List<YelpLocation> getLocations(){
        return data;
    }



}
