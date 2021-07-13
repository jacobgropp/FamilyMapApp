package DataAccessObjects;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import DataAccessObjects.Database.DatabaseConnection;
import model.Person;

/**
 * Created by jakeg on 2/15/2018.
 *
 */
public class PersonDataAccess extends DatabaseConnection {
    //Commands
    /**
     * This method adds a person to place in the database.
     *
     * @return a created event
     */
    public boolean createPerson(Person person){
        try {
            openDatabase();
            statement = connection.createStatement();
            String spouseID = null;
            String fatherID = null;
            String motherID = null;
            if(person.getSpouseID() != null){
                spouseID = person.getSpouseID();
            }
            if(person.getFatherID() != null){
                fatherID = person.getFatherID();
            }
            if(person.getMotherID() != null){
                motherID = person.getMotherID();
            }
            String sql =
                    "INSERT INTO PERSON (PERSONID,DESCENDANT,FIRSTNAME,LASTNAME,GENDER,SPOUSEID,FATHERID,MOTHERID) " +
                            "VALUES ('" + person.getPersonID() + "', '" + person.getDescendant() + "', '" + person.getFirstName() + "', '" + person.getLastName() + "', '" + person.getGender() +
                            "', '" + spouseID + "', '" + fatherID + "', '" + motherID + "');";
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
     * This method reads the specified person within the Database.
     *
     * @return the found person. Null if not found.
     */
    public boolean findPerson(String personID){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * FROM PERSON WHERE PERSONID='" + personID +"';");
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
     * Called when a ClearService is made. Deletes the given object in the database
     *
     * @return boolean
     */
    public boolean deletePersons(){
        try{
            openDatabase();

            //Generates the DELETE statement from the given username.
            statement = connection.createStatement();
            String sql = "DELETE from PERSON;";
            statement.executeUpdate(sql);

            //connection.commit();
            closeDatabase();
            return true;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    /**
     * Deletes all Persons associated with the given user except for the Person
     * representing the user
     *
     * @param username
     * @return true if all descendants of the given person are deleted.
     */
    public boolean deleteDescendantsPersons(String username){
        try{
            openDatabase();

            //Generates the DELETE statement from the given username.
            statement = connection.createStatement();
            String sql = "DELETE from PERSON where DESCENDANT='" + username + "';";
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
     * Finds and returns all persons attached to the given username
     *
     * @param username
     * @return
     */
    public Set<Person> findAllDescendantsPersons(String username){
        try{
            openDatabase();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT PERSONID from PERSON where DESCENDANT='" + username + "';");

            //Find all personIDs attached to the user and place them in a set.
            Set<String> personIDs = new HashSet<String>();
            while(rs.next()){
                String personID = rs.getString("personID");
                personIDs.add(personID);
            }
            Set<Person> persons = new HashSet<Person>();
            for(String personID : personIDs){
                Person person = convertDatabaseToJava(personID);
                persons.add(person);
            }
            return persons;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    /**
     * Converts a data entry from the PERSON table into a java object
     * @param personID
     * @return
     */
    public Person convertDatabaseToJava(String personID){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * from PERSON where PERSONID='" + personID +"';" );

            //Finds the Person associated with the given personID and returns a Person java object.
            String ID = "";
            String descendant = "";
            String firstName = "";
            String lastName = "";
            String gender = "";
            String fatherID = "";
            String motherID = "";
            String spouseID = "";
            while(rs.next()){
                 ID = rs.getString("personid");
                 descendant = rs.getString("descendant");
                 firstName = rs.getString("firstName");
                 lastName = rs.getString("lastName");
                 gender = rs.getString("gender");
                 fatherID = rs.getString("fatherID");
                 motherID = rs.getString("motherID");
                 spouseID = rs.getString("spouseID");
            }
            Person person = new Person(descendant, firstName, lastName);
            person.setPersonID(ID);
            person.setGender(gender);
            person.setFather(fatherID);
            person.setMother(motherID);
            person.setSpouse(spouseID);
            return person;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

}
