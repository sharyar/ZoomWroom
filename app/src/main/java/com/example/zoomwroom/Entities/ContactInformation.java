package com.example.zoomwroom.Entities;

/**
 * ContactInformation
 *
 * Stores user's contact information as a separate object.
 *
 * @version 1.0
 *
 * Feb 26, 2020
 *
 * @author Sharyar Memon
 *
 */
public class ContactInformation {
    private String phoneNumber; // Stores user's phone number as a string
    private String email; // Stores user's email address as a string

    /**
     * Constructor for ContactInformation class. Returns an instance of ContactInformation
     *
     * @param phoneNumber   user's phone number as a string
     * @param email         user's email address as a string
     */
    public ContactInformation(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    /**
     * Empty constructor for ContactInformation class. Returns an instance of ContactInformation
     */
    public ContactInformation() {}

    //<editor-fold desc="Getters & Setters">

    /**
     * Returns a String with the phoneNumber of the instance.
     *
     * @return      the phoneNumber of the instance
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the instance
     *
     * @param phoneNumber   value to be set for the phoneNumber variable
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the email address as a string
     *
     * @return      value of email as a string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the instance
     *
     * @param email     value to be set for the email variable
     */
    public void setEmail(String email) {
        this.email = email;
    }
    //</editor-fold>

}
