package com.example.zoomwroom;

import com.google.android.gms.maps.model.LatLng;

public class Location {
    private LatLng depart;
    private LatLng destination;

    public LatLng getDepart(){
        return depart;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDepart(LatLng depart){
        this.depart = depart;

    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }
}
