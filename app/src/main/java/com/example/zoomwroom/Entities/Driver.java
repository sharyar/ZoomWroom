package com.example.zoomwroom.Entities;

import java.util.ArrayList;

public class Driver extends User {
    // Declare variables required for class
    private ArrayList<Rating> ratings;
    private DriveRequest currentRequest;

    /**
     * Constructor
     * @param userName
     * @param userID
     */
    public Driver(String name, String userName, String userID) {
        super(name, userName, userID);
    }

    //<editor-fold desc="Getter & Setter">
    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }

    public DriveRequest getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(DriveRequest currentRequest) {
        this.currentRequest = currentRequest;
    }
    //</editor-fold>
}
