package com.example.zoomwroom;

import androidx.fragment.app.FragmentActivity;


import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    protected Location mLocation;
    private boolean f;
    private Marker departureMarker;
    private Marker destinationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mLocation = new Location();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng edmonton = new LatLng(53.5232, -113.5263);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(edmonton));

        mMap.setOnMapClickListener(this);
        f = true; // Set departure flag as true

        departureMarker = mMap.addMarker(new MarkerOptions()
                .position(edmonton)
                .title("Departure"));
        destinationMarker = mMap.addMarker(new MarkerOptions()
                .position(edmonton)
                .title("Destination"));

        departureMarker.setVisible(false);
        destinationMarker.setVisible(false);

    }

    // TODO : Move to controller class
    @Override
    public void onMapClick(LatLng point) {
        if(f){
            mLocation.setDepart(point);
            f = false;
        } else {
            mLocation.setDestination(point);
            f = true;
        }
        updateMarkers();
    }


    public void updateMarkers() {
        LatLng depart = mLocation.getDepart();
        LatLng destination = mLocation.getDestination();
        Log.d("Departure", depart.toString());
        Log.d("DepartMark", departureMarker.getPosition().toString());
        if(!depart.equals(departureMarker.getPosition())) {
            departureMarker.setPosition(depart);
            departureMarker.setVisible(true);
            Log.d("Animated", "Moved depart");
        }
        Log.d("Destination", destination.toString());
        Log.d("DestinMarker", destinationMarker.getPosition().toString());
        if(!destination.equals(destinationMarker.getPosition())) {
            destinationMarker.setPosition(destination);
            destinationMarker.setVisible(true);
            Log.d("Animated", "Moved destination");
        }
    }
}
