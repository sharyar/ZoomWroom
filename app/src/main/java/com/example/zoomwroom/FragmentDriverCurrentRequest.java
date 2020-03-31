package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.w3c.dom.Text;

import java.util.Map;

public class FragmentDriverCurrentRequest extends BottomSheetDialogFragment {

    protected TextView rider_name;
    public FragmentDriverCurrentRequest(){};


    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_current_request, container, false);

        Bundle bundle = getArguments();

        String userID = bundle.getString("userID");


        TextView destination = view.findViewById(R.id.destination);
        TextView pickup = view.findViewById(R.id.departure);
        TextView status = view.findViewById(R.id.status);
        TextView rider_name = view.findViewById(R.id.rider_name);

        DriveRequest request = MyDataBase.getCurrentRequest(userID, "driverID");
        if (request == null) {
            String message = "You currently have no request!";
            rider_name.setText(message);
        }
        else{
            String message = "Lat: " + Double.toString(request.getDestinationLat()).substring(0, 8);
            message += "  Lon: " + Double.toString(request.getDestinationLng()).substring(0, 10);
            destination.setText(message);
            message = "Lat: " + Double.toString(request.getPickupLocationLat()).substring(0, 8);
            message += "  Lon: " + Double.toString(request.getPickupLocationLng()).substring(0, 10);
            pickup.setText(message);
            message = "Lon: " + Double.toString(request.getPickupLocationLng());
            if (request.getStatus() == 1) {
                message = "ACCEPTED";
            }
            else if (request.getStatus() == 2) {
                message = "CONFIRMED";
            }
            else if (request.getStatus() == 3) {
                message = "ONGOING";
            }
            status.setText(message);
            Rider rider = MyDataBase.getRider(request.getRiderID());
            message = rider.getName();
            rider_name.setText(message);
        }

        Button complete = view.findViewById(R.id.complete_button);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // SETUP CLICKABLE RIDER NAME
        showRiderProfile(request);
        return view;
    }

    public void showRiderProfile(DriveRequest driveRequest){
        String riderId = driveRequest.getRiderID();
        rider_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RiderInfo.class);
                intent.putExtra("RIDER_ID",riderId);
                startActivity(intent);
            }
        });
    }
}
