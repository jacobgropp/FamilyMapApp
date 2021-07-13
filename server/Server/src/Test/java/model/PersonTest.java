package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/5/2018.
 */
public class PersonTest {
    @Test
    public void constructorTest() throws Exception {
        Person person = new Person("Groppstopper", "Jake", "Gropp");
        System.out.println(person.toString());
        assertNotNull(person);
    }

}