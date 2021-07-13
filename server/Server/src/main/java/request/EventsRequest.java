package request;
/**
 * Created by jakeg on 2/16/2018.
 *
 * requests access to ALL EventsResponse for ALL family members of the specified user
 */
public class EventsRequest {
    private String authToken;
    //Constructor
    /**
     * authorizationToken: String
     * @param authToken
     */
    public EventsRequest(String authToken){
        this.authToken = authToken;
    }
    /**
     * @return authorizationToken
     */
    public String getAuthToken(){
        return authToken;
    }
}
