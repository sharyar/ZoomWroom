package com.example.zoomwroom.Entities;

import java.util.ArrayList;

/**
 * Driver
 *
 * Stores a driver's personal information including name, ratings, currentRequest, userName and
 * userID (email address)
 *
 * V1.0
 *
 * Date: Feb 26, 2020
 *
 */
public class Driver extends User {
    // Declare variables required for class
    private ArrayList<Rating> ratings; // stores an array of ratings given to driver
    private DriveRequest currentRequest; // stores the currentRequest the driver is involved in

    /**
     * Constructor for class. Returns an instance of Driver
     *
     * @param name      Full name of driver
     * @param userName  unique userName of the driver
     * @param userID    unique userID of the user. We are currently using their email address
     */
    public Driver(String name, String userName, String userID) {
        super(name, userName, userID);
        ratings = new ArrayList<>();
    }

    /**
     * Empty Constructor for class. Returns an instance of driver. Does not require any arguments
     */
    public Driver(){}

    //<editor-fold desc="Getter & Setter">
    /**
     * Returns a list of ratings of the driver. Will be used to display the cumulative ratings
     * in an activity
     *
     * @return      array list of ratings for the driver object
     */
    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    /**
     * Sets the ratings of the drivers via an array list of ratings
     *
     * @param ratings   ArrayList of ratings
     */
    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    /**
     * Adds a rating to the current ArrayList of the driver
     *
     * @param rating    the rating to be added to the instance's ArrayList of ratings
     */
    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }

    /**
     * Gets the current DriveRequest the driver is involved in.
     *
     * @return      the current DriveRequest the driver is involved in.
     */
    public DriveRequest getCurrentRequest() {
        return currentRequest;
    }

    /**
     * Sets the current request the driver is involved in so it can be referenced for use.
     *
     * @param currentRequest    DriveRequest the driver is currently assigned to or involved in
     */
    public void setCurrentRequest(DriveRequest currentRequest) {
        this.currentRequest = currentRequest;
    }
    //</editor-fold>
}
