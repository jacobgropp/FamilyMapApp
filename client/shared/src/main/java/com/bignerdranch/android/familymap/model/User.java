package com.bignerdranch.android.familymap.model;

import java.util.UUID;

/**
 * Created by jakeg on 2/15/2018.
 *
 * A user within the Family Map Server
 *
 */
public class User {

    //private variables
    private String userName;
    private String password;
    private String email;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;

    //Constructor
    /**
     * The single constructor for the user
     *
     * @param username the new username for the user
     * @param password the new username for the user
     */
    public User(String username, String password){
        this.userName = username;
        this.password = password;
        personID = UUID.randomUUID().toString();
    }
    /**
     *The getter for the field username.
     *@return the username
     */
    public String getUsername() {
        return userName;
    }
    /**
     *The getter for the field Password.
     *@return the Password
     */
    public String getPassword() {
        return password;
    }
    /**
     *The getter for the field email.
     *@return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     *The getter for the field firstName.
     *@return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     *The getter for the field lastName.
     *@return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     *The getter for the field gender.
     *@return the gender
     */
    public String getGender() {
        return gender;
    }
    /**
     *The getter for the field ID.
     *@return the ID
     */
    public String getPersonID() {
        return personID;
    }
    //Setters
    /**
     *The setter for the field password.
     *@param password the new password for this user
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     *The setter for the field email.
     *@param email the new email for this user
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     *The setter for the field firstName.
     *@param firstName the new email for this user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     *The setter for the field lastName.
     *@param lastName the new email for this user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     *The setter for the field gender.
     *@param gender the new email for this user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    /**
     *The setter for the field personID.
     *@param personID the new email for this user
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    //toString
    /**
     *The toString method for a User.
     *@return A string representation of a user that satisfies the following
     *format: user{username: [username],
     *        password: [password],
     *        email: [email],
     *        ID: [ID]],
     *        firstName: [firstName],
     *        lastName: [lastName],
     *        gender: [gender]}
     */
    @Override
    public String toString() {
        StringBuilder newString = new StringBuilder();
        newString.append(
                "user{" +
                        "username: " + userName +
                        ", password: " + password +
                        ", email: " + email +
                        ", personID: " + personID +
                        ", firstName: " + firstName +
                        ", lastName: " + lastName +
                        ", gender: " + gender + "}");
        return newString.toString();
    }
}