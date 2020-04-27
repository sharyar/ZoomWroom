package com.example.zoomwroom.Entities;

/**
 * Driver
 *
 * Stores a driver's personal information including name, ratings, currentRequest, userName and
 * userID (email address)
 *
 * @version 1.1
 *
 * Date: Feb 26, 2020
 *
 * @author Sharyar Memon
 */
public class Driver extends User {
    // Declare variables required for class
    private Rating rating; // stores ratings for drivers

    /**
     * Constructor for class. Returns an instance of Driver
     *
     * @param name      Full name of driver
     * @param userName  unique userName of the driver
     * @param userID    unique userID of the user. We are currently using their email address
     */
    public Driver(String name, String userName, String userID) {
        super(name, userName, userID);
        rating = new Rating();
    }

    /**
     * Empty Constructor for class. Returns an instance of driver. Does not require any arguments
     */
    public Driver(){}

    //<editor-fold desc="Getter & Setter">
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    //</editor-fold>
}
