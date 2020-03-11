package com.example.zoomwroom.Entities;

/**
 * Useless class warning
 */
public class Rider extends User {
    /**
     * Empty constructor, required by firebase
     */
    public Rider() { }

    public Rider(String name, String userName, String userID) {
        super(name, userName, userID);
    }
}
