package DataAccessObjects.Database;
import java.sql.*;

/**
 * Created by jakeg on 3/3/2018.
 *
 * Opens and closes database connections.
 *
 * This class is utilized by all DatabaseAccessObjects in the Family Map Server.
 */

public class DatabaseConnection {
    public Connection connection = null;
    public Statement statement = null;
    /**
     * opens the database
     */
    public void openDatabase(){
        try {
            Class.forName("org.sqlite.JDBC");
            //Connects to the database
            connection = DriverManager.getConnection("jdbc:sqlite:FamilyMap.db");
            //System.out.println("Opened database successfully");
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public void createTables(){
        try {
            Class.forName("org.sqlite.JDBC");
            statement = connection.createStatement();
            //Create AUTHORIZATIONTOKEN table
            String sql = "CREATE TABLE if NOT exists `AUTHORIZATIONTOKEN` (\n" +
                    "\t`USER`\tTEXT NOT NULL,\n" +
                    "\t`KEY`\tTEXT NOT NULL UNIQUE,\n" +
                    "\tPRIMARY KEY(`KEY`));\n" +

            //Create USER table
                    "CREATE TABLE if NOT exists `USER`(\n" +
                    "\t`USERNAME`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`PASSWORD`\tTEXT NOT NULL,\n" +
                    "\t`EMAIL`\tTEXT NOT NULL,\n" +
                    "\t`PERSONID`\tTEXT NOT NULL UNIQUE,\n" +
                    "\tPRIMARY KEY('USERNAME'));\n" +

            //Create PERSON table
                    "CREATE TABLE if NOT exists `PERSON` (\n" +
                    "\t`PERSONID`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`DESCENDANT`\tTEXT NOT NULL,\n" +
                    "\t`FIRSTNAME`\tTEXT NOT NULL,\n" +
                    "\t`LASTNAME`\tTEXT NOT NULL,\n" +
                    "\t`GENDER`\tTEXT NOT NULL,\n" +
                    "\t`SPOUSEID`\tTEXT,\n" +
                    "\t`FATHERID`\tTEXT,\n" +
                    "\t`MOTHERID`\tTEXT,\n" +
                    "\tPRIMARY KEY(`PERSONID`));" +

            //Create EVENT table
                    "CREATE TABLE if NOT exists `EVENT` (\n" +
                    "\t`EVENTID`\tTEXT NOT NULL UNIQUE,\n" +
                    "\t`DESCENDANT`\tTEXT NOT NULL,\n" +
                    "\t`PERSONID`\tTEXT NOT NULL,\n" +
                    "\t`LATITUDE`\tINTEGER,\n" +
                    "\t`LONGITUDE`\tINTEGER,\n" +
                    "\t`COUNTRY`\tTEXT NOT NULL,\n" +
                    "\t`CITY`\tTEXT NOT NULL,\n" +
                    "\t`EVENTTYPE`\tTEXT NOT NULL,\n" +
                    "\t`YEAR`\tTEXT,\n" +
                    "\tPRIMARY KEY('EVENTID'));";
            statement.executeUpdate(sql);
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    /**
     * Closes the database
     */
    public void closeDatabase(){
        try {
            //Closes the database connection
            statement.close();
            connection.close();
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
