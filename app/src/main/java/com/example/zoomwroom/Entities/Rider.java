package com.example.zoomwroom.Entities;

import java.util.ArrayList;

/**
 * Useless class warning
 */
public class Rider extends User {
    // Declare variables required for class.
    ArrayList<DriveRequest> ridesRequested;

    public Rider(){}
    public Rider(String name, String userName, String userID) {
        super(name, userName, userID);
    }

    public ArrayList<DriveRequest> getRidesRequested() {
        return ridesRequested;
    }

}
