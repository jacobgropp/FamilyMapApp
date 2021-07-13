package com.bignerdranch.android.familymap.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by jakeg on 3/23/2018.
 */

public class Model {

    public static Model model;

    public static Model getModel() {
        if(model == null){
            model = new Model();
        }
        return model;
    }

    /**LOGIN INFORMATION**/
    public String serverHost;
    public String serverPort;
    private String username;
    public String password;

    /**USER DATA**/
    private Person usersPerson;
    private Map<String, Person> persons = new HashMap<String, Person>();
    private Map<String, Event> events = new HashMap<String, Event>();
    private Map<String, TreeSet<Event>> personEvents = new HashMap<String, TreeSet<Event>>();
    private List<String> eventTypes = new ArrayList<String>();
    private Map<String, List<Event>> eventsOfType = new HashMap<String, List<Event>>();
    private Map<String, Float> eventColors = new HashMap<String, Float>();

    /**FILTER SPECIFIC**/
    private List<String> fatherSide = new ArrayList<String>();
    private List<String> motherSide = new ArrayList<String>();

    private Map<String, String> children = new HashMap<String, String>();

    /**RESYNC**/
    private boolean resync = false;

    /**ACTIVITY SPECIFIC**/
    private Person activityPerson;
    private Event activityEvent;

    /**MAP ACTIVITY**/
    private Map<Marker, Event> eventMarkers = new HashMap<>();
    private List<Polyline> polylines;

    /**PERSON ACTIVITY**/
    private HashMap<Integer, Person> mFamilyPersons =  new HashMap<Integer, Person>();
    private HashMap<Integer, Event> mPersonLifeEvents = new HashMap<Integer, Event>();

    /**OPTIONS**/
    private Settings settings = new Settings();

    /**FILTERS**/
    private Filters filters;

    /**
     * Stores the user's specific person at start up
     * @param person
     */
    public void storeUserPerson(Person person){
        this.usersPerson = person;
    }

    /**
     * Stores all persons connected to the user at start up
     * @param responsePersons
     */
    public void storePersons(Set<Person> responsePersons){
        for(Person person : responsePersons){
            persons.put(person.getPersonID(), person);
        }

        if(usersPerson.getFatherID() != null || usersPerson.getMotherID() != null) {
            //Create the map to the father's side
            fatherSide.add(usersPerson.getFatherID());
            storeParentSide(persons.get(usersPerson.getFatherID()), fatherSide);

            //Create the map to the mother's side
            motherSide.add(usersPerson.getMotherID());
            storeParentSide(persons.get(usersPerson.getMotherID()), motherSide);

            //Create a map for children
            storeChildren();
        }
    }

    /**
     * Stores all events connected to the user at start up
     * @param responseEvents
     */
    public void storeEvents(Set<Event> responseEvents){
        //Store Events from database
        for(Event event : responseEvents){
            String eventType = event.getEventType();
            event.setEventType(eventType.toLowerCase());
            events.put(event.getEventID(), event);

            //Store EventTypes
            if(!eventTypes.contains(event.getEventType())){
                eventTypes.add(event.getEventType());
            }

            //Store eventsOfType
            if(!eventsOfType.keySet().contains(event.getEventType())){
                List<Event> initialEvents = new ArrayList<Event>();
                initialEvents.add(event);
                eventsOfType.put(event.getEventType(), initialEvents);
            }
            else{
                eventsOfType.get(event.getEventType()).add(event);
            }
        }
        storeEventColors();
        filters = new Filters();
    }

    /**
     * Assigns specific colors to eventTypes
     */
    private void storeEventColors(){
        //Create a list of colors
        Set<Float> colors = new TreeSet<>();
        colors.add(BitmapDescriptorFactory.HUE_RED); //Red
        colors.add(BitmapDescriptorFactory.HUE_BLUE); //Blue
        colors.add(BitmapDescriptorFactory.HUE_ORANGE); //Orange
        colors.add(BitmapDescriptorFactory.HUE_GREEN); //Green
        colors.add(BitmapDescriptorFactory.HUE_VIOLET); //Violet
        colors.add(BitmapDescriptorFactory.HUE_YELLOW); //Yellow
        colors.add(BitmapDescriptorFactory.HUE_MAGENTA); //Magenta
        colors.add(BitmapDescriptorFactory.HUE_ROSE);//Rose
        colors.add(BitmapDescriptorFactory.HUE_AZURE);//Azure
        colors.add(BitmapDescriptorFactory.HUE_CYAN);//Cyan

        //Iterate over eventTypes and add color
        Iterator<Float> color = colors.iterator();
        for (String type : eventTypes) {
            eventColors.put(type, color.next());
        }
    }

