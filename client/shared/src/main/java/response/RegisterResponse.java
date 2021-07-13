package response;

/**
 * Created by jakeg on 2/16/2018.\
 *
 * the result of calling a register on the database
 */

public class RegisterResponse {
    private String authToken;
    private String username;
    private String personID;
    private String message;
    //Constructor
    /**
     * authToken: String
     * username: String
     * personID: String
     *
     * stores error if no AuthToken was found
     *
     * @param authToken
     * @param username
     * @param personID
     */
    public RegisterResponse(String authToken, String username, String personID){
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
    }

    /**
     * Cosntructor for an error message
     *
     * @param message
     */
    public RegisterResponse(String message){
        this.message = message;
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
     * Sets an error message
     * @return
     */
    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        StringBuilder newString = new StringBuilder();
        newString.append("RegisterResponse{" +
                "authToken: " + authToken +
                ", username: " + username +
                ", personID: " + personID +
                ", message: " + message + "}");
        return newString.toString();
    }
}
