package com.example.zoomwroom.Entities;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * DriveRequest
 *
 * Stores information about a DriveRequest a user has made including the user who has requested it,
 * the driver who has accepted it, current status of the request, pickup and drop off location, fare
 * rating given to driver by rider.
 *
 */
public class DriveRequest {

    //<editor-fold desc="Status">
    // status description: https://github.com/CMPUT301W20T29-H03/ZoomWroom/wiki/App-Terminologies#status
    public final class Status {
        public static final int PENDING     = 0;
        public static final int ACCEPTED    = 1;
        public static final int CONFIRMED   = 2;
        public static final int ONGOING     = 3;
        public static final int COMPLETED   = 4;
        public static final int CANCELLED   = 5;
        public static final int DECLINED    = 6;
        public static final int ABORTED     = 7;
    }
    //</editor-fold>

    private String requestID;
    private String riderID;
    private String driverID;
    private LatLng pickupLocation;
    private LatLng destination;
    private Date requestDateTime;
    private float suggestedFare;
    private float offeredFare;
    private int status;
    private Rating rating;

    // only for firebase
    private double pickupLocationLat;
    private double pickupLocationLng;
    private double destinationLat;
    private double destinationLng;

    /**
     * Default constructor, create an empty DriveRequest
     */
    public DriveRequest() {
        requestDateTime = new Date();
        status = Status.PENDING;
    }
    
    public DriveRequest(String riderID, LatLng pickupLocation, LatLng destination) {
        this();
        this.riderID = riderID;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
    }
    
    //<editor-fold desc="getter & setter">
    public String getRequestID() {
        return requestID;
    }
  
    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverId) {
        this.driverID = driverId;
    }

    public LatLng getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(LatLng pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public float getSuggestedFare() {
        return suggestedFare;
    }

    public void setSuggestedFare(float suggestedFare) {
        this.suggestedFare = suggestedFare;
    }

    public float getOfferedFare() {
        return offeredFare;
    }

    public void setOfferedFare(float offeredFare) {
        this.offeredFare = offeredFare;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) throws IllegalArgumentException {
        if (status < 0 || status > 7) {
            throw new IllegalArgumentException("status value out of range!");
        }
        this.status = status;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public double getPickupLocationLat() {
        return pickupLocationLat;
    }

    public void setPickupLocationLat(double pickupLocationLat) {
        this.pickupLocationLat = pickupLocationLat;
    }

    public double getPickupLocationLng() {
        return pickupLocationLng;
    }

    public void setPickupLocationLng(double pickupLocationLng) {
        this.pickupLocationLng = pickupLocationLng;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public double getDestinationLng() {
        return destinationLng;
    }

    public void setDestinationLng(double destinationLng) {
        this.destinationLng = destinationLng;
    }
    //</editor-fold>

    public String toQRBucksString() {
        return String.format("%s, %s, %f", riderID, driverID, offeredFare);
    }

    /**
     * call this method before upload to firebase
     */
    public void toFirebaseMode() {
        pickupLocationLat = pickupLocation.latitude;
        pickupLocationLng = pickupLocation.longitude;
        destinationLat = destination.latitude;
        destinationLng = destination.longitude;

        pickupLocation = null;
        destination = null;
    }

    /**
     * call this method after download from firebase
     */
    public void toLocalMode() {
        pickupLocation = new LatLng(pickupLocationLat, pickupLocationLng);
        destination = new LatLng(destinationLat, destinationLng);
    }
}
