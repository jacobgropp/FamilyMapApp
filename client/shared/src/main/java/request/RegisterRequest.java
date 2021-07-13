package request;

/**
 * Created by jakeg on 2/16/2018.
 *
 * request to register a user for the FMS
 */
public class RegisterRequest {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    //Constructor
    /**
     *
     * username: String
     * password: String
     * email: String
     * firstName: String
     * lastName: String
     * gender: String (f or m)
     *
     * stores the given information for a specific user to be registered
     * @param username
     * @param password
     */
    public RegisterRequest(String username, String password){
        this.userName = username;
        this.password = password;
    }
    //GETTERS
    /**
     * @return username
     */
    public String getUsername() {
        return userName;
    }
    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    //SETTERS

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();
        newString.append(
                "user{" +
                        "username: " + userName +
                        ", password: " + password +
                        ", email: " + email +
                        ", firstName: " + firstName +
                        ", lastName: " + lastName +
                        ", gender: " + gender + "}");
        return newString.toString();
    }
}
