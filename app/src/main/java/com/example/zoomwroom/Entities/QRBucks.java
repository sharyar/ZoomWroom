package com.example.zoomwroom.Entities;

import android.util.Log;

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
    private String riderName;

    public QRBucks() {
    }



    public QRBucks(String driveRequestID, String riderName, float amountOfMoneyOwed) {
        this.status = StatusQRBucks.OWED;
        this.driveRequestID = driveRequestID;
        this.amountOfMoneyOwed = amountOfMoneyOwed;
        this.riderName = riderName;
    }

    /**
     * Constructor to use when scanning in a QR Code as it will do all the parsing
     * QR Code should be code like this: Rider'sFullName-driveRequestID-offeredfare
     * @param qrCodeData
     */
    public QRBucks(String qrCodeData) {
        StringBuilder b = new StringBuilder();
        int loopVar = 0;
        for (char c: qrCodeData.toCharArray()) {
            if (c != '-') {
                b.append(c);
            } else {
                if (loopVar == 0) {
                    this.riderName = b.toString().trim();
                    b = new StringBuilder();
                } else if (loopVar == 1) {
                    this.driveRequestID = b.toString().trim();
                    b = new StringBuilder();
                }
                loopVar++;
            }
        }
        try {
            this.amountOfMoneyOwed = Float.parseFloat(b.toString().trim());
        } catch (Exception e){
            Log.e("QRBucksEntityClass", "Parsing error in constructor");
        }
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

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }
    //</editor-fold>

    public static final class StatusQRBucks {
        public static final int OWED = 0;
        public static final int PAID = 1;
    }

    public static String giveStatus(int status) {
        String strStatus;

        switch (status) {
            case 0:
                strStatus = "Owing";
                break;
            case 1:
                strStatus = "Paid";
                break;
            default:
                strStatus = "Unknown";
        }

        return strStatus;
    }
}
