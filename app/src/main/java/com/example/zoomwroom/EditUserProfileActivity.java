package com.example.zoomwroom;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class EditUserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");


        // Text Fields used within the activity
        TextInputEditText firstNameEditText = findViewById(R.id.edit_user_first_name_editText);
        TextInputEditText lastNameEditText = findViewById(R.id.edit_user_last_name_editText);
        TextInputEditText userNameEditText = findViewById(R.id.edit_user_name_editText);
        TextInputEditText phoneNumberEditText = findViewById(R.id.edit_user_phone_editText);
        Button confirmChangesBtn = findViewById(R.id.edit_user_profile_confirm_btn);


    }
}
