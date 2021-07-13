package request;

/**
 * Created by jakeg on 2/16/2018.
 *
 *
 */

public class LoginRequest {
    private String userName;
    private String password;
    //Constructor
    /**
     * username: String
     * password: String
     *
     * stores error if LoginRequest was invalid
     */
    public LoginRequest(String username, String password){
        this.userName = username;
        this.password = password;
    }
    /**
     * returns username
     */
    public String getUsername(){
        return userName;
    }
    /**
     * returns password
     */
    public String getPassword(){
        return password;
    }
}
