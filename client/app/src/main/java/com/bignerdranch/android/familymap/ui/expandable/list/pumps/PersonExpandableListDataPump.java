package com.bignerdranch.android.familymap.ui.expandable.list.pumps;

import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Model;
import com.bignerdranch.android.familymap.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class PersonExpandableListDataPump {

    private HashMap mFamilyPersons =  new HashMap<Integer, Person>();

    private HashMap mPersonLifeEvents = new HashMap<Integer, Event>();

    private HashMap<String, List<String>> mExpandableListDetail = new HashMap<String, List<String>>();

    public void getData(Person person) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> lifeEvents = findLifeEvents(person);

        List<String> family = findFamily(person);

        //Add the person's life events to titled list "LIFE EVENTS"
        mExpandableListDetail.put("LIFE EVENTS", lifeEvents);
        mExpandableListDetail.put("FAMILY", family);

        //Add the newly created HashMaps to the model class
        System.out.println("Family persons: " + mFamilyPersons.toString());
        System.out.println("Life Events: " + mPersonLifeEvents.toString());
        Model.getModel().setFamilyPersons(mFamilyPersons);
        Model.getModel().setPersonLifeEvents(mPersonLifeEvents);
    }

    private List<String> findLifeEvents(Person person){
        //Pull the personEvents from the model
        TreeSet<Event> personEvents = Model.getModel().getPersonEvents().get(person.getPersonID());

        int iterator = 0;
        //Loop through the map and find all the person's life events
        List<String> lifeEvents = new ArrayList<String>();
        for(Event event : personEvents){
            if(Model.getModel().getFilters().getEventFilter().get(event.getEventType())) {
                String lifeEvent = event.getEventType() + ": " +
                        event.getCity() + ", " + event.getCountry();
                if (event.getYear().equals("") || event.getYear().equals(null)) {
                    lifeEvent = lifeEvent + " N/A\n";
                } else {
                    lifeEvent = lifeEvent + " (" + event.getYear() + ")\n";
                }

                lifeEvent = lifeEvent + person.getFirstName() + " " + person.getLastName();

                mPersonLifeEvents.put(iterator, event);
                iterator++;
                lifeEvents.add(lifeEvent);
            }
        }

        return lifeEvents;
    }

    private List<String> findFamily(Person person){
        //Create a list to store the family members
        List<String> family = new ArrayList<String>();

        int iterator = 0;
        //Pull all persons to find the parents and spouse
        Map<String, Person> persons = Model.getModel().getPersons();
        Person father = persons.get(person.getFatherID());
        if(father != null) {
            String fatherString = father.getFirstName() + " "
                    + father.getLastName() + "\n" + "Father";
            family.add(fatherString);
            mFamilyPersons.put(iterator, father);
            iterator++;
        }
        Person mother = persons.get(person.getMotherID());
        if(mother != null) {
            String motherString = mother.getFirstName() + " "
                    + mother.getLastName() + "\n" + "Mother";
            family.add(motherString);
            mFamilyPersons.put(iterator, mother);
            iterator++;
        }
        Person spouse = persons.get(person.getSpouseID());
        if (spouse != null) {
            String spouseString = spouse.getFirstName() + " "
                    + spouse.getLastName() + "\n" + "Spouse";
            family.add(spouseString);
            mFamilyPersons.put(iterator, spouse);
            iterator++;
        }

        //Pull all the children to find the child if any
        Map<String, String> children = Model.getModel().getChildren();
        Person child = persons.get(children.get(person.getPersonID()));
        if(child != null) {
            String childString = child.getFirstName() + " "
                    + child.getLastName() + "\n" + "Child";
            family.add(childString);
            mFamilyPersons.put(iterator, child);
            iterator++;
        }

        return family;
    }

    /**GETTERS**/
    public HashMap getFamilyPersons() {
        return mFamilyPersons;
    }

    public HashMap getPersonLifeEvents() {
        return mPersonLifeEvents;
    }

    public HashMap<String, List<String>> getExpandableListDetail() {
        return mExpandableListDetail;
    }
}
