package response;

/**
 * Created by jakeg on 2/16/2018.

 *
 * error
 *
 * the result of calling a login on the database
 */

public class LoginResponse {
    private String authToken;
    private String username;
    private String personID;
    private String error;
    //Constructor
    /**
     * authToken: String
     * username: String
     * personID: String, personID of the user's generated person object
     *
     * stores error if request was invalid
     *
     * @param authToken
     * @param username
     * @param personID
     */
    public LoginResponse(String authToken, String username, String personID){
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }
    public LoginResponse(String error){
        this.error = error;
    }
    /**
     * returns authToken
     */
    public String getAuthToken(){
        return authToken;
    }
    /**
     * returns username
     */
    public String getUsername(){
        return username;
    }
    /**
     * returns personID
     */
    public String getPersonID(){
        return personID;
    }

    @Override
    public String toString(){
        StringBuilder newString = new StringBuilder();
        newString.append("LoginResponse{authToken: " + authToken +
                        ", username: " + username +
                        ", personID: " + personID + "}");
        return newString.toString();
    }
}
