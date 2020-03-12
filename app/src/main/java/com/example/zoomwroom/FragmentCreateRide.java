package com.example.zoomwroom;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * Create a ride Fragment
 *
 * Author : Amanda Nguyen
 * Creates a ride fragment where destination and pickup markers will be replaced to where the user wants to go
 *
 */

public class FragmentCreateRide  extends BottomSheetDialogFragment {

    public FragmentCreateRide(){};


    @Override
    public void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);

    }
    /**
     * Displays the fragment xml for creating a ride
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);

        Bundle bundle = getArguments();

        return view;
    }
}
