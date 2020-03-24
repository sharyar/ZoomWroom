
package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.Frame;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Rider Home Activity
 *
 * Author : Henry Lin, Amanda Nguyen
 * Creates a google map fragment where markers can be placed
 * Allows the user to create a ride and see their profile from fragments
 *
 *
 * @see com.example.zoomwroom.Location
 * @see com.google.android.gms.maps.GoogleMap
 *
 * March 12, 2020
 *
 * Modified source from: https://developers.google.com/maps/documentation/android-sdk/start
 * Under the Apache 2.0 license
 */
public class RiderHomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, Serializable {

    private GoogleMap mMap;
    protected Location mLocation;
    private boolean f;
    private Marker departureMarker;
    private Marker destinationMarker;
    private FloatingActionButton profileButton;
    private Button rideButton;
    private String riderEmail;
    private String token;
    private TextView rideStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);




        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.d("newToken-----", token);
            }
        });

        //get current user;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        riderEmail = user.getEmail();

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("riderID", user.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<DriveRequest> requests =  MyDataBase.getRiderRequest(riderEmail);
                        for (DriveRequest request :requests) {
                            if (request.getStatus() == 1) {
                                //Log.d("newToken", token);
                                Driver driver = MyDataBase.getDriver(request.getDriverID());
                                if (driver != null) {
                                    String name = driver.getName();
                                    String message = name + " just accepted your request!";
                                    new Notify(token, message).execute();
                                }

                                FragmentDriverAccepted driverAcceptedFragment = new FragmentDriverAccepted();
                                startAcceptedRide(driverAcceptedFragment, request);
                                showButton();

                            }
                            else if (request.getStatus() == 2){
                                FragmentDriverAccepted driverAcceptedFragment = new FragmentDriverAccepted();
                                startAcceptedRide(driverAcceptedFragment, request);
                                showButton();
                            }
                        }

                    }
                });

        mLocation = new Location();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // User profile button
        profileButton = findViewById(R.id.rider_profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), UserProfileActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Create a ride button
        rideButton = findViewById(R.id.create_ride_button);
        rideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCreateRide createRideFragment = new FragmentCreateRide();
                startCreateRide(createRideFragment);
                showButton();


            }
        });

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
            Log.d("Lon", Double.toString(mLocation.getDepart().longitude));
            Log.d("Lat", Double.toString(mLocation.getDepart().latitude));
        } else {
            mLocation.setDestination(point);
            f = true;
            Log.d("Lon", Double.toString(mLocation.getDestination().longitude));
            Log.d("Lat", Double.toString(mLocation.getDestination().latitude));
        }
        updateMarkers();
    }
    /**
     * Function is called when the drive request has been accepted
     * @param fragment
     *      the fragment where we will be switching activities
     * @param driveRequest
     *      pass in the driveRequest object so we can switch the status if the rider cancels
     *  Note: this function is only called for FragmentAcceptedRide
     *
     * Two separate functions are made since accepted ride fragment will always be displayed "on top"
     * of the create ride fragment
     * In this case, we use replace and not add!
     * */
    public void startAcceptedRide(FragmentDriverAccepted fragment, DriveRequest driveRequest) {
        Bundle b = new Bundle();
        b.putSerializable("driveRequest", driveRequest);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(b);
        fragmentTransaction.replace(R.id.rider_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    /**
     * Function is called when the user wants to create a ride
     * @param fragment
     *  represents which fragment it will go to
     * Note: this function is only called for FragmentCreateRide
     * */
    public void startCreateRide(FragmentCreateRide fragment){

        Bundle b = new Bundle();
        Log.d("Lon", Double.toString(mLocation.getDepart().longitude));
        Log.d("Lat", Double.toString(mLocation.getDepart().latitude));
        Log.d("Lon", Double.toString(mLocation.getDestination().longitude));
        Log.d("Lat", Double.toString(mLocation.getDestination().latitude));
        Log.d("price", Double.toString(getPrice(5.00, 0.5)));
        b.putDouble("desLat", mLocation.getDestination().latitude);
        b.putDouble("desLon", mLocation.getDestination().longitude);
        b.putDouble("depLat", mLocation.getDepart().latitude);
        b.putDouble("depLon", mLocation.getDepart().longitude);
        b.putDouble("price", getPrice(5.00, 0.5));
        b.putString("userID", riderEmail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(b);
        fragmentTransaction.add(R.id.rider_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }
    /**
     * Checks to see if there is a fragment in the current FrameLayout
     * if there isn't, display the create a ride button
     *
     * */
    public void showButton(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment checkCreate = fragmentManager.findFragmentById(R.id.rider_fragment);
        if (checkCreate == null){
            rideButton.setVisibility(View.VISIBLE);
        }
        else{
            rideButton.setVisibility(View.GONE);
        }
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


    /**
     * Called when user wants to create a ride
     * Will open up a fragment to deal with creating a ride request
     * */
    public void openRideCreation() {

        Bundle b = new Bundle();
        Log.d("Lon", Double.toString(mLocation.getDepart().longitude));
        Log.d("Lat", Double.toString(mLocation.getDepart().latitude));
        Log.d("Lon", Double.toString(mLocation.getDestination().longitude));
        Log.d("Lat", Double.toString(mLocation.getDestination().latitude));
        Log.d("price", Double.toString(getPrice(5.00, 0.5)));
        b.putDouble("desLat", mLocation.getDestination().latitude);
        b.putDouble("desLon", mLocation.getDestination().longitude);
        b.putDouble("depLat", mLocation.getDepart().latitude);
        b.putDouble("depLon", mLocation.getDepart().longitude);
        b.putDouble("price", getPrice(5.00, 0.5));
        b.putString("userID", riderEmail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final FragmentCreateRide createRideFragment = new FragmentCreateRide();

        createRideFragment.setArguments(b);
        createRideFragment.show(getSupportFragmentManager(), createRideFragment.getTag());
    }

}
