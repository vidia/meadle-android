package edu.purdue.cs408.meadle.structures;

/**
 * Created by kyle on 9/20/14.
 */
public class UserLocation {
    private String userId;
    private long lat;
    private long lng;

    public UserLocation(String userId, long lat, long lng){
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
    }

    public long getLat(){
        return lat;
    }

    public long getLng(){
        return lng;
    }

    public String getUserId(){
        return userId;
    }
}
