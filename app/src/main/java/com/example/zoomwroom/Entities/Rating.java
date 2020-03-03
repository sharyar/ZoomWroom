package com.example.zoomwroom.Entities;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Rating {
    // Declare variables required for class
    private boolean thumbStatus;

    // These may not be required as the DriveRequest can HAVE the rating which in turn have the driver and rider.
    private DriveRequest drive;
    private Driver driver;
    private Rider rider;

    //Constructors

    /**
     * This constructor is the default constructor. We may want to use this in case the rider does
     * not offer a rating.
     */
    public Rating() {
        this(true);
    }

    /**
     * This constructor accepts one argument, a boolean indicating thumbs up or down.
     * @param thumbStatus Boolean indicating rider's experience. True for thumbs up and
     *                    false for thumbs down.
     */
    public Rating(Boolean thumbStatus) {
        this.thumbStatus = thumbStatus;
    }

    /**
     * This constructor may not get used if we rely on the drive request storing info
     * about the rider and driver rather then linking it to the rating itself.
     * @param thumbStatus
     * @param driver
     * @param rider
     */
    public Rating(boolean thumbStatus, Driver driver, Rider rider) {
        this.thumbStatus = thumbStatus;
        this.driver = driver;
        this.rider = rider;
    }

    public void giveRating(Boolean thumbStatus, Driver driver, Rider rider) {
        setThumbStatus(thumbStatus);
        setDriver(driver);
        setRider(rider);
    }


    /**
     * Returns an array list of Strings showing ratings from a list of Ratings provided as an argument
     * It is meant to be used to display the ratings of a driver on their profile.
     * @param listOfRatings ArrayList of Ratings.
     * @return ArrayList of strings with ratings given to driver.
     */
    public static ArrayList<String> showRatings(ArrayList<Rating> listOfRatings) {
        ArrayList<String> allRatingsForDriver = new ArrayList<>();
        for (Rating r: listOfRatings){
            StringBuilder s = new StringBuilder();
            s.append(r.getRider().getUserName());
            s.append(": ");
            if (r.thumbStatus)
                s.append("Thumbs Up");
            else
                s.append("Thumbs Down");
            allRatingsForDriver.add(s.toString());
        }
        return allRatingsForDriver;
    }

    //<editor-fold desc="Getter & Setter Methods">
    public boolean getThumbStatus() {
        return thumbStatus;
    }

    public void setThumbStatus(boolean thumbStatus) {
        this.thumbStatus = thumbStatus;
    }

    public DriveRequest getDrive() {
        return drive;
    }

    public void setDrive(DriveRequest drive) {
        this.drive = drive;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }
    //</editor-fold>
}
