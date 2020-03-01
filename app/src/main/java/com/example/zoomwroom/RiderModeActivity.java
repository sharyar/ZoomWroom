package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RiderModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_mode);

        // change to driver Mode
        Button DrivermodeBT = findViewById(R.id.RiderActivityChangemodeBT);
        DrivermodeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeModetoDriver();
            }
        });
    }

    public void ChangeModetoDriver() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
