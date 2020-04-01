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

import androidx.annotation.Nullable;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class FragmentDriverCurrentRequest extends BottomSheetDialogFragment {

    private String requestID;
    private String userID;
    private DriveRequest request;

    private TextView destination;
    private TextView pickup;
    private TextView status;
    private TextView rider_name;

    public FragmentDriverCurrentRequest(){};

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_current_request, container, false);

        Bundle bundle = getArguments();

        userID = bundle.getString("userID");


        destination = view.findViewById(R.id.destination);
        pickup = view.findViewById(R.id.departure);
        status = view.findViewById(R.id.status);
        rider_name = view.findViewById(R.id.rider_name);
        Button complete = view.findViewById(R.id.complete_button);

        request = MyDataBase.getInstance().getCurrentRequest(userID, "driverID");
        if (request == null) {
            requestID = "null";
            Log.d("requestID", requestID);
            setNoneText();
        }
        else{
            requestID = request.getRequestID();
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
            else if (request.getStatus() == 4) {
                message = "COMPLETE";
            }
            status.setText(message);
            Rider rider = MyDataBase.getInstance().getRider(request.getRiderID());
            message = rider.getName();
            rider_name.setText(message);


            String riderID = request.getRiderID();
            // SETUP CLICKABLE RIDER NAME
            rider_name.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UserContactActivity.class);
                intent.putExtra("USER_ID", riderID);
                startActivity(intent);
            });

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    request.setStatus(DriveRequest.Status.ONGOING);
                    MyDataBase.getInstance().updateRequest(request);
                }
            });
        }

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (request == null) {
                    dismiss();
                }
                else {
                    if (request.getStatus() == 2) {
                        request.setStatus(DriveRequest.Status.ONGOING);
                        MyDataBase.getInstance().updateRequest(request);
                    } else {
                        Toast.makeText(getActivity(), "Cannot confirm right now", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("requestID", requestID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<DriveRequest> requests =  MyDataBase.getInstance().getDriverRequest(userID);
                        for (DriveRequest request :requests) {
                            if (requestID.equals(request.getRequestID())) {
                                String message;
                                if (request.getStatus() == 5) {
                                    setNoneText();
                                }
                                else if (request.getStatus() == 2) {
                                    message = "CONFIRMED";
                                    status.setText(message);
                                }
                                else if (request.getStatus() == 3) {
                                    message = "ONGOING";
                                    status.setText(message);
                                }
                                else if (request.getStatus() == 4) {
                                    message = "COMPLETE";
                                    status.setText(message);
                                }
                            }
                        }

                    }
                });
        return view;
    }

//    private void showRiderProfile(DriveRequest driveRequest){
//        String riderId = driveRequest.getRiderID();
//        rider_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), RiderInfo.class);
//                intent.putExtra("USER_ID",riderId);
//                startActivity(intent);
//            }
//        });
//    }

    private void setNoneText() {
        String message = "You currently have no request!";
        status.setText(message);
        String None_message = "None";
        rider_name.setText(None_message);
        pickup.setText(None_message);
        destination.setText(None_message);
    }
}

