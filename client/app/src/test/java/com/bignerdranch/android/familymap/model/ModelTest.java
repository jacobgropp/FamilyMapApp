package com.bignerdranch.android.familymap.model;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 4/17/2018.
 */
public class ModelTest {

    @Test
    public void storePersons() throws Exception {
        Model.getModel().clear();
        Set<Person> persons = new HashSet<>();
        Person person1 = new Person("Groppstopper", "Jarom", "Gropp");
        person1.setPersonID("1");
        persons.add(person1);
        Person person2 = new Person("Groppstopper", "Jake", "Gropp");
        person2.setPersonID("2");
        persons.add(person2);
        Person person3 = new Person("Groppstopper", "Josh", "Gropp");
        person3.setPersonID("3");
        persons.add(person3);
        Person person4 = new Person("Groppstopper", "Jordan", "Gropp");
        person4.setPersonID("4");
        persons.add(person4);
        Person person5 = new Person("Groppstopper", "Jensine", "Gropp");
        person5.setPersonID("5");
        persons.add(person5);

        Model.getModel().storeUserPerson(person2);

        Model.getModel().storePersons(persons);

        Map<String, Person> personMap = new HashMap<>();
        personMap.put("1", person1);
        personMap.put("2", person2);
        personMap.put("3", person3);
        personMap.put("4", person4);
        personMap.put("5", person5);

        assertEquals(personMap, Model.getModel().getPersons());
    }

    @Test
    public void storeEvents() throws Exception {
        Model.getModel().clear();
        Set<Event> events = new TreeSet<>();
        Event event1 = new Event("Groppstopper", "Jarom");
        event1.setEventID("Jarom");
        event1.setEventType("a");
        event1.setYear("1999");
        events.add(event1);
        Event event2 = new Event("Groppstopper", "Jake");
        event2.setEventID("Jake");
        event2.setEventType("b");
        event2.setYear("2000");
        events.add(event2);
        Event event3 = new Event("Groppstopper", "Josh");
        event3.setEventID("Josh");
        event3.setEventType("c");
        event3.setYear("2001");
        events.add(event3);
        Event event4 = new Event("Groppstopper", "Jordan");
        event4.setEventID("Jordan");
        event4.setEventType("d");
        event4.setYear("2002");
        events.add(event4);
        Event event5 = new Event("Groppstopper", "Jensine");
        event5.setEventID("Jensine");
        event5.setEventType("e");
        event5.setYear("2003");
        events.add(event5);

        Model.getModel().storeEvents(events);

        Map<String, Event> eventMap = new TreeMap<>();
        eventMap.put("Jarom", event1);
        eventMap.put("Jake", event2);
        eventMap.put("Josh", event3);
        eventMap.put("Jordan", event4);
        eventMap.put("Jensine", event5);

        assertEquals(eventMap, Model.getModel().getEvents());

        List<String> eventTypes = new ArrayList<>();
        eventTypes.add("a");
        eventTypes.add("b");
        eventTypes.add("c");
        eventTypes.add("d");
        eventTypes.add("e");

        assertEquals(eventTypes, Model.getModel().getEventTypes());
    }

    @Test
    public void storeEventColors() throws Exception {
        Model.getModel().clear();
        Set<Event> events = new HashSet<>();
        Event event1 = new Event("Groppstopper", "Jarom");
        event1.setEventID("Jarom");
        event1.setEventType("a");
        events.add(event1);
        Event event2 = new Event("Groppstopper", "Jake");
        event2.setEventID("Jake");
        event2.setEventType("b");
        events.add(event2);
        Event event3 = new Event("Groppstopper", "Josh");
        event3.setEventID("Josh");
        event3.setEventType("c");
        events.add(event3);
        Event event4 = new Event("Groppstopper", "Jordan");
        event4.setEventID("Jordan");
        event4.setEventType("d");
        events.add(event4);
        Event event5 = new Event("Groppstopper", "Jensine");
        event5.setEventID("Jensine");
        event5.setEventType("e");
        events.add(event5);

        Model.getModel().storeEvents(events);

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

        Iterator<Float> color = colors.iterator();
        Map<String, Float> colorMap = new HashMap<>();
        for(Event event : events){
            colorMap.put(event.getEventType(),color.next());
        }

        assertEquals(colorMap, Model.getModel().getEventColors());
    }

