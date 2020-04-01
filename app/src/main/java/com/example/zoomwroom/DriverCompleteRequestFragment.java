package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * @author Baijin tao
 * last update: Mar 31, 2020
 */

public class DriverCompleteRequestFragment extends BottomSheetDialogFragment {


    private Driver driver;
    private DriveRequest driveRequest;

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_complete_request, container, false);
//        Bundle bundle = getArguments();
//        assert bundle != null;
//        driveRequest = (DriveRequest) bundle.getSerializable("driveRequest");
//        assert driveRequest != null;
//
//        driver = MyDataBase.getDriver(driveRequest.getDriverID());
//        assert driver != null;

        // findViews
        Button completeButton;
        completeButton = view.findViewById(R.id.complete_request_scan_button);


        //completeButton.setText(String.format("Rate %s", driver.getName()));
        completeButton.setOnClickListener(v -> {
            // TODO: complete
            Intent intent = new Intent(v.getContext(), ScannerActivity.class);
            startActivity(intent);

        });

        return view;
    }
}