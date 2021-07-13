package DataAccessObjects;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import DataAccessObjects.Database.DatabaseConnection;
import model.AuthorizationToken;

/**
 * Created by jakeg on 2/15/2018.
 *
 *class to access database objects relating to authorizationTokens
 *
 */
public class AuthorizationDataAccess extends DatabaseConnection{

    /**
     * This stores the created authorizationToken int the Database
     *
     * @param authToken
     *
     * @return a created authorizationToken
     */
    public boolean createAuthToken(AuthorizationToken authToken){
        try {
            openDatabase();
            statement = connection.createStatement();
            String sql =
                    "INSERT into AUTHORIZATIONTOKEN (USER,KEY) " +
                            "VALUES ('" + authToken.getUser() + "', '" + authToken.getKey() + "');";
            statement.executeUpdate(sql);
            closeDatabase();
            return true;
        }
        catch(Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * This method selects the given authorizationToken
     *
     * @param authToken
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean findAuthToken(String authToken){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT USER from AUTHORIZATIONTOKEN" +
                    " where KEY='" + authToken + "';");
            boolean personFound = rs.next();
            rs.close();
            closeDatabase();
            return personFound;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    /**
     * Called when a ClearService is made. Deletes the authToken in the database
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean deleteAuthTokens(){
        try{
            openDatabase();
            //Generates the DELETE statement from the given username.
            statement = connection.createStatement();
            String sql = "DELETE from AUTHORIZATIONTOKEN;";
            statement.executeUpdate(sql);
            closeDatabase();
            return true;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    /**
     * Gets a user from the string
     *
     * @param authToken
     * @return
     */
    public String getUser(String authToken){
        String user = null;
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * from AUTHORIZATIONTOKEN where KEY='" + authToken+"';");
            while(rs.next()){
                user = rs.getString("user");
            }
            closeDatabase();
            return user;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    /**
     * Creates a set of authorization keys that are attached to the given user.
     *
     * @param username
     * @return set of authToken keys belonging to the user
     */
    public Set<String> getKeys(String username){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * from AUTHORIZATIONTOKEN where USER='" + username+"';");
            Set<String> keys = new HashSet<String>();
            while(rs.next()){
                keys.add(rs.getString("key"));
            }
            closeDatabase();
            return keys;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
}