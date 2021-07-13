package json;

import com.google.gson.Gson;

/**
 * Created by jakeg on 3/7/2018.
 */

public class JsonConverter {

    public static Object convertJsonToObject(String json, Object object) {
        return new Gson().fromJson(json, object.getClass());
    }

    public static String convertObjectToJson(Object object){
        return new Gson().toJson(object);
    }

}
