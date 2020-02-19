package com.example.zoomwroom.Entities;

public class User {
    private String name;
    private String userName;
    private String userId;
    private Image photo;
    private ContactInformation contactDetails;
    private QRBucks accountBalance;



    public User(String name, String userName, String userId, Image photo,
                ContactInformation contactDetails, QRBucks accountBalance) {
        this.name = name;
        this.userName = userName;
        this.userId = userId;
        this.photo = photo;
        this.contactDetails = contactDetails;
        this.accountBalance = accountBalance;
    }

    public User(){
        super();
    }

    //<editor-fold desc="Getters & setters">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Image getPhoto() {
        return photo;
    }

    public void setPhoto(Image photo) {
        this.photo = photo;
    }

    public ContactInformation getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactInformation contactDetails) {
        this.contactDetails = contactDetails;
    }

    public QRBucks getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(QRBucks accountBalance) {
        this.accountBalance = accountBalance;
    }
    //</editor-fold>



}
