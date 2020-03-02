package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////
        // Driver Button
        Button driverBT = findViewById(R.id.DriverBT);
        driverBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityDriverMode();
            }
        });
        //Rider Button
        Button riderBT = findViewById(R.id.RiderBT);
        riderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityRiderMode();
            }
        });
        /////////////////////////////////

        // Map button bypass
        Button mapbtn = findViewById(R.id.bypasstomap);
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityMaps();
            }
        });
    }

    public void OpenActivityMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    ////////////////////////////////////////////////

    public void OpenActivityRiderMode(){
        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);
    }

    //OpenActivityDriverMode
    public void OpenActivityDriverMode() {

        Intent intent = new Intent(this,DriverModeActivity.class);
        startActivity(intent);

    }
    ///////////////////////////////////////////////
}
