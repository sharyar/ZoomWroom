package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;

import org.w3c.dom.Text;

public class FragmentAcceptedRide extends FragmentCreateRide {
    public void FragmentAcceptedRide(){};

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // ******************** Setting textview to the appropriate values ***************

        View view = inflater.inflate(R.layout.fragment_accepted_ride, container, false);
        Bundle bundle = getArguments();
        DriveRequest driveRequest = (DriveRequest) bundle.getSerializable("driveRequest");

        TextView driverName = view.findViewById(R.id.driver_name);
        String stringName = MyDataBase.getDriver(driveRequest.getDriverID()).getName();
        driverName.setText(stringName);

        TextView driverUserName = view.findViewById(R.id.driver_username);
        String stringUsername = MyDataBase.getDriver(driveRequest.getDriverID()).getUserName();
        driverUserName.setText(stringUsername);

        TextView destination = view.findViewById(R.id.destination_text);
        String stringDestination =  Double.toString(driveRequest.getDestinationLng()) + ", " + Double.toString(driveRequest.getDestinationLat());
        destination.setText("Destination: " + stringDestination);

        TextView pickup = view.findViewById(R.id.pickup_text);
        String stringPickup = Double.toString(driveRequest.getPickupLocationLng()) + ", " + Double.toString(driveRequest.getPickupLocationLat());
        pickup.setText("Pickup: " + stringPickup);

        TextView fare = view.findViewById(R.id.fare_text);
        String stringFare = Float.toString(driveRequest.getSuggestedFare());
        fare.setText("Fare: " + stringFare);

        // ******************** Setting textview to the appropriate values ***************

        // Cancel button
        // Set the status to 5, then update firebase
        // restart main activity
        Button cancel = view.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driveRequest.setStatus(5);
                MyDataBase.updateRequest(driveRequest);
                Intent intent = new Intent(getActivity(), RiderHomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        return view;
    }

}
