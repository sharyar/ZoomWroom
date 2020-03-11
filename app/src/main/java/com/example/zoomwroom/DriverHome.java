package com.example.zoomwroom;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DriverHome extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

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

        DriveRequest newRequest = new DriveRequest(rider, edmonton, edmonton);
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final FragmentDisplayDriveRequestInfo driveRequestFragment = new FragmentDisplayDriveRequestInfo();

        driveRequestFragment.setArguments(b);

        driveRequestFragment.show(getSupportFragmentManager(), driveRequestFragment.getTag());
    }


}
