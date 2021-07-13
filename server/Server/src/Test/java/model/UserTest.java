package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/5/2018.
 */
public class UserTest {
    @Test
    public void constructorTest() throws Exception {
        User user = new User("Groppstopper", "password");
        user.setEmail("jakegropp@gmail.com");
        System.out.println(user.toString());
        assertNotNull(user);
    }

}