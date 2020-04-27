package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class DriveRequestDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_request_details);

        DriveRequest currentRequest;
        String driveRequestId;

        TextInputEditText riderName;
        TextInputEditText offeredFare;
        TextInputEditText dateOfRequest;
        TextInputEditText status;
        TextInputEditText distance;

        riderName = findViewById(R.id.drive_request_details_riders_name);
        offeredFare = findViewById(R.id.drive_request_details_offered_fare);
        dateOfRequest = findViewById(R.id.drive_request_details_date);
        status = findViewById(R.id.drive_request_details_status);
        distance = findViewById(R.id.drive_request_details_distance);

        Intent intent = getIntent();
        driveRequestId = intent.getStringExtra("requestID");
        currentRequest = MyDataBase.getInstance().getDriveRequestByID(driveRequestId);

        if (currentRequest!= null) {
            riderName.setText(MyDataBase.getInstance().getRider(currentRequest.getRiderID()).getName());
            offeredFare.setText(String.format(Locale.CANADA, "$ %.2f", currentRequest.getOfferedFare()));
            dateOfRequest.setText(currentRequest.getRequestDateTime().toString());
            status.setText(DriveRequest.giveStatus(currentRequest.getStatus()));
            distance.setText(String.valueOf(FareCalculation.getDistance(
                    currentRequest.getPickupLocation(), currentRequest.getDestination())));
        }
    }
}
