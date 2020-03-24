package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.textfield.TextInputEditText;

public class ActivityDriveRequestDetails extends AppCompatActivity {

    private DriveRequest currentRequest;
    private String driveRequestId;


    private TextInputEditText riderName;
    private TextInputEditText offeredFare;
    private TextInputEditText dateOfRequest;
    private TextInputEditText status;
    private TextInputEditText distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_request_details);

        riderName = findViewById(R.id.drive_request_details_riders_name);
        offeredFare = findViewById(R.id.drive_request_details_offered_fare);
        dateOfRequest = findViewById(R.id.drive_request_details_date);
        status = findViewById(R.id.drive_request_details_status);
        distance = findViewById(R.id.drive_request_details_distance);

        Intent intent = getIntent();
        driveRequestId = intent.getStringExtra("requestID");
        currentRequest = MyDataBase.getDriveRequestByID(driveRequestId);

        if (currentRequest!= null) {
            riderName.setText(MyDataBase.getRider(currentRequest.getRiderID()).getName());
            offeredFare.setText("$ " + Double.toString(currentRequest.getOfferedFare()));
            dateOfRequest.setText(currentRequest.getRequestDateTime().toString());
            status.setText(DriveRequest.giveStatus(currentRequest.getStatus()));
        }


    }
}
