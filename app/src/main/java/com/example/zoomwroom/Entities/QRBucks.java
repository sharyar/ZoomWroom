package com.example.zoomwroom.Entities;

public class QRBucks {
    private double CADValue;

    public QRBucks(double CADValue) {
        this.CADValue = CADValue;
    }

    public QRBucks() {
        this(0);
    }

    public void addQRBucks(Double money) {
        this.CADValue += money;
    }

}
