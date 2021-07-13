package DataAccessObjects;

import java.sql.ResultSet;

import DataAccessObjects.Database.DatabaseConnection;
import model.User;

/**
 * Created by jakeg on 2/15/2018.
 *
 *
 */
public class UserDataAccess extends DatabaseConnection {

    /**
     * Creates a new user to place in the database
     *
     * @param user
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean createUser(User user){
        try {
            openDatabase();
            statement = connection.createStatement();
            String sql =
                    "INSERT INTO USER (USERNAME,PASSWORD,EMAIL,PERSONID) " +
                        "VALUES ('" + user.getUsername() + "', '" + user.getPassword() +
                            "', '" + user.getEmail() + "', '" + user.getPersonID() + "');";
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
     * Determines if the user exists
     *
     * @param username
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean findUsername(String username){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * from USER where USERNAME='" + username + "';");
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
     * Authenticates the password to log the user in
     * @param username
     * @param password
     * @return
     */
    public boolean authenticatePassword(String username, String password){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * from USER where USERNAME='" + username + "' and PASSWORD='" + password + "';");
            boolean passwordFound = rs.next();
            rs.close();
            closeDatabase();
            return passwordFound;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    /**
     * Called when a ClearService is made. Deletes the object in the database
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean deleteUsers(){
        try{
            openDatabase();

            //Generates the DELETE statement from the given username.
            statement = connection.createStatement();
            String sql = "DELETE from USER;";
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
     * Retrieves the personID from the user data entry.
     * @param username
     * @return
     */
    public String getPersonID(String username){
        String personID = "";
        try{
            openDatabase();
            statement = connection.createStatement();
            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT PERSONID from USER where USERNAME='" + username +"';");

            //Finds the personID and returns it
            while(rs.next()){
                personID = rs.getString("personid");
            }
            closeDatabase();
            return personID;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return personID;
        }
    }
}