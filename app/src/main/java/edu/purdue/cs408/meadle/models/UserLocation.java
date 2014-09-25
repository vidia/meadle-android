package edu.purdue.cs408.meadle.models;

/**
 * Created by kyle on 9/20/14.
 */
public class UserLocation {
    private String userId;
    private double lat;
    private double lng;

    public UserLocation(String userId, double lat, double lng){
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

    public String getUserId(){
        return userId;
    }
}
