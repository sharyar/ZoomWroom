// geocode source: https://www.journaldev.com/15676/android-geocoder-reverse-geocoding

package com.example.zoomwroom;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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

public class DriverHomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers = new ArrayList<>();

    Marker pickupLocationMarker;
    Marker destinationLocationMarker;
    FloatingActionButton profileBtn;

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

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), EditUserProfileActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });
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

        // The first array list gets and stores the current active requests. The second array list is used to store markers for those requests
        ArrayList<DriveRequest> requests = new ArrayList<>();

        // Get the currently open requests from the database.
        requests = MyDataBase.getOpenRequests();

        if (!requests.isEmpty()) {
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


}
