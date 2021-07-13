package com.bignerdranch.android.familymap.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 4/17/2018.
 */
public class FiltersTest {
    @Test
    public void changeEventFilter() throws Exception {
        Model.getModel().clear();
        //The Map expected to return
        Map<String, Boolean> eventTypeFilters = new HashMap<>();
        eventTypeFilters.put("birth", true);
        eventTypeFilters.put("baptism", true);
        eventTypeFilters.put("marriage", true);
        eventTypeFilters.put("death", true);

        //The set of events
        Set<Event> events = new HashSet<>();
        Event event1 = new Event("Groppstopper", "Jake");
        event1.setEventType("birth");
        event1.setYear("1994");
        events.add(event1);
        Event event2 = new Event("Groppstopper", "Jake");
        event2.setEventType("baptism");
        event2.setYear("2002");
        events.add(event2);
        Event event3 = new Event("Groppstopper", "Jake");
        event3.setEventType("marriage");
        event3.setYear("2025");
        events.add(event3);
        Event event4 = new Event("Groppstopper", "Jake");
        event4.setEventType("death");
        event4.setYear("2085");
        events.add(event4);

        //Store events in the client model
        Model.getModel().storeEvents(events);

        Filters filters = new Filters();

        //Assert the original mapping is correct at start up
        assertEquals(filters.getEventFilter(), eventTypeFilters);

        //Change a value in the original array
        eventTypeFilters.remove("marriage");
        eventTypeFilters.put("marriage", false);

        filters.changeEventFilter("marriage");

        assertEquals(filters.getEventFilter(), eventTypeFilters);
    }

}