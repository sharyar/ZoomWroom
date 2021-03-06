package com.example.zoomwroom.Entities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Represents a Location in Latitude and Longitude
 *
 * Author : Henry Lin
 * @see com.google.android.gms.maps.model.LatLng
 */
public class Location {
    private LatLng depart;
    private LatLng destination;

    public Location() { }

    /**
     * Returns the LatLng of the departure
     * @return depart
     */
    public LatLng getDepart(){
        return depart;
    }

    /**
     * Returns the LatLng of the destination
     * @return destination
     */
    public LatLng getDestination() {
        return destination;
    }

    /**
     * Sets the LatLng of the departure
     * @params depart
     */
    public void setDepart(LatLng depart){
        this.depart = depart;

    }

    /**
     * Sets the LatLng of the destination
     * @param destination
     */
    public void setDestination(LatLng destination) {
        this.destination = destination;
    }
}
