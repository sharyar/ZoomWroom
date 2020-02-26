package com.example.zoomwroom.Entities;

public class ContactInformation {
    private String phoneNumber;
    private String email;

    public ContactInformation(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public ContactInformation() {}

    //<editor-fold desc="Getters & Setters">
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    //</editor-fold>

    public void call(String phoneNumber){
        //implementation to come later
    }

    public void email(String email){
        //implementation to come later
    }
}
