package com.example.zoomwroom;

import android.icu.util.ULocale;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zoomwroom.Entities.DriveRequest;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

public class FragmentDisplayDriveRequestInfo extends BottomSheetDialogFragment {

    TextView riderNameTextView;
    TextView fareTextView;


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

        riderNameTextView.setText(bundle.getString("RiderName"));
        fareTextView.setText(String.format(Locale.CANADA,"$%.0f",bundle.getFloat("OfferedFare")));

        return view;
    }

}
