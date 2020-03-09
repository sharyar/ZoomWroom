package com.example.zoomwroom.Entities;

public class User {

    // Declare variables required for class.
    private String userName;
    private String userID;
    private ContactInformation contactDetails;
    private float balance;

    /**
     * Empty constructor, required by firebase.
     */
    public User(){ }

    /**
     * Constructor for class. Returns an instance of User based on provided parameters.
     * @param userName String Full name of user.
     * @param userID String unique userID used to reference the user within the system.
     */
    public User(String userName, String userID) {
        this.userName = userName;
        this.userID = userID;
    }

    //<editor-fold desc="Getters & setters">
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ContactInformation getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactInformation contactDetails) {
        this.contactDetails = contactDetails;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
    //</editor-fold>

    public float addBalance(float amount) {
        balance += amount;
        return balance;
    }
}
