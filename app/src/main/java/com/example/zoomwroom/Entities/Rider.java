package com.example.zoomwroom.Entities;

import java.util.ArrayList;

/**
 * Rider
 *
 * Represents a rider, someone who wants to create a ride request and have a driver pick them up.
 * Stores details related to the rider including rides requested, contact information etc.
 *
 * @see User
 *
 * @version 1.0
 *
 * Feb 26, 2020
 *
 * @author Sharyar Memon
 *
 */
public class Rider extends User {

    /**
     * Stores a list of drives requested by the user
     */
    ArrayList<DriveRequest> ridesRequested;

    /**
     * Empty constructor for class
     */
    public Rider(){}

    /**
     * Constructor for class. Returns an instance of Rider and set the values of name, userName
     * and userID variables.
     *
     * @param name      full name of Rider
     * @param userName  unique username of rider
     * @param userID    unique userID of rider. Currently set as the email of the user as that is
     *                  unique within the firebase auth system.
     */
    public Rider(String name, String userName, String userID) {
        super(name, userName, userID);
    }

    /**
     * Returns an array list of DriveRequests that the user has initiated
     *
     * @return      Arraylist of DriveRequest rider has created
     */
    public ArrayList<DriveRequest> getRidesRequested() {
        return ridesRequested;
    }

}
