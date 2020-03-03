package com.example.zoomwroom.Entities;

import com.example.zoomwroom.Location;

import java.util.Date;

public class DriveRequest {

    // status description: https://github.com/CMPUT301W20T29-H03/ZoomWroom/wiki/App-Terminologies#status
    public enum Status {
        PENDING,
        ACCEPTED,
        CONFIRMED,
        ONGOING,
        COMPLETED,
        CANCELLED,
        DECLINED,
        ABORTED
    }

    private Rider rider;
    private Driver driver;
    private Location pickupLocation;
    private Location destination;
    private Date requestDateTime;
    private float suggestedFare;
    private float offeredFare;
    private Status status;
    private Rating rating;

    /**
     * Default constructor, create an empty DriveRequest
     */
    public DriveRequest() {
        requestDateTime = new Date();
        status = Status.PENDING;
        rating = null;
    }

    public DriveRequest(Rider rider, Location pickupLocation, Location destination) {
        this();
        this.rider = rider;
        this.pickupLocation = pickupLocation;
        this.destination = destination;
        computeSuggestedFare();
    }

    private void computeSuggestedFare() {
        /* To be implemented */
        suggestedFare = 0;
    }

    /**
     * @param thumbsUp  true if rider gives a thumbs up, false otherwise
     */
    public void rate(boolean thumbsUp) {
        rating = new Rating(thumbsUp);
    }

    //<editor-fold desc="Getter & Setter Methods">
    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Location pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public float getSuggestedFare() {
        return suggestedFare;
    }

    public float getOfferedFare() {
        return offeredFare;
    }

    public void setOfferedFare(float offeredFare) throws IllegalArgumentException {
        if (offeredFare < suggestedFare) {
            throw new IllegalArgumentException("Offered fare should be not less than suggested fare");
        }
        this.offeredFare = offeredFare;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Rating getRating() {
        return rating;
    }
    //</editor-fold>
}
