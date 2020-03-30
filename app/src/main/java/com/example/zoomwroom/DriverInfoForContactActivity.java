package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.database.MyDataBase;

public class DriverInfoForContactActivity extends AppCompatActivity {
    TextView driverphonenumber;
    TextView driveremailaddress;
    private Driver currentUser;
    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView numThumbsUpTextView;
    private TextView numThumbsDownTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_infos_for_contact_activitys);

        driverphonenumber = findViewById(R.id.driver_info_phonenumber_content);
        driveremailaddress = findViewById(R.id.driver_info_email_content);

    }
    @Override
    public void onResume() {
        super.onResume();

        //retrieve driverid
        Intent intent = getIntent();
        String driverID = intent.getStringExtra("DRIVER_ID");

        // get the current user
        currentUser = MyDataBase.getDriver(driverID);

        //set usernameTextView
        usernameTextView = findViewById(R.id.driver_info_profile_username);
        String stringuserName = currentUser.getUserName();
        usernameTextView.setText(stringuserName);

        //set nameTextView
        nameTextView = findViewById(R.id.driver_info_profile_name);
        String stringName = currentUser.getName();
        nameTextView.setText(stringName);

        //set emailTextView
        emailTextView = findViewById(R.id.driver_info_email_content);
        String stringemailaddress = currentUser.getContactDetails().getEmail();
        emailTextView.setText(stringemailaddress);

        //set phonenumber
        phoneTextView = findViewById(R.id.driver_info_phonenumber_content);
        String stringphonenumber = currentUser.getContactDetails().getPhoneNumber();
        phoneTextView.setText(stringphonenumber);

        //set numThumbsUpTextView
        numThumbsUpTextView = findViewById(R.id.profile_num_thumbsup);
        numThumbsUpTextView.setText(String.valueOf(currentUser.getRating().getThumbsUp()));




        //set numThumbsDownTextView
        numThumbsDownTextView = findViewById(R.id.profile_num_thumbsdown);
        numThumbsDownTextView.setText(String.valueOf(currentUser.getRating().getThumbsUp()));



    }

    public void makecalltodriver(View view){
        String phone = driverphonenumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);

    }

    public void sendemailtodriver(View view){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        System.out.println(driveremailaddress.getText().toString());
        i.putExtra(Intent.EXTRA_EMAIL  ,new String[]{driveremailaddress.getText().toString()});

        try {
            startActivity(i);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DriverInfoForContactActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
