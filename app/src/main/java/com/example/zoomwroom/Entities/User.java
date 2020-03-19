package com.example.zoomwroom.Entities;

/**
 * User
 *
 * Represents a user of the app. Is primarily used to generalize the common attributes of the rider
 * and driver class.
 *
 * @see Driver  - Extends user
 * @see Rider   - Extends user
 *
 * Feb 26, 2020
 *
 * @author Sharyar Memon
 *
 */
public class User {

    // Declare variables required for class.
    private String name;
    private String userName;
    private String userID;
    private ContactInformation contactDetails;
    private DriveRequest currentRequest; // stores the currentRequest the driver is involved in

    /**
     * Empty constructor, required by firebase.
     */
    public User(){ }

    /**
     * Constructor for class. Returns an instance of User based on provided parameters.
     * @param name String Full name of user.
     * @param userName String Unique userName of user. Used in place like profile info etc.
     * @param userID String unique userID used to reference the user within the system.
     */
    public User(String name, String userName, String userID) {
        this.name = name;
        this.userName = userName;
        this.userID = userID;
    }

    //<editor-fold desc="Getters & setters">

    /**
     * Returns the full name of the user
     *
     * @return      full name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user instance
     *
     * @param name  full name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the unique userName of the instance
     *
     * @return      userName of the instance
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName of the instance
     *
     * @param userName  unique userName to represent the instance
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the unique userID of the instance
     *
     * @return      userID of the instance
     */
    public String getUserID() {
        return userID;
    }

    /**
     * sets the userID of the instance based on the provided argument
     *
     * @param userID    string value to set as the userID of the instance
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Returns the contact information instance stored within the instance
     *
     * @return      Contact information instance stored within the instance
     */
    public ContactInformation getContactDetails() {
        return contactDetails;
    }

    /**
     * Stores a contactInformation instance within the User instance
     *
     * @param contactDetails    instance of ContactInformation containing the contact details
     *                          of the user
     */
    public void setContactDetails(ContactInformation contactDetails) {
        this.contactDetails = contactDetails;
    }

    /**
     * Gets the current DriveRequest the driver is involved in.
     *
     * @return      the current DriveRequest the driver is involved in.
     */
    public DriveRequest getCurrentRequest() {
        return currentRequest;
    }

    /**
     * Sets the current request the driver is involved in so it can be referenced for use.
     *
     * @param currentRequest    DriveRequest the driver is currently assigned to or involved in
     */
    public void setCurrentRequest(DriveRequest currentRequest) {
        this.currentRequest = currentRequest;
    }
    //</editor-fold>
}
