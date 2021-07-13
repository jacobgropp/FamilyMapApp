package model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/5/2018.
 */
public class EventTest {
    @Test
    public void constructorTest() throws Exception {
        Event event = new Event("Groppstopper", UUID.randomUUID().toString());
        System.out.println(event.toString());
        assertNotNull(event);
    }
}