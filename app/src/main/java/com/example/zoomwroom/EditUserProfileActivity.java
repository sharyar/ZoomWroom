package com.example.zoomwroom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserProfileActivity extends AppCompatActivity {

    // Variables required for class
    Boolean isRider = false;
    Boolean isDriver = false;
    Driver driver = null;
    Rider rider = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");

        // Text Fields used within the activity
        TextInputEditText fullNameEditText = findViewById(R.id.edit_user_full_name_editText);
        TextInputEditText userNameEditText = findViewById(R.id.edit_user_name_editText);
        TextInputEditText phoneNumberEditText = findViewById(R.id.edit_user_phone_editText);
        Button confirmChangesBtn = findViewById(R.id.edit_user_profile_confirm_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (MyDataBase.getDriver(user.getEmail()) != null) {
                isDriver = true;
                driver = MyDataBase.getDriver(user.getEmail());

                fullNameEditText.setText(driver.getName());
                userNameEditText.setText(driver.getUserName());
                phoneNumberEditText.setText(driver.getContactDetails().getPhoneNumber());



            } else if (MyDataBase.getRider(user.getEmail()) != null) {
                isRider = true;
                rider = MyDataBase.getRider(user.getEmail());

                fullNameEditText.setText(rider.getName());
                userNameEditText.setText(rider.getUserName());
                phoneNumberEditText.setText(rider.getContactDetails().getPhoneNumber());
            }

            confirmChangesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isDriver) {

                        if (userNameEditText.getText().toString().equalsIgnoreCase(driver.getUserName())) {
                            driver.setName(fullNameEditText.getText().toString());
                            driver.getContactDetails().setPhoneNumber(phoneNumberEditText.getText().toString());

                            MyDataBase.updateDriver(driver);

                            Toast.makeText(EditUserProfileActivity.this, "Your info has been updated.", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {

                            if (MyDataBase.isUserNameUnique(userNameEditText.getText().toString())) {
                                driver.setName(fullNameEditText.getText().toString());
                                driver.setUserName(userNameEditText.getText().toString());
                                driver.getContactDetails().setPhoneNumber(phoneNumberEditText.getText().toString());

                                MyDataBase.updateDriver(driver);

                                Toast.makeText(EditUserProfileActivity.this, "Your info has been updated.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(EditUserProfileActivity.this,
                                        "This username is already taken, please " +
                                                "use a different username", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else if (isRider) {

                        if (userNameEditText.getText().toString().equalsIgnoreCase(rider.getUserName())) {
                            rider.setName(fullNameEditText.getText().toString());
                            rider.getContactDetails().setPhoneNumber(phoneNumberEditText.getText().toString());

                            MyDataBase.updateRider(rider);

                            Toast.makeText(EditUserProfileActivity.this, "Your info has been updated.", Toast.LENGTH_SHORT).show();
                            finish();

                        } else {

                            if (MyDataBase.isUserNameUnique(userNameEditText.getText().toString())) {
                                rider.setName(fullNameEditText.getText().toString());
                                rider.setUserName(userNameEditText.getText().toString());
                                rider.getContactDetails().setPhoneNumber(phoneNumberEditText.getText().toString());

                                MyDataBase.updateRider(rider);

                                Toast.makeText(EditUserProfileActivity.this, "Your info has been updated.", Toast.LENGTH_SHORT).show();
                                finish();

                            } else {
                                Toast.makeText(EditUserProfileActivity.this,
                                        "This username is already taken, please " +
                                                "use a different username", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else
                        Toast.makeText(EditUserProfileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Please sign in again", Toast.LENGTH_SHORT).show();
        }

    }


}
