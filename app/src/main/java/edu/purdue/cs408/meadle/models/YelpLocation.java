package edu.purdue.cs408.meadle.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jeremy on 9/18/14.
 */
public class YelpLocation {
    public String id;
    public String name;
    public String image_url;
    public String shortDescription;
    public ArrayList<String> categories;
    public JSONObject location;
    public JSONObject yelpObject;
    public Double lat;
    public Double lng;

    public YelpLocation(JSONObject location) throws Exception {
        try {
            this.yelpObject = location;
            this.id = location.getString("id");
            this.name = location.getString("name");
            this.image_url = location.getString("image_url");
            this.shortDescription = location.getString("snippet_text");
            this.categories = new ArrayList<String>();
            JSONArray yelpCategories = location.getJSONArray("categories");
            for (int i = 0; i < yelpCategories.length(); i++) {
                JSONArray row = yelpCategories.getJSONArray(i);
                this.categories.add(row.getString(0));
            }
            this.location = location.getJSONObject("location");
        } catch (org.json.JSONException e) {
            System.err.println("Incorrect YelpLocation schema: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
