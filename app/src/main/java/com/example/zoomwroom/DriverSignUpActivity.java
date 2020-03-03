package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        //Click Back button return to the main activity

        Button BackBT = findViewById(R.id.SignupActivity_BackBT);
        BackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDriverModeActivity();
            }
        });
    }
    public void OpenDriverModeActivity(){
        Intent intent = new Intent(this,DriverModeActivity.class);
        startActivity(intent);
    }
}
