package request;
/**
 * Created by jakeg on 2/16/2018.
 *
 * the request for a single event from a give ID
 */
public class EventRequest {
    private String eventID;
    private String authToken;
    //Constructor
    /**
     * requests to access the specified event with the given ID
     * eventID : String
     * authToken : String
     *
     * @param eventID
     * @param authToken
     */
    public EventRequest(String eventID, String authToken){
        this.eventID = eventID;
        this.authToken = authToken;
    }
    //GETTERS
    /**
     * getter for the field personID
     * @return personID
     */
    public String getEventID(){
        return eventID;
    }
    /**
     * getter for the field authToken
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }
}
