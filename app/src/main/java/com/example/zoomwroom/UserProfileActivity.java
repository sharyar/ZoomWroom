/**
 * @author Dulong Sang
 * last update: Apr 1, 2020
 */

package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.User;
import com.example.zoomwroom.database.MyDataBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserProfileActivity extends AppCompatActivity {

    private User currentUser;
    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView numThumbsUpTextView;
    private TextView numThumbsDownTextView;
    private View ratingViews;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        Button editButton = findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(v -> {
            openEditProfileActivity();
        });

        Button backButton = findViewById(R.id.profile_back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });

        findViews();
        findUser();
        setViews();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String username = data.getStringExtra("username");
        String name = data.getStringExtra("name");
        String phoneNumber = data.getStringExtra("phoneNumber");

        currentUser.setUserName(username);
        currentUser.setName(name);
        currentUser.getContactDetails().setPhoneNumber(phoneNumber);

        setViews();
    }


    public void setViews() {
        usernameTextView.setText(currentUser.getUserName());
        nameTextView.setText(currentUser.getName());
        emailTextView.setText(currentUser.getContactDetails().getEmail());
        phoneTextView.setText(currentUser.getContactDetails().getPhoneNumber());

        // display the number of thumbs up and down if the user is a driver.
        if (currentUser instanceof Driver) {
            numThumbsUpTextView.setText(String.valueOf(((Driver) currentUser).getRating().getThumbsUp()));
            numThumbsDownTextView.setText(String.valueOf(((Driver) currentUser).getRating().getThumbsDown()));
        } else {
            ratingViews.setVisibility(View.INVISIBLE);
        }
    }


    private void findUser() {
        // get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUser = MyDataBase.getInstance().getDriver(user.getEmail());
            if (currentUser == null) {
                currentUser = MyDataBase.getInstance().getRider(user.getEmail());
            }
        }
        if (currentUser == null) {
            Log.e("Profile", "User not found");
            finish();
        }
    }


    private void findViews() {
        usernameTextView = findViewById(R.id.profile_username);
        nameTextView = findViewById(R.id.profile_name);
        emailTextView = findViewById(R.id.profile_email);
        phoneTextView = findViewById(R.id.profile_phone);
        numThumbsUpTextView = findViewById(R.id.profile_num_thumbsup);
        numThumbsDownTextView = findViewById(R.id.profile_num_thumbsdown);
        ratingViews = findViewById(R.id.profile_rating);
    }


    private void openEditProfileActivity() {
        Intent intent = new Intent(this, EditUserProfileActivity.class);
        startActivityForResult(intent, 1);
    }


}
