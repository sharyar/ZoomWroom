package com.example.zoomwroom;

import android.icu.util.ULocale;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoomwroom.Entities.DriveRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FragmentDisplayDriveRequestInfo extends BottomSheetDialogFragment {

    TextView riderNameTextView;
    TextView fareTextView;
    TextView pickupLocationTextView;
    TextView dropoffLocationTextView;
    TextView distanceTextView;


   public FragmentDisplayDriveRequestInfo() {
   }

   @Override
   public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

//        fareTextView.setText(String.format(Locale.CANADA,"$%.0f", fare));
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_drive_request_info, container, false);

        Bundle bundle = getArguments();

        riderNameTextView = view.findViewById(R.id.rider_name);
        fareTextView = view.findViewById(R.id.offered_fare);
        pickupLocationTextView = view.findViewById(R.id.pickup_location);
        dropoffLocationTextView = view.findViewById(R.id.drop_off_location);
        distanceTextView = view.findViewById(R.id.distance);

        riderNameTextView.setText(bundle.getString("RiderName"));
        fareTextView.setText(String.format(Locale.CANADA,"$%.0f",bundle.getFloat("OfferedFare")));
        pickupLocationTextView.setText(bundle.getString("PickupLocation"));
        dropoffLocationTextView.setText(bundle.getString("DropOffLocation"));
        distanceTextView.setText(String.format(Locale.CANADA, "Distance: %.0fkm",bundle.getDouble("Distance")));

        return view;
    }

}
