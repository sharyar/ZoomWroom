package com.example.zoomwroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.util.Locale;


public class FragmentDisplayDriveRequestInfo extends BottomSheetDialogFragment {

    TextView riderNameTextView;
    TextView fareTextView;
    TextView distanceTextView;
    Button acceptRequest;


   public FragmentDisplayDriveRequestInfo() {
   }

   @Override
   public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_drive_request_info, container, false);

        Bundle bundle = getArguments();

        riderNameTextView = view.findViewById(R.id.rider_name);
        fareTextView = view.findViewById(R.id.offered_fare);
        distanceTextView = view.findViewById(R.id.distance);
        acceptRequest = view.findViewById(R.id.accept_request_btn);

        riderNameTextView.setText(bundle.getString("RiderName"));
        fareTextView.setText(String.format(Locale.CANADA,"$%.0f",bundle.getFloat("OfferedFare")));
        distanceTextView.setText(String.format(Locale.CANADA, "Distance: %.0fkm",bundle.getDouble("Distance")));

        DriveRequest request = MyDataBase.getDriveRequestByID(bundle.getString("DriveRequestID"));

        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setStatus(DriveRequest.Status.PENDING);
                request.setDriverID(bundle.getString("DriverID"));
                MyDataBase.updateRequest(request);
            }
        });

        return view;
    }

    // This resetMarkers method is called to reset the markers when the user closes the request fragment.
    @Override
    public void onDestroy() {
       ((DriverHomeActivity)getActivity()).resetMarkers();
       super.onDestroy();
    }



}
