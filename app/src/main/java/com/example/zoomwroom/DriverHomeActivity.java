package com.example.zoomwroom;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
public class DriverHomeActivity extends MapsActivity implements GoogleMap.OnMarkerClickListener {

    //private GoogleMap mMap; // Stores the instance google maps used to display the markers
    private ArrayList<Marker> markers = new ArrayList<>(); // stores a list of markers on the map

    ArrayList<DriveRequest> requests = new ArrayList<>();


    FloatingActionButton profileBtn; // Used to open the user's profile
    FloatingActionButton driveRequestListBtn;
    FloatingActionButton currentRequest;


    private String driverID;
    private String requestID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);



        //get current user;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        driverID = user.getEmail();

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("driverID", driverID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<DriveRequest> requests =  MyDataBase.getInstance().getDriverRequest(driverID);
                        for (DriveRequest request :requests) {
                            if (request.getStatus() == 2) {
                                Log.d("newToken", token);
                                String message = "Your offer has been accepted!";
                                new Notify(token, message).execute();
                            }
                        }

                    }
                });

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("driverID", driverID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            DriveRequest request = doc.toObject(DriveRequest.class);
                            request.toLocalMode();
                            if (request.getStatus() == 1 || request.getStatus() == 2 || request.getStatus() == 3 || request.getStatus() == 4) {
                                requestID = request.getRequestID();
                                Log.d("requestIDDDD", requestID);
                                break;
                            }
                        }

                    }
                });

        DriveRequest request = MyDataBase.getInstance().getCurrentRequest(driverID, "driverID");
        if (request != null) {
            requestID = request.getRequestID();
            Log.d("new request ID: ", requestID);
        }
        else{
            requestID = "null";
            Log.d("null", requestID);
        }

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("driverID", driverID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            DriveRequest request = doc.toObject(DriveRequest.class);
                            request.toLocalMode();
                            if (requestID != null) {
                                if (request.getStatus() == 5 && requestID.equals(request.getRequestID())) {
                                    String message = "Sorry, Your offer was cancelled";
                                    Log.d("send message", token);
                                    new Notify(token, message).execute();
                                }
                            }
                        }

                    }
                });

        requests = MyDataBase.getInstance().getOpenRequests();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Profile button - Open's user's profile
        profileBtn = findViewById(R.id.floatingActionButton);

        /*
        * Sets the listener so it open's the  driver's profile.
        */
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), UserProfileActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        driveRequestListBtn = findViewById(R.id.drive_request_list_btn_driver_home);
        driveRequestListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), AcceptedDriveRequestsActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        // Current request button
        currentRequest = findViewById(R.id.current_request);

        currentRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("userID", driverID);

                FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                final FragmentDriverCurrentRequest currentRequestFragment = new FragmentDriverCurrentRequest();
                currentRequestFragment.setArguments(b);
                currentRequestFragment.show(getSupportFragmentManager(), currentRequestFragment.getTag());
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
        // set mMap to the google map
        // creates two invisible markers for drive request
        super.onMapReady(googleMap);

        // Get the currently open requests from the database and stores them in an ArrayList
        updateMap();

        // Sets the markerclicklistener and allows the user to select a marker
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
    }

    @Override
    public void onMapClick(LatLng point) {updateMap();}

    /**
     * Opens a fragment to show the details of the currently selected request and allows the driver
     * to accept a request. Calls the showDriveRequestFragment.
     *
     * @param marker    user selected marker
     * @return          boolean value indicating if the fragment was consumed or not.
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        DriveRequest pickedRequest = (DriveRequest) marker.getTag();
        showDriveRequestFragment(pickedRequest);

        for (Marker m: markers) {
            m.setVisible(false);
        }
        this.mLocation.setDepart(pickedRequest.getPickupLocation());
        this.mLocation.setDestination(pickedRequest.getDestination());
        updateMarkers();
        return false;
    }

    /**
     * Bundles required information and passes it to the driveRequestFragment. Which then uses
     * the bundled information to display details of the drive request to the driver.
     *
     * @param request       currently selected request based on map marker click
     */
    public void showDriveRequestFragment(DriveRequest request) {

        Bundle b = new Bundle();

        b.putString("DriverID", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        b.putString("RiderName", MyDataBase.getInstance().getRider(request.getRiderID()).getName());
        b.putFloat("OfferedFare", request.getOfferedFare());
        b.putDouble("Distance", getDistance(request));
        b.putString("DriveRequestID", request.getRequestID());

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final FragmentDisplayDriveRequestInfo driveRequestFragment = new FragmentDisplayDriveRequestInfo();
        driveRequestFragment.setArguments(b);
        driveRequestFragment.show(getSupportFragmentManager(), driveRequestFragment.getTag());
    }

    /**
     * Calculates distance between the destination and pickup location.
     *
     * @param request       currently selected driveRequest
     * @return              double value indicating distance between the locations
     */
    public double getDistance(DriveRequest request) {
        double lat1 = request.getPickupLocation().latitude;
        double lat2 = request.getDestination().latitude;
        double long1 = request.getPickupLocation().longitude;
        double long2 = request.getDestination().longitude;
        return FareCalculation.getDistance(lat1, lat2, long1, long2);
    }


    /**
     * Resets the markers upon fragment closure so only open requests are displayed. Removes the
     * markers as needed as well.
     */
    public void resetMarkers() {
        departureMarker.setVisible(false);
        destinationMarker.setVisible(false);
        updateMap();
    }





    /**
     * This updates the currently open map with the markers for open requests. This called in several
     * places throughout this activity
     */
    public void updateMap() {
        // stores a list of currently open requests locally
        requests = MyDataBase.getInstance().getOpenRequests();

        if (!requests.isEmpty()) { // Checks to ensure that the ArrayList of requests is not empty
            /*
             * Iterates over the requests in the ArrayList and creates a map marker for each of
             * the requests.
             */
            for (DriveRequest request : requests) {
                LatLng requestLocationStart = request.getPickupLocation();
                String riderName = MyDataBase.getInstance().getRider(request.getRiderID()).getName();
                Marker m = mMap.addMarker(new MarkerOptions()
                                                .position(requestLocationStart)
                                                .title(riderName)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ride_request_marker)));
                m.setTag(request);
                markers.add(m);
            }

        } else {
            Toast.makeText(this, "No requests in your area currently.", Toast.LENGTH_SHORT).show();
        }
    }

}
