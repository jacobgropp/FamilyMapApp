package model;

import java.util.UUID;

/**
 *A Date consisting of a day, month, and year.
 *<pre>
 *<b>Domain</b>:
 *     eventID : String
 *     descendent : user
 *     personID : String
 *     latitude: String
 *     longitude: String
 *     country: String
 *     city: String
 *     eventType: String
 *     year: String
 *
 *     <b>Invariant</b>:
 *     latitude: [two or three digit]&deg
 *     longitude: [two or three digit]&deg
 *     Country: a single country from a list of countries
 *     city: a single city from a list of cities
 *     EventType: a single eventType from a list of EVENTTYPES
 *     Year: a 4 digit number stored in a String
 *
 */
public class Event {

    //private variables
    private String eventID;
    private String descendant;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private String year;

    //CONSTRUCTOR
    /**
     * The single constructor for the event
     * @param descendant the user connected to the event
     * @param personID the ID of the person connected to the event
     */
    public Event(String descendant, String personID){
        eventID = UUID.randomUUID().toString();
        this.descendant = descendant;
        this.personID = personID;
        latitude = 0.00;
        longitude = 0.00;
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
    public String getDescendant() {
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
    public double getLongitude() {
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
    /**
     *The toString method for an event.
     *@return A string representation of a person that satisfies the following
     *format: event[evenID: [ID],
     *     descendent: [descendent],
     *     personID: [personID],
     *     latitude: [latitude],
     *     longitude: [longitude],
     *     country: [country],
     *     city: [city],
     *     eventType: [eventType],
     *     year: [year]
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
    //SETTERS

    /**
     * The setter for the field eventID
     * @param eventID
     */
    public void setEventID(String eventID){
        this.eventID = eventID;
    }
    /**
     * The setter for the field personID
     * @param personID
     */
    public void setPersonID(String personID){
        this.personID = personID;
    }
    /**
     *The setter for the field latitude.
     *@param latitude
     */
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    /**
     *The getter for the field longitude.
     *@param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    /**
     *The setter for the field country.
     *@param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     *The setter for the field city.
     *@param city
     */
    public void setCity(String city){
        this.city = city;
    }
    /**
     * The setter for the field eventType
     * @param eventType
     */
    public void setEventType(String eventType){
        this.eventType = eventType;
    }
    /**
     *The setter for the field year.
     *@param year
     */
    public void setYear(String year) {
        this.year = year;
    }
}