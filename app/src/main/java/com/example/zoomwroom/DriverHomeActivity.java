package com.example.zoomwroom;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * DriverHomeActivity
 *
 * Serves as the Activity where a driver can pick up new jobs. It uses google maps to display
 * markers for currently open requests (with status pending).
 *
 * March 12, 2020
 *
 * @author Sharyar Memon, Henry Lin
 *
 * Sources: https://www.androdocs.com/java/getting-current-location-latitude-longitude-in-android-using-java.html
 *
 */
public class DriverHomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Stores the instance google maps used to display the markers
    private ArrayList<Marker> markers = new ArrayList<>(); // stores a list of markers on the map

    /* Both of the following fields are only used for the currently selected request */
    Marker pickupLocationMarker; // used to mark the location of the pickup
    Marker destinationLocationMarker; // used to mark the location of the drop off

    LatLng driverLocation; // stores driver's current location. Used to set the default position of map

    FloatingActionButton profileBtn; // Used to open the user's profile

    int PERMISSION_ID = 44; // used for driver's current location permissions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Profile button - Open's user's profile
        profileBtn = findViewById(R.id.floatingActionButton);

        /*
        * Sets the listener so it open's the  driver's profile. Currently set to the edit profile
        * activity as the user profile activity has not been completed.
        */
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), EditUserProfileActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Used for getting driver's current location and permissions for location

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Get the currently open requests from the database and stores them in an ArrayList
        ArrayList<DriveRequest> requests = MyDataBase.getOpenRequests();


        if (!requests.isEmpty()) { // Checks to ensure that the ArrayList of requests is not empty
            /*
             * Iterates over the requests in the arraylist and creates a map marker for each of
             * the requests.
             */
            for (DriveRequest request : requests) {
                LatLng requestLocationStart = request.getPickupLocation();
                String riderName = MyDataBase.getRider(request.getRiderID()).getName();
                Marker m = mMap.addMarker(new MarkerOptions().position(requestLocationStart).title(riderName));
                m.setTag(request);
                markers.add(m);
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
        } else {

            Toast.makeText(this, "No requests in your area currently.", Toast.LENGTH_SHORT).show();
        }

        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        DriveRequest pickedRequest = (DriveRequest) marker.getTag();
        showDriveRequestFragment(pickedRequest);

        for (Marker m: markers) {
            m.setVisible(false);
        }
        pickupLocationMarker = mMap.addMarker(new MarkerOptions().position(pickedRequest.getPickupLocation()).title("Pickup"));
        destinationLocationMarker = mMap.addMarker(new MarkerOptions().position(pickedRequest.getDestination()).title("Destination"));

        LatLngBounds currentRequestBounds = new LatLngBounds(pickedRequest.getPickupLocation(), pickedRequest.getDestination());
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(currentRequestBounds,0));
        return false;
    }


    public void showDriveRequestFragment(DriveRequest request) {

        Bundle b = new Bundle();
//
//        b.putString("RiderName", request.getRider().getName());
        b.putFloat("OfferedFare", request.getOfferedFare());
        b.putDouble("Distance", getDistance(request));

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final FragmentDisplayDriveRequestInfo driveRequestFragment = new FragmentDisplayDriveRequestInfo();
        driveRequestFragment.setArguments(b);
        driveRequestFragment.show(getSupportFragmentManager(), driveRequestFragment.getTag());
    }


    public double getDistance(DriveRequest request) {
        final int R = 6371; // Radius of the earth in Km
        Double latDistance = MapsActivity.toRad(request.getPickupLocation().latitude
                - request.getDestination().latitude);
        Double lonDistance = MapsActivity.toRad(request.getPickupLocation().longitude
                - request.getDestination().longitude);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(MapsActivity.toRad(request.getPickupLocation().latitude))*
                        Math.cos(MapsActivity.toRad(request.getDestination().latitude)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }


    // This method resets the markers so they go back to what they were before the user clicked the map marker.
    public void resetMarkers() {
        for (Marker m: markers) {
            m.setVisible(true);
            pickupLocationMarker.remove();
            destinationLocationMarker.remove();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));
    }

    // Checks if the app has location permission
    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }



}
