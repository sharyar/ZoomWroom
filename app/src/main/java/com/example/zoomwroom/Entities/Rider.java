package com.example.zoomwroom.Entities;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Rider extends User {
    // Declare variables required for class.
    ArrayList<DriveRequest> ridesRequested;


    public Rider(String name, String userName, String userID) {
        super(name, userName, userID);
    }

    public ArrayList<DriveRequest> getRidesRequested() {
        return ridesRequested;
    }

    public void setRidesRequested(ArrayList<DriveRequest> ridesRequested) {
        this.ridesRequested = ridesRequested;
    }
}
