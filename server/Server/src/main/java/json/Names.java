package json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jakeg on 3/7/2018.
 */

public class Names {
    @SerializedName("data")
    String[] names;

    public String[] getNames(){
        return names;
    }
}
