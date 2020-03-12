/**
 * Examples:
 * User currentUser = LocalData.instance.getCurrentUser();
 * LocalData.instance.setCurrentUser(user);
 * LocalData.instance.updateCurrentUser();
 */


package com.example.zoomwroom;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.User;

import java.util.ArrayList;

public class LocalData {
    public static LocalData instance = new LocalData();

    private User currentUser;
    private String currentUserID;
    private ArrayList<DriveRequest> currentRequests;
    private DriveRequest currentRequest;

    //<editor-fold desc="getters & setters">
    public User getCurrentUser() {
        if (currentUser == null) {
            updateCurrentUser();
        }
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getCurrentUserID() {
        return currentUserID;
    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    public ArrayList<DriveRequest> getCurrentRequests() {
        updateCurrentRequests();
        return currentRequests;
    }

    public void setCurrentRequests(ArrayList<DriveRequest> currentRequests) {
        this.currentRequests = currentRequests;
    }

    public DriveRequest getCurrentRequest() {
        return currentRequest;
    }

    public void setCurrentRequest(DriveRequest currentRequest) {
        this.currentRequest = currentRequest;
    }
    //</editor-fold>

    public boolean updateCurrentUser() {
        //currentUser = MyDatabase.getUser(currentUserID);
        if (User.userNotFound(currentUser)) {
            return false;
        }
        return true;
    }

    public boolean updateCurrentRequests() {
        if (currentRequests == null) {
            //currentRequests = MyDatabase.getDriveRequests(currentUserID);
        }
        return true;
    }
}
