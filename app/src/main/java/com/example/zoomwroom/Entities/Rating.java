package com.example.zoomwroom.Entities;

import java.util.ArrayList;

/**
 * Rating
 *
 * Stores the rating given to a driver by a user. Is stored within a drive request as it is
 * provided by the user at the end of a drive.
 *
 * @see DriveRequest
 *
 * @version 1.0
 *
 * Feb 26, 2020
 *
 * @author Sharyar Memon
 *
 */
public class Rating {
    private boolean thumbStatus; // thumbStatus is true if user gives thumbs up, false if thumbs down
    private DriveRequest drive;
    private Driver driver;
    private Rider rider;

    // Constants for variable thumbStatus
    public static final boolean THUMBSUP = true;
    public static final boolean THUMBSDOWN = false;

    //Constructors
    /**
     * This constructor is the default constructor. We may want to use this in case the rider does
     * not offer a rating
     */
    public Rating() {
        this(true);
    }

    /**
     * This constructor accepts one argument, a boolean indicating thumbs up or down.
     *
     * @param thumbStatus Boolean indicating rider's experience. True for thumbs up and
     *                    false for thumbs down
     */
    public Rating(Boolean thumbStatus) {
        this.thumbStatus = thumbStatus;
    }

    /**
     * This constructor may not get used if we rely on the drive request storing info
     * about the rider and driver rather then linking it to the rating itself.
     *
     * @param thumbStatus   Boolean indicating rider's experience. True for thumbs up and
     *                      false for thumbs down
     * @param driver        Driver for whom the rating is given
     * @param rider         Rider who gave the rating
     */
    public Rating(boolean thumbStatus, Driver driver, Rider rider) {
        this.thumbStatus = thumbStatus;
        this.driver = driver;
        this.rider = rider;
    }

    //<editor-fold desc="Getter & Setter Methods">

    /**
     * Returns the thumbStatus of the rating
     *
     * @return      boolean value of thumbStatus
     */
    public boolean getThumbStatus() {
        return thumbStatus;
    }

    /**
     * Sets the value of thumbStatus of rating. Maybe used if user changes their rating.
     *
     * @param thumbStatus   new boolean value of thumbStatus to set for the rating
     */
    public void setThumbStatus(boolean thumbStatus) {
        this.thumbStatus = thumbStatus;
    }

    /**
     * Returns the drive request associated with the rating
     *
     * @return      DriveRequest associated with a rating. Can be used to retrieve the details of
     *              a drive request when clicking a rating.
     */
    public DriveRequest getDrive() {
        return drive;
    }

    /**
     * Sets the drive request for the rating.
     *
     * @param drive DriveRequest to be set for the rating.
     */
    public void setDrive(DriveRequest drive) {
        this.drive = drive;
    }

    /**
     * Returns the driver associated with the rating
     *
     * @return      Driver instance associated with the rating
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Store an instance of a driver in the rating
     *
     * @param driver    Driver instance to be stored in the rating
     */
    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    /**
     * Returns the rider associated with the rating
     *
     * @return      Rider who gave the rating
     */
    public Rider getRider() {
        return rider;
    }

    /**
     * Set the rider associated with the rating
     *
     * @param rider     Rider instance to be stored in the rating
     */
    public void setRider(Rider rider) {
        this.rider = rider;
    }
    //</editor-fold>
}
