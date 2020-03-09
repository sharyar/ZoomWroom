// geocode source: https://www.journaldev.com/15676/android-geocoder-reverse-geocoding

package com.example.zoomwroom;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Rider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DriverHomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        ArrayList<Marker> markers = new ArrayList<>();

        // Mock data
        Rider rider = new Rider("Sharyar Memon", "sharyarMemon", "sharyar@ualberta.ca");

        LatLng edmonton = new LatLng(53.5232, -113.5263);
        LatLng calgary = new LatLng(51.038431, -114.075507);

        DriveRequest newRequest = new DriveRequest(rider, edmonton, calgary);
        newRequest.setOfferedFare((float) 20.3);
        requests.add(newRequest);




        for (DriveRequest request: requests) {
            LatLng requestLocationStart = request.getPickupLocation();
            String riderName = request.getRider().getName();

            Marker m = mMap.addMarker(new MarkerOptions().position(requestLocationStart).title(riderName));
            m.setTag(request);
            markers.add(m);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(markers.get(0).getPosition()));

        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        DriveRequest pickedRequest = (DriveRequest) marker.getTag();
        showDriveRequestFragment(pickedRequest);
        return false;
    }


    public void showDriveRequestFragment(DriveRequest request) {

        Bundle b = new Bundle();

        b.putString("RiderName", request.getRider().getName());
        b.putFloat("OfferedFare", request.getOfferedFare());
        b.putString("PickupLocation", getAddressFromLocation(request.getPickupLocation()));
        b.putString("DropOffLocation", getAddressFromLocation(request.getDestination()));
        b.putDouble("Distance", getDistance(request));

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final FragmentDisplayDriveRequestInfo driveRequestFragment = new FragmentDisplayDriveRequestInfo();

        driveRequestFragment.setArguments(b);

        driveRequestFragment.show(getSupportFragmentManager(), driveRequestFragment.getTag());
    }

    private String getAddressFromLocation(LatLng location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");
                }

                return strAddress.toString();
            } else {
                return "Searching Current Address";
            }
        }

        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not get address", Toast.LENGTH_SHORT).show();
        }

        return null;
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


}
