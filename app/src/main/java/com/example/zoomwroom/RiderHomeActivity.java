package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Location;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Rider Home Activity
 *
 * @author Henry Lin, Amanda Nguyen, Dulong Sang
 * Creates a google map fragment where markers can be placed
 * Allows the user to create a ride and see their profile from an additional fragment
 *
 * @see Location
 * @see com.google.android.gms.maps.GoogleMap
 *
 * partial refactored by Dulong Sang, Apr 1, 2020
 * last update: Apr 1, 2020
 *
 * Modified source from: https://developers.google.com/maps/documentation/android-sdk/start
 * Under the Apache 2.0 license
 */
public class RiderHomeActivity extends MapsActivity {

    private boolean f;

    private FloatingActionButton profileButton;
    private Button rideButton;
    private String riderEmail;
    private TextView rideStatus;
    private FragmentCreateRide createRideFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);

        //get current user;
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        riderEmail = user.getEmail();

        // rideStatus will always be invisible at the start of the activity
        rideStatus = findViewById(R.id.rideStatus);
        rideStatus.setVisibility(View.INVISIBLE);

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("riderID", user.getEmail())
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    ArrayList<DriveRequest> requests =  MyDataBase.getInstance().getRiderRequest(riderEmail);
                    for (DriveRequest request :requests) {

                        // this code is required when the user has logged out and logs back in
                        // recreates the fragment so the appropriate fragment will be shown
                        // the only time we won't create a ride fragment is if ride is complete or cancelled
                        if (request.getStatus() != DriveRequest.Status.CANCELLED &&
                                request.getStatus() != DriveRequest.Status.FINALIZED) {
                            if (createRideFragment == null) {
                                rideStatus.setVisibility(View.VISIBLE);
                                createRideFragment = new FragmentCreateRide();
                                startCreateRide(createRideFragment);
                                rideButton.setVisibility(View.INVISIBLE);
                                setMarkers(request.getDestination(), request.getPickupLocation());
                            }
                        }

                        if (request.getStatus() == DriveRequest.Status.PENDING){
                            rideStatus.setVisibility(View.VISIBLE);
                            rideStatus.setText("PENDING");
                            createRideFragment.pendingPhase(request);
                        }

                        else if (request.getStatus() == DriveRequest.Status.ACCEPTED) {
                            Log.d("newToken", token);
                            Driver driver = MyDataBase.getInstance().getDriver(request.getDriverID());
                            if (driver != null) {
                                String name = driver.getName();
                                String message = name + " just accepted your request!";
                                new Notify(token, message).execute();
                            }
                            rideStatus.setText("ACCEPTED BY DRIVER");
                            createRideFragment.acceptedPhase(request);
                        }

                        // fragment does not change here, only need to update rideStatus
                        else if (request.getStatus() == DriveRequest.Status.CONFIRMED){
                            rideStatus.setText("WAITING FOR DRIVER");
                            createRideFragment.confirmRidePhase(request);
                        }

                        else if (request.getStatus() == DriveRequest.Status.ONGOING) {
                            rideStatus.setText("RIDE IN PROGRESS");
                            createRideFragment.ridingPhase(request);
                        }

                        else if (request.getStatus() == DriveRequest.Status.COMPLETED){
                            rideStatus.setText("RIDE COMPLETED");
                            RiderCompleteRequestFragment completeRequest = new RiderCompleteRequestFragment(request);
                            completeRideFragment(completeRequest, request);
                        }
                    }
                });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // User profile button
        profileButton = findViewById(R.id.rider_profile_button);
        profileButton.setOnClickListener(v -> {
            Intent myIntent = new Intent(v.getContext(), UserProfileActivity.class);
            startActivityForResult(myIntent, 0);
        });

        // Create a ride button
        // Create the ride fragment and hide the create a ride button
        rideButton = findViewById(R.id.create_ride_button);
        rideButton.setOnClickListener(v -> {
            createRideFragment = new FragmentCreateRide();
            startCreateRide(createRideFragment);
            createRideFragment.createRequest(new DriveRequest(riderEmail,
                    mLocation.getDepart(), mLocation.getDestination()));
            rideButton.setVisibility(View.INVISIBLE);
        });

        // initialize google Places api
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.places_api_key), Locale.CANADA);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        f = true; // Set departure flag as true
    }
    /**
     * Moves the destination and departure markers when user clicks on a point on the map
     * flag f - true = set departure
     * false = set destination
     *
     * @param point
     */
    @Override
    public void onMapClick(LatLng point) {
        Log.d("POINT", point.toString());
        if (f) {
            mLocation.setDepart(point);
            f = false;
        } else {
            mLocation.setDestination(point);
            f = true;
        }
        updateMarkers();
    }

    public void setMarkers(LatLng destination, LatLng pickup){
        mLocation.setDepart(pickup);
        mLocation.setDestination(destination);
        updateMarkers();
    }

    /**
     * Function is called when the user wants to create a ride
     * @param fragment
     *  represents which fragment it will go to
     * Note: this function is only called for FragmentCreateRide
     * */
    public void startCreateRide(FragmentCreateRide fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.rider_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    public void completeRideFragment(RiderCompleteRequestFragment fragment, DriveRequest request){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rider_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        QRDisplayFragment qrDisplayFragment = new QRDisplayFragment(request.toQRBucksString());
        qrDisplayFragment.show(fragmentManager, "QR_Display");
    }


     /* DON'T ERASE THIS!!! I'm probably going to reuse this for the final fragment


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
    }*/

}
