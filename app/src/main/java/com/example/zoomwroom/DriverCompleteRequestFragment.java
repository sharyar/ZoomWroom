package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * @author Baijin Tao
 * last update: Apr 1, 2020
 */

public class DriverCompleteRequestFragment extends BottomSheetDialogFragment {

    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_complete_request, container, false);

        // findViews
        Button completeButton = view.findViewById(R.id.complete_request_scan_button);

        completeButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ScannerActivity.class);
            startActivity(intent);
        });

        return view;
    }
}