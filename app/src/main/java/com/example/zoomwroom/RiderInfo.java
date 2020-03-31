package com.example.zoomwroom;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;


public class RiderInfo extends AppCompatActivity {
    protected TextView phoneNumber;
    protected TextView emailAddress;
    protected TextView usernameTextView;
    protected TextView nameTextView;
    protected TextView emailTextView;
    protected TextView phoneTextView;

    private Rider currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rider_contact_info);

        phoneNumber = findViewById(R.id.rider_info_phonenumber_content);
        emailAddress = findViewById(R.id.rider_info_email_content);

    }

    @Override
    public void onResume() {
        super.onResume();

        //retrieve username
        Intent intent = getIntent();
        String userID = intent.getStringExtra("USER_ID");

        // get the current user
        getCurrentUser(userID);
        //set usernameTextView
        setUsernameTextView();
        //set nameTextView
        setNameTextView();
        //set emailTextView
        setEmailTextView();
        //set phonenumber
        setPhoneNumber();
    }

    protected void setPhoneNumber() {
        phoneTextView = findViewById(R.id.rider_info_phonenumber_content);
        String stringPhoneNumber = currentUser.getContactDetails().getPhoneNumber();
        phoneTextView.setText(stringPhoneNumber);
    }

    protected void setEmailTextView() {
        emailTextView = findViewById(R.id.rider_info_email_content);
        String stringEmailAddress = currentUser.getContactDetails().getEmail();
        emailTextView.setText(stringEmailAddress);
    }

    protected void setNameTextView() {
        nameTextView = findViewById(R.id.rider_info_profile_name);
        String stringName = currentUser.getName();
        nameTextView.setText(stringName);
    }

    protected void setUsernameTextView() {
        usernameTextView = findViewById(R.id.rider_info_profile_username);
        String stringUserName = currentUser.getUserName();
        usernameTextView.setText(stringUserName);
    }

    protected void getCurrentUser(String userID) {
        currentUser = MyDataBase.getRider(userID);
    }


    public void CallNumber(View view){
        String phone = phoneNumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);

    }

    public void SendEmail(View view){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        System.out.println(emailAddress.getText().toString());
        i.putExtra(Intent.EXTRA_EMAIL  ,new String[]{emailAddress.getText().toString()});

        try {
            startActivity(i);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