    /**
     * Stores a map where key = personID and value = List of that person's events
     */
    public void storePersonsEvents(){
        for(String personID : persons.keySet()){

            //Create a list that will store the persons specific events
            TreeSet<Event> thisPersonsEvents = new TreeSet<Event>();
            for(Event event : events.values()){
                String eventID = event.getPersonID();

                //Determine if this is the persons event
                if(eventID.equals(personID)){
                    thisPersonsEvents.add(event);
                }
            }
            //Add the events to the specific person
            personEvents.put(personID, thisPersonsEvents);
        }
    }

    /**
     * Stores a mapping of the user's paternal ancestry and faternal ancestry
     * @param person
     * @param list
     */
    private void storeParentSide(Person person, List<String> list) {
        if (persons.get(person.getFatherID()) instanceof Person
                && persons.get(person.getMotherID()) instanceof Person) {

            Person father = persons.get(person.getFatherID());
            Person mother = persons.get(person.getMotherID());

            //Add fathers
            if (!father.equals(null))
                list.add(person.getFatherID());
            storeParentSide(father, list);

            //Add mothers
            if (!mother.equals(null))
                list.add(person.getMotherID());
            storeParentSide(mother, list);
        }
        return;
    }

    /**
     * Stores a mapping of persons and their children
     */
    private void storeChildren(){
       for(Person person : persons.values()){
           if(persons.get(person.getFatherID()) instanceof Person
                   && persons.get(person.getMotherID()) instanceof Person){//person.getFatherID() != null && person.getMotherID() != null){

               //Add child for father
               children.put(persons.get(person.getFatherID()).getPersonID(), person.getPersonID());

               //Add child for mother
               children.put(persons.get(person.getMotherID()).getPersonID(), person.getPersonID());
           }
       }
    }

    /**
     * Clears the client model at logout and re-sync
     */
    public void clear(){
        model = null;
        settings = new Settings();
    }

    /**GETTERS**/
    public Person getUsersPerson() {
        return usersPerson;
    }
    public Map<String, Person> getPersons() {
        return persons;
    }
    public Map<String, Event> getEvents() {
        return events;
    }
    public Map<String, TreeSet<Event>> getPersonEvents() {
        return personEvents;
    }
    public List<String> getEventTypes() {
        return eventTypes;
    }
    public Map<String, Float> getEventColors() {
        return eventColors;
    }
    public List<String> getFatherSide() {
        return fatherSide;
    }
    public List<String> getMotherSide() {
        return motherSide;
    }
    public Map<String, String> getChildren() {
        return children;
    }
    public Person getActivityPerson() {
        return activityPerson;
    }
    public Event getActivityEvent() {
        return activityEvent;
    }
    public HashMap getFamilyPersons() {
        return mFamilyPersons;
    }
    public HashMap getPersonLifeEvents() {
        return mPersonLifeEvents;
    }
    public Map<String, List<Event>> getEventsOfType() {
        return eventsOfType;
    }
    public Settings getSettings() {
        return settings;
    }
    public Filters getFilters() {
        return filters;
    }
    public String getServerHost() {
        return serverHost;
    }
    public String getServerPort() {
        return serverPort;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public boolean getResync() {
        return resync;
    }

    /**SETTERS**/
    public void setActivityPerson(Person person){
        this.activityPerson = person;
    }
    public void setActivityEvent(Event activityEvent) {
        this.activityEvent = activityEvent;
    }
    public void setEventMarkers(Map<Marker, Event> eventMarkers) {
        this.eventMarkers = eventMarkers;
    }
    public void setPolylines(List<Polyline> polylines) {
        this.polylines = polylines;
    }
    public void setFamilyPersons(HashMap<Integer, Person>  familyPersons) {
        mFamilyPersons = familyPersons;
    }
    public void setPersonLifeEvents(HashMap<Integer, Event>  personLifeEvents) {
        mPersonLifeEvents = personLifeEvents;
    }
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setResync(boolean resync) {
        this.resync = resync;
    }
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
