package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * @author Dulong Sang
 * last update: Mar 31, 2020
 */

public class RiderCompleteRequestFragment extends BottomSheetDialogFragment {

    private Driver driver;
    private DriveRequest driveRequest;
    private boolean isRated = false;

    public RiderCompleteRequestFragment(DriveRequest driveRequest) {
        this.driveRequest = driveRequest;
        this.driver = MyDataBase.getInstance().getDriver(driveRequest.getDriverID());
        System.out.println("DRIVER: " + driveRequest.getDriverID());
    }

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_complete_request, container, false);

        // findViews
        Button generateQRButton;
        Button rateDriverButton;
        Button completeButton;

        generateQRButton = view.findViewById(R.id.complete_request_generate_qr_button);
        rateDriverButton = view.findViewById(R.id.complete_request_rate_driver_button);
        completeButton = view.findViewById(R.id.complete_request_complete_button);

        generateQRButton.setOnClickListener(v -> {
            QRDisplayFragment qrDisplayFragment = new QRDisplayFragment(driveRequest.toQRBucksString());
            qrDisplayFragment.show(getFragmentManager(), "QR_Display");
        });

        rateDriverButton.setText(String.format("Rate %s", driver.getName()));
        rateDriverButton.setOnClickListener(v -> {
            if (isRated) {
                Toast.makeText(getContext(), "You have already rated this ride!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            isRated = true;
            RateDriverFragment rateDriverFragment = new RateDriverFragment(driver.getUserID());
            rateDriverFragment.show(getFragmentManager(), "Rate_Driver");
        });

        completeButton.setOnClickListener(v -> {
            // update the request status
            driveRequest.setStatus(DriveRequest.Status.FINALIZED);
            MyDataBase.getInstance().updateRequest(driveRequest);

            // close this fragment
            Intent intent = new Intent(getActivity(), RiderHomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        return view;
    }
}
