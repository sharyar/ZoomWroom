package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RiderSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_sign_up);

        ///if user click back button it will return back to their mode activity

        Button rider_backBT = findViewById(R.id.rider_BackBT);
        rider_backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRiderModeActivity();
            }
        });
    }

    public void OpenRiderModeActivity() {

        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);
    }



}
