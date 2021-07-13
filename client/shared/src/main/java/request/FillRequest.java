package request;

/**
 * Created by jakeg on 3/4/2018.
 *
 * Populates the server's database with generated data for the specified user name.
 * The required 'username' parameter must be a user already registered with the server. If there
 * is any data in the database already associated with the given user name, it is deleted. The
 * optional 'generations' parameter lets the caller specify the number of generations of ancestors
 * to be generated, and must be a non-negative integer (the default is 4, which results in 31 new
 * persons each with associated events).
 */

public class FillRequest {
    private String userName;
    private int generations;

    /**
     * Constructor when generations are givenm
     * @param username
     * @param generations
     */
    public FillRequest(String username, int generations){
        this.userName = username;
        this.generations = generations;
    }
    /**
     * Default Constructor when no generations are given
     * @param username
     */
    public FillRequest(String username){
        this.userName = username;
        generations = 4;
    }

    /**
     * getter for field username
     * @return
     */
    public String getUsername() {
        return userName;
    }

    /**
     * getter for field generations
     * @return
     */
    public int getGenerations() {
        return generations;
    }
}