    @Test
    public void storePersonsEvents() throws Exception {
        Model.getModel().clear();
        Person jake = new Person("Groppstopper", "Jake", "Gropp");

        Model.getModel().storeUserPerson(jake);

        Set<Person> persons = new HashSet<>();
        persons.add(jake);
        Model.getModel().storePersons(persons);

        Set<Event> events = new HashSet<>();
        Event birth = new Event("Groppstopper", jake.getPersonID());
        birth.setEventType("birth");
        birth.setYear("1994");
        Event baptism = new Event("Groppstopper", jake.getPersonID());
        baptism.setEventType("baptism");
        baptism.setYear("2002");
        Event marriage = new Event("Groppstopper", jake.getPersonID());
        marriage.setEventType("marriage");
        marriage.setYear("2025");
        Event death = new Event("Groppstopper", jake.getPersonID());
        death.setEventType("death");
        death.setYear("2075");

        events.add(birth);
        events.add(baptism);
        events.add(marriage);
        events.add(death);
        Model.getModel().storeEvents(events);

        Model.getModel().storePersonsEvents();

        TreeSet<Event> JakeEvents = new TreeSet<>();
        JakeEvents.add(birth);
        JakeEvents.add(baptism);
        JakeEvents.add(marriage);
        JakeEvents.add(death);

        Map<String, TreeSet<Event>> personEvents = new HashMap<>();
        personEvents.put(jake.getPersonID(), JakeEvents);

        assertEquals(personEvents.toString(), Model.getModel().getPersonEvents().toString());
    }

    @Test
    public void storeParentSide() throws Exception {
        Model.getModel().clear();
        Set<Person> persons = new HashSet<>();
        Person person1 = new Person("Groppstopper", "Jake", "Gropp");
        person1.setPersonID("1");
        person1.setFather("2");
        person1.setMother("3");
        persons.add(person1);
        Person person2 = new Person("Groppstopper", "Joe", "Gropp");
        person2.setPersonID("2");
        person2.setFather("4");
        person2.setMother("5");
        persons.add(person2);
        Person person3 = new Person("Groppstopper", "Janice", "Gorringe");
        person3.setPersonID("3");
        person3.setFather("6");
        person3.setMother("7");
        persons.add(person3);
        Person person4 = new Person("Groppstopper", "Les", "Gropp");
        person4.setPersonID("4");
        persons.add(person4);
        Person person5 = new Person("Groppstopper", "Sherry", "Remmers");
        person5.setPersonID("5");
        persons.add(person5);
        Person person6 = new Person("Groppstopper", "Mark", "Gorringe");
        person6.setPersonID("6");
        persons.add(person6);
        Person person7 = new Person("Groppstopper", "Nadine", "Bair");
        person7.setPersonID("7");
        persons.add(person7);

        Model.getModel().storeUserPerson(person1);
        Model.getModel().storePersons(persons);

        List<String> paternalAncestry = new ArrayList<>();
        paternalAncestry.add("2");
        paternalAncestry.add("4");
        paternalAncestry.add("5");

        List<String> faternalAncestry = new ArrayList<>();
        faternalAncestry.add("3");
        faternalAncestry.add("6");
        faternalAncestry.add("7");

        assertEquals(paternalAncestry, Model.getModel().getFatherSide());

        assertEquals(faternalAncestry, Model.getModel().getMotherSide());
    }

    @Test
    public void storeChildren() throws Exception {
        Model.getModel().clear();
        Set<Person> persons = new HashSet<>();
        Person person1 = new Person("Groppstopper", "Jake", "Gropp");
        person1.setPersonID("1");
        person1.setFather("2");
        person1.setMother("3");
        persons.add(person1);
        Person person2 = new Person("Groppstopper", "Joe", "Gropp");
        person2.setPersonID("2");
        person2.setFather("4");
        person2.setMother("5");
        persons.add(person2);
        Person person3 = new Person("Groppstopper", "Janice", "Gorringe");
        person3.setPersonID("3");
        person3.setFather("6");
        person3.setMother("7");
        persons.add(person3);
        Person person4 = new Person("Groppstopper", "Les", "Gropp");
        person4.setPersonID("4");
        persons.add(person4);
        Person person5 = new Person("Groppstopper", "Sherry", "Remmers");
        person5.setPersonID("5");
        persons.add(person5);
        Person person6 = new Person("Groppstopper", "Mark", "Gorringe");
        person6.setPersonID("6");
        persons.add(person6);
        Person person7 = new Person("Groppstopper", "Nadine", "Bair");
        person7.setPersonID("7");
        persons.add(person7);

        Model.getModel().storeUserPerson(person1);
        Model.getModel().storePersons(persons);

        Map<String, String> children = new HashMap<>();
        children.put("2", "1"); //Joe to Jake
        children.put("3", "1"); //Janice to Jake
        children.put("4", "2"); //Les to Joe
        children.put("5", "2"); //Sherry to Joe
        children.put("6", "3"); //Mark to Janice
        children.put("7", "3"); //Nadine to Janice

        assertEquals(children, Model.getModel().getChildren());
    }
}