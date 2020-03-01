package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SIGNUP BUTTON
        Button signBT = findViewById(R.id.SignupBT);
        signBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignUpActivity();
            }
        });

        //Change mode between rider mode and driver mode
        Button RiderModeBT = findViewById(R.id.RidermodeBT);
        RiderModeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityRiderMode();
            }
        });

    }

    public void OpenSignUpActivity(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);


    }

    public void OpenActivityRiderMode(){
        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);
    }

}
