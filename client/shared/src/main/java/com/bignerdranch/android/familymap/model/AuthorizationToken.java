package com.bignerdranch.android.familymap.model;

import java.util.UUID;

/**
 * Created by jakeg on 2/15/2018.
 *
 * An authorizationToken in the FMS
 *
 * A unique key that is attached to a specific user
 *
 * user: the specific user attached to this authorization token
 * key: the unique key used to login to the FMS
 *
 */
public class AuthorizationToken {
    private String user;
    private String key;
    //Constructor
    /**
     * The single constructor for the authToken
     * @param user the user connected to the authToken
     */
    public AuthorizationToken(String user){
        this.user = user;
        key = UUID.randomUUID().toString();
    }
    /**
     *The getter for the field user.
     *@return the user
     */
    public String getUser(){
        return user;
    }
    /**
     *The getter for the field key.
     *@return the authorization key
     */
    public String getKey() {
        return key;
    }
    /**
     *The toString method for an event.
     *@return A string representation of a person that satisfies the following
     *format: authorizationToken[user: [user.username], key: [key]]
     *
     */
    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();
        newString.append("AuthorizationToken{ " +
                        "User: " + user +
                        ", Key: " + key + "}");
        return newString.toString();
    }
    //Commands
    /**
     * the setter for the field key
     * @param newKey
     */
    public void setKey(String newKey){
        this.key = newKey;
    }
}

