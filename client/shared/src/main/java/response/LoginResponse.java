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
    private String message;
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
        this.message = error;
    }
    /**
     * @return authToken
     */
    public String getAuthToken(){
        return authToken;
    }
    /**
     * @return username
     */
    public String getUsername(){
        return username;
    }
    /**
     * @return personID
     */
    public String getPersonID(){
        return personID;
    }

    /**
     * @return message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
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
