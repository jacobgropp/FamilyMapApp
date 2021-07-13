package request;
/**
 * Created by jakeg on 2/16/2018.
 *
 * requests access all family members from a users family tree
 */
public class PersonsRequest {
    private String authToken;
    //Constructor
    /**
     * stores the information from the client to access a specific person
     *
     * authorizationToken: String
     * people: Array of person objects
     */
    public PersonsRequest(String authToken){
        this.authToken = authToken;
    }
    /**
     * getter for the field AuthorizationToken
     * @return authToken
     */
    public String getAuthToken(){
        return authToken;
    }
}
