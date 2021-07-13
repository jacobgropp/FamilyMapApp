package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jakeg on 3/5/2018.
 */
public class AuthorizationTokenTest {
    @Test
    public void constructorTest() throws Exception {
        AuthorizationToken authToken = new AuthorizationToken("Groppstopper");
        System.out.println(authToken.toString());
        assertNotNull(authToken);
    }
}