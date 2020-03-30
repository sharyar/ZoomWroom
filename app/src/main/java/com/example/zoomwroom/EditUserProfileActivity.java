package com.example.zoomwroom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.Entities.User;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditUserProfileActivity extends AppCompatActivity {

    // Variables required for class
    User user;

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

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            Toast.makeText(EditUserProfileActivity.this,
                    "Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        user = MyDataBase.getDriver(firebaseUser.getEmail());
        if (user == null) {
            user = MyDataBase.getRider(firebaseUser.getEmail());
            if (user == null) {
                return;
            }
        }

        confirmChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserName = userNameEditText.getText().toString();
                if (!(newUserName.equalsIgnoreCase(user.getUserName()))) {
                    user.setName(newUserName);
                    if (!(MyDataBase.isUserNameUnique(userNameEditText.getText().toString()))) {
                        // not unique username
                        Toast.makeText(EditUserProfileActivity.this,
                                "This username is already taken, please use a different username",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                user.setName(fullNameEditText.getText().toString());
                user.getContactDetails().setPhoneNumber(phoneNumberEditText.getText().toString());

                if (user instanceof Driver) {
                    MyDataBase.updateDriver((Driver) user);
                } else if (user instanceof Rider) {
                    MyDataBase.updateRider((Rider) user);
                } else {
                    Toast.makeText(EditUserProfileActivity.this,
                            "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(EditUserProfileActivity.this,
                        "Your info has been updated.", Toast.LENGTH_SHORT).show();

                // wait firebase to update
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
            }
        });
    }
}
