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
        Button DrivermodeBT = findViewById(R.id.rider_chagentodrivermodeBT);
        DrivermodeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityDriverMode();
            }
        });
        //rider mode sign up button
        Button signUpBT = findViewById(R.id.rider_SignupBT);
        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRiderSignUpActivity();
            }
        });
    }
    /**
     * switch mode between driver and rider
     */


    public void OpenActivityDriverMode() {
        Intent intent = new Intent(this,DriverModeActivity.class);
        startActivity(intent);
    }

    /**
     * change activity to rider signup page for driver to sign up
     */


    public void OpenRiderSignUpActivity() {
        Intent intent = new Intent(this,RiderSignUpActivity.class);
        startActivity(intent);


    }
}
