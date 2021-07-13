package response;

/**
 * Created by jakeg on 3/8/2018.
 */

public class Response {
    private String message;

    public Response(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return "message: " + message;
    }
}
