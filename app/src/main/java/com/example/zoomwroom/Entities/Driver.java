package com.example.zoomwroom.Entities;
import java.util.ArrayList;

public class Driver extends User {
    // Declare variables required for class
    private ArrayList<Rating> ratings;
    private ArrayList<DriveRequest> rideRequests;

    /**
     * Constructor
     * @param name
     * @param userName
     * @param userID
     * @param photo
     */
    public Driver(String name, String userName, String userID) {
        super(name, userName, userID);
    }


    //<editor-fold desc="Getter & Setter Methods">
    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public Driver(){}

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    public ArrayList<DriveRequest> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(ArrayList<DriveRequest> rideRequests) {
        this.rideRequests = rideRequests;
    }
    //</editor-fold>
}
