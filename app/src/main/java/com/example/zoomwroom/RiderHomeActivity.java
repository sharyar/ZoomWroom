
package com.example.zoomwroom;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Google Maps Activity
 *
 * Author : Henry Lin
 * Creates a google map fragment where markers can be placed
 *
 * @see com.example.zoomwroom.Location
 * @see com.google.android.gms.maps.GoogleMap
 *
 * Modified source from: https://developers.google.com/maps/documentation/android-sdk/start
 * Under the Apache 2.0 license
 */
public class RiderHomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    protected Location mLocation;
    private boolean f;
    private Marker departureMarker;
    private Marker destinationMarker;
    private FloatingActionButton testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);
        mLocation = new Location();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Bundle b = new Bundle(); // idk what this is

        FragmentManager fragmentManager = getSupportFragmentManager(); // this is creaitng a fragment manager
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); // something you just have to do
        final FragmentDisplayDriveRequestInfo driveRequestFragment = new FragmentDisplayDriveRequestInfo(); // this is your fragment class
        testButton = (FloatingActionButton) findViewById(R.id.test_button);

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
                .title("Departure")
                .icon(BitmapDescriptorFactory.defaultMarker(244))
                .alpha(0.71f));
        destinationMarker = mMap.addMarker(new MarkerOptions()
                .position(edmonton)
                .title("Destination")
                .icon(BitmapDescriptorFactory.defaultMarker(244))
                .alpha(0.71f));

        departureMarker.setVisible(false);
        destinationMarker.setVisible(false);

    }

    /**
     * Moves the destination and departure markers when user clicks on a point on the map
     * flag f - true = set departure
     *          false = set destination
     * @param point
     */
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

    /**
     * Moves markers to the current latlng position and updates the estimated fare
     */
    public void updateMarkers() {
        LatLng depart = mLocation.getDepart();
        LatLng destination = mLocation.getDestination();
        if(!depart.equals(departureMarker.getPosition())) {
            departureMarker.setPosition(depart);
            departureMarker.setVisible(true);
        }
        if(!destination.equals(destinationMarker.getPosition())) {
            destinationMarker.setPosition(destination);
            destinationMarker.setVisible(true);
        }
        double price = getPrice(5.00, 0.5);
        Log.d("Price", Double.toString(price));
    }

    /**
     * gets the recommended fare price using formula baseprice + multiplier * distance between points
     *
     * @param basePrice
     * @param multiplier
     * @return price
     */
    public double getPrice(double basePrice, double multiplier) {
        if(departureMarker.isVisible() && destinationMarker.isVisible()) {
            double price = basePrice + multiplier * getDistance();
            return round(price, 2);
        } else {
            return 0;
        }
    }

    /**
     * gets distance in kilometers from two latitude/longitude points
     * by using the haversine formula : https://www.movable-type.co.uk/scripts/latlong.html
     *
     * Adapted from javascript code
     * @return distance
     */
    public double getDistance() {
        final int R = 6371; // Radius of the earth in Km
        Double latDistance = toRad(departureMarker.getPosition().latitude
                - destinationMarker.getPosition().latitude);
        Double lonDistance = toRad(departureMarker.getPosition().longitude
                - destinationMarker.getPosition().longitude);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(departureMarker.getPosition().latitude)) *
                        Math.cos(toRad(destinationMarker.getPosition().latitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    /**
     * Rounds a double value to int places
     * Source: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
     * @param value
     * @param places
     * @return roundedNum
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Converts a degree to a radian value
     * @param value
     * @return radian
     */
    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }
}
