package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.Entities.User;
import com.example.zoomwroom.Entities.UserDataValidation;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserProfileActivity extends AppCompatActivity {

    // Variables required for class
    User user;
    String fullName;
    String userName;
    String phoneNumber;
    String newUserName;

    TextInputEditText fullNameEditText;
    TextInputEditText userNameEditText;
    TextInputEditText phoneNumberEditText;

    Button confirmChangesBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");

        // Text Fields used within the activity
        fullNameEditText = findViewById(R.id.edit_user_full_name_editText);
        userNameEditText = findViewById(R.id.edit_user_name_editText);
        phoneNumberEditText = findViewById(R.id.edit_user_phone_editText);
        confirmChangesBtn = findViewById(R.id.edit_user_profile_confirm_btn);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(EditUserProfileActivity.this,
                    "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        user = MyDataBase.getInstance().getDriver(firebaseUser.getEmail());
        if (user == null) {
            user = MyDataBase.getInstance().getRider(firebaseUser.getEmail());
            if (user == null) {
                Toast.makeText(EditUserProfileActivity.this,
                        "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }

        fullNameEditText.setText(user.getName());
        userNameEditText.setText(user.getUserName());
        phoneNumberEditText.setText(user.getContactDetails().getPhoneNumber());

        confirmChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newUserName = userNameEditText.getText().toString().trim();
                fullName = fullNameEditText.getText().toString().trim();
                phoneNumber = PhoneNumberUtils.normalizeNumber(phoneNumberEditText.getText().toString());
                // validate data before committing to database and updating info

                if (isInfoValid()) {
                    user.setName(fullNameEditText.getText().toString());
                    user.getContactDetails().setPhoneNumber(phoneNumberEditText.getText().toString());

                    // update user profile
                    if (user instanceof Driver) {
                        MyDataBase.getInstance().updateDriver((Driver) user);
                    } else if (user instanceof Rider) {
                        MyDataBase.getInstance().updateRider((Rider) user);
                    } else {
                        Toast.makeText(EditUserProfileActivity.this,
                                "Something went wrong", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(EditUserProfileActivity.this,
                            "Your info has been updated.", Toast.LENGTH_SHORT).show();

                    // pass back changes to the user profile activity
                    Intent intent = new Intent();
                    intent.putExtra("username", newUserName);
                    intent.putExtra("name", fullName);
                    intent.putExtra("phoneNumber", phoneNumber);
                    setResult(RESULT_OK, intent);

                    finish();

                }


            }
        });
    }

    boolean isInfoValid() {
        if (!UserDataValidation.isFullNameValid(fullName)){
            Toast.makeText(this, "Please enter a valid name. A name can only contain alphabets & spaces.", Toast.LENGTH_SHORT).show();

            return false;

        } else if (!UserDataValidation.isAlphaNumeric(newUserName)) {
            Toast.makeText(this, "Username can only have alphabets or numbers.", Toast.LENGTH_SHORT).show();

            return false;

        } else if (!(newUserName.equals(user.getUserName()))) {
            if (MyDataBase.getInstance().isUserNameUnique(newUserName)) {
                Toast.makeText(this, "Please use a unique username, this username is already taken.", Toast.LENGTH_SHORT).show();
            }
            return false;

        } else if (!UserDataValidation.isPhoneNumberValid(phoneNumber)) {
            Toast.makeText(this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return false;

        } else
            return true;
    }
}
