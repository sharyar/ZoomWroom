
package com.example.zoomwroom;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.zoomwroom.Entities.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Google Maps Activity
 *
 * Author : Henry Lin
 * Creates a google map fragment where markers can be placed
 *
 * @see Location
 * @see com.google.android.gms.maps.GoogleMap
 *
 * Modified source from: https://developers.google.com/maps/documentation/android-sdk/start
 * Under the Apache 2.0 license
 */
public abstract class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    protected GoogleMap mMap;
    protected Location mLocation;

    protected Marker departureMarker;
    protected Marker destinationMarker;
    protected LatLng userLocation;
    protected String token;

    // Required for current driver location
    int PERMISSION_ID = 44; // used for driver's current location permissions
    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This gets device's token. Used for notification
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.d("newToken-----", token);
            }
        });
        //setContentView(R.layout.activity_maps);
        mLocation = new Location();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Log.d("MAP FRAGMENT", "ABOUT TO GET SUPPORT FRAGMENT MANAGER");

        // This gets the user's current location. Currently display California as the emulator does not use current location.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;




        mMap.setOnMapClickListener(this);


        departureMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.5232, -113.5263))
                .title("Departure")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_marker_a))
                .alpha(0.71f));
        destinationMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(53.5232, -113.5263))
                .title("Destination")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_marker_b))
                .alpha(0.71f));

        departureMarker.setVisible(false);
        destinationMarker.setVisible(false);

        Log.d("LOCATION SERVICES", "GETTING LOCATION");
        getLastLocation();

    }


    @Override
    public abstract void onMapClick(LatLng point);

    /**
     * Moves markers to the current latlng position and updates the estimated fare
     */
    public void updateMarkers() {
        LatLng depart = mLocation.getDepart();
        LatLng destination = mLocation.getDestination();
        if (!depart.equals(departureMarker.getPosition())) {
            departureMarker.setPosition(depart);
            departureMarker.setVisible(true);
        }
        if (!destination.equals(destinationMarker.getPosition())) {
            destinationMarker.setPosition(destination);
            destinationMarker.setVisible(true);
        }

        if (destinationMarker.isVisible() && departureMarker.isVisible()) {
            double price = FareCalculation.getPrice(5.00, 0.5, departureMarker, destinationMarker);
            Log.d("Price", Double.toString(price));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(depart).include(destination);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(),10);
            Log.d("CameraMove", "Updating to new markers");
            mMap.moveCamera(cameraUpdate);
        }
    }



    /**
     * Uses to get the user's last location.
     */
    protected void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<android.location.Location>() {
                            @Override
                            public void onComplete(@NonNull Task<android.location.Location> task) {
                                android.location.Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                    Log.d("Camera Move", "Updating to getLastLocation");
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12.0f));
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
    protected LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            android.location.Location mLastLocation = locationResult.getLastLocation();
            userLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            Log.d("CameraMove", "Update to mLocationCallback");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 12.0f));
        }
    };

    /**
     * Checks if the user has given location permissions to the app to avoid exceptions related
     * to that issue.
     *
     * @return      boolean value indicating the status of location permission by user.
     */
    protected boolean checkPermissions() {
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
}


