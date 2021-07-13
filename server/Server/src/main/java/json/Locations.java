package json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jakeg on 3/7/2018.
 */

public class Locations {
    @SerializedName("data")
    private Location[] locations;

    public Location[] getLocations(){
        return locations;
    }
}
