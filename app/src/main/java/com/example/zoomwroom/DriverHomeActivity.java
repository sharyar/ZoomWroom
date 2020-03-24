package com.example.zoomwroom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import android.location.Location;
import java.util.ArrayList;
import android.os.Looper;

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

    ArrayList<DriveRequest> requests = new ArrayList<>();

    /* Both of the following fields are only used for the currently selected request */
    Marker pickupLocationMarker; // used to mark the location of the pickup
    Marker destinationLocationMarker; // used to mark the location of the drop off

    LatLng driverLocation; // stores driver's current location. Used to set the default position of map

    FloatingActionButton profileBtn; // Used to open the user's profile
    FloatingActionButton driveRequestListBtn;

    // Required for current driver location
    int PERMISSION_ID = 44; // used for driver's current location permissions
    FusedLocationProviderClient mFusedLocationClient;

    private String token;
    private String driverID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        // This gets the user's current location. Currently display California as the emulator does not use current location.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        // This gets device's token. Used for notification
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.d("newToken-----", token);
            }
        });

        //get current user;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        driverID = user.getEmail();

        FirebaseFirestore.getInstance().collection("DriverRequest")
                .whereEqualTo("driverID", driverID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        ArrayList<DriveRequest> requests =  MyDataBase.getDriverRequest(driverID);
                        for (DriveRequest request :requests) {
                            if (request.getStatus() == 2) {
                                Log.d("newToken", token);
                                String message = "Your offer has been accepted!";
                                new Notify(token, message).execute();
                            }
                        }

                    }
                });

        requests = MyDataBase.getOpenRequests();
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
                Intent myIntent = new Intent(v.getContext(), AcceptedRequestActivity.class);
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

        // Get the currently open requests from the database and stores them in an ArrayList
        updateMap();

        // Sets the markerclicklistener and allows the user to select a marker
        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
    }

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
        pickupLocationMarker = mMap.addMarker(new MarkerOptions().position(pickedRequest.getPickupLocation()).title("Pickup"));
        destinationLocationMarker = mMap.addMarker(new MarkerOptions().position(pickedRequest.getDestination()).title("Destination"));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(pickedRequest.getPickupLocation()).include(pickedRequest.getDestination());

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(),150,200,10);
        mMap.moveCamera(cameraUpdate);
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
        b.putString("RiderName", MyDataBase.getRider(request.getRiderID()).getName());
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


    /**
     * Resets the markers upon fragment closure so only open requests are displayed. Removes the
     * markers as needed as well.
     */
    public void resetMarkers() {
        pickupLocationMarker.remove();
        destinationLocationMarker.remove();
        updateMap();
    }

    /**
     * Uses to get the user's last location.
     */
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    driverLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, 12.0f));
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    /**
     * This function is called if the lastlocation value is null.
     */
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    /**
     * Updates the camera location based on user's current location.
     */
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            driverLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(driverLocation, 12.0f));
        }
    };

    /**
     * Checks if the user has given location permissions to the app to avoid exceptions related
     * to that issue.
     *
     * @return      boolean value indicating the status of location permission by user.
     */
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    /**
     * Requests user permission for location access
     */
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    /**
     * Checks if the user's location has been enabled in the settings.
     *
     * @return      boolean indicating status of location setting (phone location setting)
     */
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    /**
     * Checks if user has given permission, check if location is enabled in settings and then calls
     * the getLastLocation function to get the user's location if the above conditions are satisfied
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    /**
     * If the activity is resumed, it updates the user's location if required.
     */
    @Override
    public void onResume(){
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    /**
     * This updates the currently open map with the markers for open requests. This called in several
     * places throughout this activity
     */
    public void updateMap() {
        // stores a list of currently open requests locally
        requests = MyDataBase.getOpenRequests();

        if (!requests.isEmpty()) { // Checks to ensure that the ArrayList of requests is not empty
            /*
             * Iterates over the requests in the ArrayList and creates a map marker for each of
             * the requests.
             */
            for (DriveRequest request : requests) {
                LatLng requestLocationStart = request.getPickupLocation();
                String riderName = MyDataBase.getRider(request.getRiderID()).getName();
                Marker m = mMap.addMarker(new MarkerOptions().position(requestLocationStart).title(riderName));
                m.setTag(request);
                markers.add(m);
            }

        } else {
            Toast.makeText(this, "No requests in your area currently.", Toast.LENGTH_SHORT).show();
        }
    }

}
