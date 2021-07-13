package request;

/**
 * Created by jakeg on 2/16/2018.
 *
 * request to access a person from the database
 */

public class PersonRequest {
    private String personID;
    private String authToken;
    //Constructor
    /**
     * stores the information from the client to access a specific person
     *
     * personID: String
     *
     * @param personID
     * @param authToken
     */
    public PersonRequest(String personID, String authToken){
        this.personID = personID;
        this.authToken = authToken;
    }
    /**
     * getter for the field personID
     * @return personID
     */
    public String getPersonID(){
        return personID;
    }

    /**
     * getter for the field authToken
     * @return authToken
     */
    public String getAuthToken() {
        return authToken;
    }
}
