package edu.purdue.cs408.meadle.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jeremy on 9/18/14.
 */
public class YelpLocation {
    public String name;
    public String image_url;
    public String shortDescription;
    public ArrayList<String> categories;
    public JSONObject location;

    public YelpLocation(JSONObject location) throws Exception {
        name = location.getString("name");
        image_url = location.getString("image_url");
        shortDescription = location.getString("snippet_text");
        JSONArray categories = location.getJSONArray("categories");
        for (int i = 0; i < categories.length(); i++) {
            JSONArray row = categories.getJSONArray(i);
            this.categories.add(row.getString(0));
        }
        this.location = location.getJSONObject("location");
    }
}
