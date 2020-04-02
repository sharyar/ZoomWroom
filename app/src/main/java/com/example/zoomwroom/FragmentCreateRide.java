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

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Create a ride Fragment
 *
 * @author Amanda Nguyen, Dulong Sang
 * Fragment that changes dynamically depending on the status of the drive request
 * Functions are created to be called in RiderHomeActivity to hide or show certain buttons/text
 *
 * refactored by Dulong Sang, Apr 1, 2020
 * last update Apr 1, 2020
 *
 * Under the Apache 2.0 license
 */

public class FragmentCreateRide  extends BottomSheetDialogFragment {

    // searching places service code
    private static final int RESULT_OK = -1;
    private static final int PICKUP_REQUEST = 1;
    private static final int DESTINATION_REQUEST = 2;

    private DriveRequest request;

    private TextView driverNameTextView;
    private TextView driverUsernameTextView;
    private Button confirmButton;
    private Button cancelButton;
    private Button completeButton;
    private EditText fareEditText;
    private TextView destinationTextView;
    private TextView pickupTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_ride, container, false);
        findViews(view);
        setGeneralOnClickListeners();

        return view;
    }

    /**
     * @param request
     *      userID must be set, other fields are nullable.
     */
    public void createRequest(DriveRequest request) {
        this.request = request;
        request.setStatus(DriveRequest.Status.CREATE);

        // calculate suggestedFare if both pickupLocation and destination are provided
        if (request.getPickupLocation() != null && request.getDestination() != null) {
            // update suggestedFare
            double suggestFare = FareCalculation.getPrice(FareCalculation.basePrice,
                    FareCalculation.multiplier, request.getPickupLocation(), request.getDestination());
            suggestFare = FareCalculation.round(suggestFare, 2);
            request.setSuggestedFare((float) suggestFare);
            request.setOfferedFare((float) suggestFare);
        }

        updateViews();

        enablePlaceSearch();

        confirmButton.setOnClickListener( v -> {
            float offeredFare = Float.parseFloat(fareEditText.getText().toString());
            float suggestedFare = request.getSuggestedFare();

            if (request.getPickupLocation() == null || request.getDestination() == null) {
                Toast.makeText(getContext(), "Please select your pickup location and destination",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            if (offeredFare < suggestedFare) {
                Toast.makeText(getContext(), "Fare must be a minimum of " + suggestedFare, Toast.LENGTH_SHORT).show();
                return;
            }
            request.setOfferedFare(offeredFare);
            request.setStatus(DriveRequest.Status.PENDING);
            MyDataBase.getInstance().addRequest(request);
            Toast.makeText(getContext(), "Successfully create a ride!", Toast.LENGTH_SHORT).show();
            disablePlaceSearch();
        });
    }

    /**
     * When the ride request is pending, the confirm button is hidden
     */
    public void pendingPhase(DriveRequest request) {
        this.request = request;
        confirmButton.setVisibility(View.GONE);
        disablePlaceSearch();
        updateViews();
    }

    /**
     * Have the driver's name, username and confirm button appear
     * Override confirm button to update the drive request
     * Driver's username is also now clickable to view
     *
     * @param _request
     *      DriveRequest to update
     */
    public void acceptedPhase(DriveRequest _request) {
        request = _request;

        // show views
        confirmButton.setVisibility(View.VISIBLE);
        showDriverInfo();
        updateViews();

        // rider side update request status from ACCEPTED to CONFIRMED
        confirmButton.setOnClickListener( v -> {
            request.setStatus(DriveRequest.Status.CONFIRMED);
            MyDataBase.getInstance().updateRequest(request);
        });
    }

    /**
     * Called when user wants to accept the ride from driver
     * Confirm button is invisible again
     * @param request
     *      DriveRequest to update
     */
    public void confirmRidePhase(DriveRequest request){
        this.request = request;
        confirmButton.setVisibility(View.GONE);
        showDriverInfo();

        updateViews();
    }

    /**
     * Ride is now in progress
     * User is no longer able to cancel the ride
     * Complete the ride button is now active
     *
     * @param _request
     *     DriveRequest to update
     * */
    public void ridingPhase(DriveRequest _request) {
        request = _request;

        // set buttons visibility
        cancelButton.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);
        completeButton.setVisibility(View.VISIBLE);

        showDriverInfo();
        updateViews();

        // rider side set request status from ONGOING to COMPLETE
        completeButton.setOnClickListener( v -> {
            request.setStatus(DriveRequest.Status.COMPLETED);
            MyDataBase.getInstance().updateRequest(request);
        });
    }

    private void enablePlaceSearch() {
        destinationTextView.setClickable(true);
        pickupTextView.setClickable(true);

        destinationTextView.setOnClickListener( v -> {
            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(getActivity());
            startActivityForResult(intent, DESTINATION_REQUEST);
        });

        pickupTextView.setOnClickListener( v -> {
            // Set the fields to specify which types of place data to
            // return after the user has made a selection.
            List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(getActivity());
            startActivityForResult(intent, PICKUP_REQUEST);
        });
    }

    private void disablePlaceSearch() {
        destinationTextView.setClickable(false);
        pickupTextView.setClickable(false);
    }

    // receive the place search result from Autocomplete (google Places api)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);

            if (requestCode == DESTINATION_REQUEST) {
                request.setDestinationName(place.getName());
                request.setDestination(place.getLatLng());
            } else if (requestCode == PICKUP_REQUEST) {
                request.setPickupLocationName(place.getName());
                request.setPickupLocation(place.getLatLng());
            } else {    // unexpected cases
                return;
            }

            LatLng destinationLatLng = request.getDestination();
            LatLng pickupLatLng = request.getPickupLocation();

            if (destinationLatLng != null && pickupLatLng != null) {
                // update markers
                ((RiderHomeActivity) getActivity()).setMarkers(destinationLatLng, pickupLatLng);

                // update suggestedFare
                double suggestFare = FareCalculation.getPrice(FareCalculation.basePrice,
                        FareCalculation.multiplier, pickupLatLng, destinationLatLng);
                suggestFare = FareCalculation.round(suggestFare, 2);
                request.setSuggestedFare((float) suggestFare);
                request.setOfferedFare((float) suggestFare);
            }

            updateViews();

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // show the error info
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.e("PLACES", status.getStatusMessage());
        }
    }

    private void findViews(View view) {
        driverNameTextView = view.findViewById(R.id.create_ride_driver_name);
        driverUsernameTextView = view.findViewById(R.id.create_ride_driver_username);
        confirmButton = view.findViewById(R.id.create_ride_confirm_button);
        cancelButton = view.findViewById(R.id.create_ride_cancel_button);
        completeButton = view.findViewById(R.id.create_ride_complete_button);
        fareEditText = view.findViewById(R.id.create_ride_fare);
        destinationTextView = view.findViewById(R.id.create_ride_destination);
        pickupTextView = view.findViewById(R.id.create_ride_pickup);
    }

    private void updateViews() {
        // if destination/pickupLocation name exists, show the place name
        // show the LatLng otherwise
        String destinationString = request.getDestinationName();
        if (destinationString == null) {
            if (request.getDestination() == null) {
                destinationString = getResources().getString(R.string.select_a_place);
            } else {
                destinationString = "Lng: " + FareCalculation.round(request.getDestination().longitude, 8)
                        + " Lat: " + FareCalculation.round(request.getDestination().latitude, 10);
            }
        }
        destinationTextView.setText(destinationString);

        String pickupString = request.getPickupLocationName();
        if (pickupString == null) {
            if (request.getPickupLocation() == null) {
                pickupString = getResources().getString(R.string.select_a_place);
            } else {
                pickupString = "Lng: " + FareCalculation.round(request.getPickupLocation().longitude, 8)
                        + " Lat: " + FareCalculation.round(request.getPickupLocation().latitude, 10);
            }
        }
        pickupTextView.setText(pickupString);
        fareEditText.setText(String.valueOf(request.getOfferedFare()));
    }

    private void showDriverInfo(){
        driverNameTextView.setVisibility(View.VISIBLE);
        driverUsernameTextView.setVisibility(View.VISIBLE);

        Driver driver = MyDataBase.getInstance().getDriver(request.getDriverID());
        assert driver != null;

        driverNameTextView.setText(driver.getName());
        driverUsernameTextView.setText(driver.getUserName());
    }

    private void setGeneralOnClickListeners() {
        cancelButton.setOnClickListener( v -> {
            if (request.getStatus() != DriveRequest.Status.CREATE) {
                request.setStatus(DriveRequest.Status.CANCELLED);
                MyDataBase.getInstance().updateRequest(request);
            }

            // dismiss this fragment
            dismissFragment();
        });

        // show driver's contact info when clicking the driver name TextView
        driverUsernameTextView.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity(), UserContactActivity.class);
            intent.putExtra("USER_ID", request.getDriverID());
            startActivity(intent);
        });

        driverNameTextView.setOnClickListener( v -> {
            Intent intent = new Intent(getActivity(), UserContactActivity.class);
            intent.putExtra("USER_ID", request.getDriverID());
            startActivity(intent);
        });
    }


    private void dismissFragment() {
        Intent intent = new Intent(getActivity(), RiderHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
