package com.example.zoomwroom.Entities;

public class User {

    // Declare variables required for class.
    private String name;
    private String userName;
    private String userID;
    private Image photo;
    private ContactInformation contactDetails;
    private QRBucks accountBalance;


    /**
     * Constructor for class. Returns an instance of User based on provided parameters.
     * @param name String Full name of user.
     * @param userName String Unique userName of user.
     * @param userID String unique userID used to reference the user within the system.
     * @param photo Image   User's profile photo.
     */
    public User(String name, String userName, String userID, Image photo) {
        this.name = name;
        this.userName = userName;
        this.userID = userID;
        this.photo = photo;
    }

    /**
     * Empty constructor. I think it is required by firebase(Not certain).
     */
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public void editProfile(String name, String userName, ContactInformation contactDetails) {
        setName(name);
        setUserName(userName);
        setContactDetails(contactDetails);
    }

}
