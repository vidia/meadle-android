package edu.purdue.cs408.meadle.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.image.SmartImageView;

import java.util.List;

import edu.purdue.cs408.meadle.R;
import edu.purdue.cs408.meadle.models.YelpLocation;

/**
 * Created by david on 9/19/14.
 */
public class  YelpArrayAdapter extends StableArrayAdapter<YelpLocation> {
    private List<YelpLocation> data;
    public YelpArrayAdapter(Context context, int listItem, int textView, List<YelpLocation> data) {
        super(context, listItem, textView, data);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);

        SmartImageView image = (SmartImageView) convertView.findViewById(R.id.yelp_image_view);
        if(getItem(position).image_url != null)
            image.setImageUrl(getItem(position).image_url);

        return convertView;
    }

    public List<YelpLocation> getLocations(){
        return data;
    }
}
