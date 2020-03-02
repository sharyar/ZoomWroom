package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.Button;

public class DriverModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode);

        //SIGNUP BUTTON
        Button signBT = findViewById(R.id.driver_SignupBT);
        signBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDriverSignUpActivity();
            }
        });

        //Change mode between rider mode and driver mode
        Button RiderModeBT = findViewById(R.id.driver_chagentoRidermodeBT);
        RiderModeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityRiderMode();
            }
        });

    }

    public void OpenDriverSignUpActivity(){
        Intent intent = new Intent(this,DriverSignUpActivity.class);
        startActivity(intent);


    }
    public void OpenActivityRiderMode() {
        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);

    }
}
