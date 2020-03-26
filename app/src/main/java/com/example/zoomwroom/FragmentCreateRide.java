package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Create a ride Fragment
 *
 * Author : Amanda Nguyen
 * Creates a ride fragment where destination and pickup markers will be replaced to where the user wants to go
 *
 * March 12, 2020
 *
 * Under the Apache 2.0 license
 */

public class FragmentCreateRide  extends BottomSheetDialogFragment {

    public FragmentCreateRide(){};
    private DriveRequest newRequest;
    private TextView driverName;
    private TextView driverUserName;
    private Button confirm;
    private Button cancel;
    private Button complete;
    private EditText fare;
    private double price;
    private double desLat;
    private double desLon;
    private double depLat;
    private double depLon;
    private String userID;


    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);

        Bundle bundle = getArguments();

        driverName = view.findViewById(R.id.driver_name);
        driverUserName = view.findViewById(R.id.driver_username);

        // ****** Setting up information ********
        double desLat = bundle.getDouble("desLat");
        double desLon = bundle.getDouble("desLon");
        double depLat = bundle.getDouble("depLat");
        double depLon = bundle.getDouble("depLon");
        double oldPrice = bundle.getDouble("price");
        price = RiderHomeActivity.round(oldPrice, 2);
        String userID = bundle.getString("userID");

        TextView destination = view.findViewById(R.id.destination_text);
        TextView pickup= view.findViewById(R.id.pickup_text);
        fare = view.findViewById(R.id.fare_text);


        String des = "Lon: " + RiderHomeActivity.round(desLon,12) + " Lat: " + RiderHomeActivity.round(desLat,12);
        String dep = "Lon: " + RiderHomeActivity.round(depLon,12) + " Lat: " + RiderHomeActivity.round(depLat,12);
        String fa = Double.toString(price);
        destination.setText(des);
        pickup.setText(dep);
        fare.setText(fa);

        // ****** Setting up information ********


        cancel = view.findViewById(R.id.cancel_button);
        confirm = view.findViewById(R.id.confirm_button);
        complete = view.findViewById(R.id.complete_button);


        // Confirm button in order to send new DriveRequest to Firebase
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newRequest == null){
                    double offeredFare = Float.valueOf(fare.getText().toString());
                    price = RiderHomeActivity.round(price,2);
                    // Do not accept ride requests where the offer is lower than the suggested price
                    if (offeredFare < price){
                        Toast.makeText(getContext(), "Fare must be a minimum of " + price, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        LatLng departure = new LatLng(depLat, depLon);
                        LatLng destination = new LatLng(desLat, desLon);
                        newRequest = new DriveRequest(userID, departure, destination);

                        // grabbing the fare offered by the user
                        newRequest.setOfferedFare(Float.valueOf(fare.getText().toString()));
                        fare.setEnabled(false);
                        pendingPhase();
                    }

                }

            }
        });

        // Cancel button
        // First check if user has created a ride request or not
        // If so, set the status to 5 and update the database
        // restart the activity all together
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newRequest != null){
                    newRequest.setStatus(5);
                    MyDataBase.updateRequest(newRequest);
                }

                Intent intent = new Intent(getActivity(), RiderHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        return view;
    }

    // PROBLEM:
    // the confirm button needs to do 2 different things depending on two things:
    //  1. create a ride
    //  2. set the status as accepted and let the driver know to drive over

    public void createRide(){
        Float offeredFare = Float.valueOf(fare.getText().toString());
        // Do not accept ride requests where the offer is lower than the suggested price
        if (offeredFare < price){
            Toast.makeText(getContext(), "Fare must be a minimum of " + price, Toast.LENGTH_SHORT).show();
        }
        else{
            LatLng departure = new LatLng(depLat, depLon);
            LatLng destination = new LatLng(desLat, desLon);
            newRequest = new DriveRequest(userID, departure, destination);

            // grabbing the fare offered by the user
            newRequest.setOfferedFare(Float.valueOf(fare.getText().toString()));
            fare.setEnabled(false);
            pendingPhase();
        }

    }

    // phase 0
    // make the confirm button disappear since they don't need to confirm another ride
    public void pendingPhase(){
        confirm.setVisibility(View.GONE);
        MyDataBase.addRequest(newRequest);
        Toast.makeText(getContext(), "Successfully create a ride!", Toast.LENGTH_SHORT).show();
    }

    // phase 1
    // have the driver name and username show up
    // change the fragment to accepted
    // see the confirm button again so they can accept the ride
    public void DriverAcceptedPhase(DriveRequest driveRequest){
        newRequest = driveRequest;
        confirm.setVisibility(View.VISIBLE);
        String stringName = MyDataBase.getDriver(driveRequest.getDriverID()).getName();
        driverName.setText(stringName);
        driverName.setVisibility(View.VISIBLE);

        String stringUsername = MyDataBase.getDriver(driveRequest.getDriverID()).getUserName();
        driverUserName.setText(stringUsername);
        driverUserName.setVisibility(View.VISIBLE);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmRidePhase(driveRequest);
            }
        });



    }

    // phase 2
    // rider has confirmed that they want to take the ride
    // hide the confirm button
    public void confirmRidePhase(DriveRequest driveRequest){
        driveRequest.setStatus(2);
        MyDataBase.updateRequest(driveRequest);
        confirm.setVisibility(View.GONE);
    }

    // phase 3
    // waiting for the driver?
    // do nothing for now
    public void ridingPhase(DriveRequest driveRequest){
        cancel.setVisibility(View.GONE);
        complete.setVisibility(View.VISIBLE);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driveRequest.setStatus(4);
                MyDataBase.updateRequest(driveRequest);
            }
        });

    }




}
