package com.example.zoomwroom;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.database.MyDataBase;

public class DriverInfo extends RiderInfo {
    
    protected TextView numThumbsUpTextView;
    protected TextView numThumbsDownTextView;

    private Driver currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_contact_info);

        phoneNumber = findViewById(R.id.driver_info_phonenumber_content);
        emailAddress = findViewById(R.id.driver_info_email_content);

    }

    @Override
    public void onResume() {
        super.onResume();

        //set numThumbsUpTextView
        numThumbsUpTextView = findViewById(R.id.profile_num_thumbsup);
        numThumbsUpTextView.setText(String.valueOf(currentUser.getRating().getThumbsUp()));

        //set numThumbsDownTextView
        numThumbsDownTextView = findViewById(R.id.profile_num_thumbsdown);
        numThumbsDownTextView.setText(String.valueOf(currentUser.getRating().getThumbsUp()));
    }

    @Override
    protected void setPhoneNumber() {
        phoneTextView = findViewById(R.id.driver_info_phonenumber_content);
        String stringPhoneNumber = currentUser.getContactDetails().getPhoneNumber();
        phoneTextView.setText(stringPhoneNumber);
    }

    @Override
    protected void setEmailTextView() {
        emailTextView = findViewById(R.id.driver_info_email_content);
        String stringEmailAddress = currentUser.getContactDetails().getEmail();
        emailTextView.setText(stringEmailAddress);
    }

    @Override
    protected void setNameTextView() {
        nameTextView = findViewById(R.id.driver_info_profile_name);
        String stringName = currentUser.getName();
        nameTextView.setText(stringName);
    }

    @Override
    protected void setUsernameTextView() {
        usernameTextView = findViewById(R.id.driver_info_profile_username);
        String stringUserName = currentUser.getUserName();
        usernameTextView.setText(stringUserName);
    }

    @Override
    protected void getCurrentUser(String userID) {
        currentUser = MyDataBase.getDriver(userID);
    }


    public void CallNumber(View view){
        super.CallNumber(view);
    }

    public void SendEmail(View view){
        super.SendEmail(view);
    }
}
