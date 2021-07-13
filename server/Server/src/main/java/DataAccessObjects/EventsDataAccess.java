package DataAccessObjects;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import DataAccessObjects.Database.DatabaseConnection;
import model.Event;

/**
 * Created by jakeg on 2/15/2018.
 *
 * Class to access event data from the database
 */
public class EventsDataAccess extends DatabaseConnection {

    /**
     * This method places the newly created event into the database
     *
     * @param event
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean createEvent(Event event){
        try {
            openDatabase();
            statement = connection.createStatement();
            String sql = "INSERT INTO EVENT (EVENTID,DESCENDANT,PERSONID,LATITUDE,LONGITUDE,COUNTRY,CITY,EVENTTYPE,YEAR) " +
                        "VALUES ('" + event.getEventID() + "', '" + event.getDescendant() + "', '" + event.getPersonID() + "', " +
                        event.getLatitude() + ", " + event.getLongitude() + ", '" + event.getCountry() + "', '" + event.getCity() + "', '" +
                        event.getEventType() + "', '" + event.getYear() + "');";
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
     * This method selects the event attached to the specified
     *
     * @param eventID
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean findEvent(String eventID){
        try{
            openDatabase();
            statement = connection.createStatement();

            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * FROM EVENT WHERE EVENTID='" + eventID +"';");
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
     * Called when a ClearService is made. Deletes the object in the database
     *
     * @return true if successful, false if unsuccessful
     */
    public boolean deleteEvents(){
        try{
            openDatabase();
            //Generates the DELETE statement from the given username.
            statement = connection.createStatement();
            String sql = "DELETE from EVENT;";
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
     * Deletes all events in the database that are connected to the descendant.
     *
     * @param username
     * @return true if successful, false if unsuccessful
     */
    public boolean deleteAllDescendantsEvents(String username){
        try{
            openDatabase();
            //Generates the DELETE statement from the given username.
            statement = connection.createStatement();
            String sql = "DELETE from EVENT where DESCENDANT='" + username + "';";
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
     * Finds and returns a set of events that belong to the given user.
     *
     * @param username
     * @return
     */
    public Set<Event> findAllDescendantsEvents(String username){
        try{
            openDatabase();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT EVENTID from EVENT where DESCENDANT='" + username + "';");

            //Find all personIDs attached to the user and place them in a set.
            Set<String> eventIDs = new HashSet<>();
            while(rs.next()){
                String personID = rs.getString("eventid");
                eventIDs.add(personID);
            }
            Set<Event> events = new HashSet<>();
            for(String eventID : eventIDs){
                Event event = convertDatabaseToJava(eventID);
                events.add(event);
            }
            return events;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    /**
     * Converts the Event data entry into an Event java object
     *
     * @param eventID
     * @return
     */
    public Event convertDatabaseToJava(String eventID){
        String ID = "";
        String descendant = "";
        String personID = "";
        int latitude = 0;
        int longitude = 0;
        String country = "";
        String city = "";
        String eventType = "";
        String year = "";
        try{
            openDatabase();
            statement = connection.createStatement();
            //Builds the SELECT statement
            ResultSet rs = statement.executeQuery( "SELECT * from EVENT where EVENTID='" + eventID +"';" );

            //Finds the Person associated with the given personID and returns a Person java object.
            while(rs.next()) {
                ID = rs.getString("eventid");
                descendant = rs.getString("descendant");
                personID = rs.getString("personID");
                latitude = rs.getInt("latitude");
                longitude = rs.getInt("longitude");
                country = rs.getString("country");
                city = rs.getString("city");
                eventType = rs.getString("eventType");
                year = rs.getString("year");
            }

            //Place the found values into the vent object
            Event event = new Event(descendant, personID);
            event.setEventID(eventID);
            event.setLatitude(latitude);
            event.setLongitude(longitude);
            event.setCountry(country);
            event.setCity(city);
            event.setEventType(eventType);
            event.setYear(year);

            //Close the resultSet and return the event
            rs.close();
            return event;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }
}
