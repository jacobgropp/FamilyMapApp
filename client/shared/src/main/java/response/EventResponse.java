
package response;
/**
 * Created by jakeg on 2/16/2018.
 *
 * the response that stores all vital information pertaining to an event service for a singular event
 */
public class EventResponse {
    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private String year;
    //Constructor
    /**
     *     eventID : String
     *     descendent : user
     *     personID : String
     *     latitude: String
     *     longitude: String
     *     country: String
     *     city: String
     *     eventType: String
     *     year: String
     */
    public EventResponse(String eventID, String descendant, String personID){
        this.eventID = eventID;
        this.descendant = descendant;
        this.personID = personID;
    }

    //GETTERS
    /**
     *The getter for the field eventID.
     *@return the eventID
     */
    public String getEventID(){
        return eventID;
    }
    /**
     *The getter for the field descendent.
     *@return the user descendent
     */
    public String getDescendent() {
        return descendant;
    }
    /**
     *The getter for the field personID.
     *@return the personID
     */
    public String getPersonID(){
        return personID;
    }
    /**
     *The getter for the field latitude.
     *@return the latitude
     */
    public double getLatitude(){
        return latitude;
    }
    /**
     *The getter for the field longitude.
     *@return the longitude
     */
    public double getLongitute() {
        return longitude;
    }
    /**
     *The getter for the field country.
     *@return the country
     */
    public String getCountry() {
        return country;
    }
    /**
     *The getter for the field city.
     *@return the city
     */
    public String getCity(){
        return city;
    }
    /**
     *The getter for the field eventType.
     *@return the eventType
     */
    public String getEventType() {
        return eventType;
    }
    /**
     *The getter for the field year.
     *@return the year
     */
    public String getYear() {
        return year;
    }

    //SETTERS
    /**
     *
     * @param eventID
     */
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }
    /**
     *
     * @param descendant
     */
    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }
    /**
     *
     * @param personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    /**
     *
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    /**
     *
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     *
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     *
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    /**
     *
     * @param year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * the toString for the EventResponse object
     * @return toString
     */
    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();
        newString.append("Event{" +
                "eventID: " + eventID + ", " +
                "descendant: " + descendant + ", " +
                "personID: " + personID + ", " +
                "eventType: " + eventType + ", latitude: " + latitude +
                ", longitude: " + longitude +
                ", country: " + country +
                ", city: " + city +
                ", year: " + year + "}");
        return newString.toString();
    }
}
