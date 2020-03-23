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


    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);

        Bundle bundle = getArguments();

        double desLat = bundle.getDouble("desLat");
        double desLon = bundle.getDouble("desLon");
        double depLat = bundle.getDouble("depLat");
        double depLon = bundle.getDouble("depLon");
        double price = bundle.getDouble("price");
        String userID = bundle.getString("userID");

        EditText destination = view.findViewById(R.id.destination_text);
        EditText pickup= view.findViewById(R.id.pickup_text);
        EditText fare = view.findViewById(R.id.fare_text);

        String des = "Lon: " + desLon + " Lat: " + desLat;
        String dep = "Lon: " + depLon + " Lat: " + depLat;
        String fa = Double.toString(price);
        destination.setText(des);
        pickup.setText(dep);
        fare.setText(fa);


        Button cancel = view.findViewById(R.id.cancel_button);
        Button confirm = view.findViewById(R.id.confirm_button);


        // Confirm button in order to send new DriveRequest to Firebase
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng departure = new LatLng(depLat, depLon);
                LatLng destination = new LatLng(desLat, desLon);
                newRequest = new DriveRequest(userID, departure, destination);

                // grabbing the fare offered by the user
                newRequest.setOfferedFare(Float.valueOf(fare.getText().toString()));
                Float offeredFare = Float.valueOf(fare.getText().toString());

                // Do not accept ride requests where the offer is lower than the suggested price
                if (offeredFare < price){
                    Toast.makeText(getContext(), "Fare must be a minimum of " + price, Toast.LENGTH_SHORT).show();
                }
                else{
                    MyDataBase.addRequest(newRequest);
                    Toast.makeText(getContext(), "Successfully create a ride!", Toast.LENGTH_SHORT).show();
                    TextView rideStatus = ((RiderHomeActivity) getActivity()).findViewById(R.id.rideStatus);
                    rideStatus.setVisibility(View.VISIBLE);
                    rideStatus.setText("PENDING");
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
}
