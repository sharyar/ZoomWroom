package com.example.zoomwroom.Entities;

/**
 * This class represents the QRBucks in the form of IOUs.
 * They store the info about driveRequest via its ID as well as the amount owed.
 * The status will be used in the future for the driver to collect the owed sum.
 */
public class QRBucks {
    // Declare variables required for class
    private int status; // stores status of the QRBuck (0 - Owing, 1- Paid)
    private String driveRequestID; // stores the driveRequestID so the driveRequest can be linked
    private float amountOfMoneyOwed; // amount paid by the rider.

    public QRBucks() {
    }

    public QRBucks(String driveRequestID, float amountOfMoneyOwed) {
        this.status = StatusQRBucks.OWED;
        this.driveRequestID = driveRequestID;
        this.amountOfMoneyOwed = amountOfMoneyOwed;
    }

    //<editor-fold desc="Getters & Setters">
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDriveRequestID() {
        return driveRequestID;
    }

    public void setDriveRequestID(String driveRequestID) {
        this.driveRequestID = driveRequestID;
    }

    public float getAmountOfMoneyOwed() {
        return amountOfMoneyOwed;
    }

    public void setAmountOfMoneyOwed(float amountOfMoneyOwed) {
        this.amountOfMoneyOwed = amountOfMoneyOwed;
    }

    /**
     * This function changes the status of the QRBuck from owed to paid. (0 to 1)
     */
    public void collectMoney() {
        this.status = StatusQRBucks.PAID;
    }
    //</editor-fold>

    public static final class StatusQRBucks {
        public static final int OWED = 0;
        public static final int PAID = 1;
    }
}
