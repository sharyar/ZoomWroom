/**
 * @author Dulong Sang
 */

package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
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
    private boolean isDriver;
    private TextView usernameTextView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView phoneTextView;
    private TextView numThumbsUpTextView;
    private TextView numThumbsDownTextView;
    private ImageView thumbsUpImageView;
    private ImageView thumbsDownImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        Button editButton = findViewById(R.id.profile_edit_button);
        editButton.setOnClickListener(v -> {
            openEditProfileActivity();
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        // get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUser = MyDataBase.getDriver(user.getEmail());
            isDriver = true;
            if (currentUser == null) {
                currentUser = MyDataBase.getRider(user.getEmail());
                isDriver = false;
            }
        }

        findViews();
        usernameTextView.setText(currentUser.getUserID());
        nameTextView.setText(currentUser.getName());
        emailTextView.setText(currentUser.getContactDetails().getEmail());
        phoneTextView.setText(currentUser.getContactDetails().getPhoneNumber());

        if (isDriver) {
            numThumbsUpTextView.setText(String.valueOf(((Driver) currentUser).getRatings().getNumThumbsUp()));
            numThumbsDownTextView.setText(String.valueOf(((Driver) currentUser).getRatings().getNumThumbsDown()));
        } else {
            thumbsUpImageView.setVisibility(View.INVISIBLE);
            thumbsDownImageView.setVisibility(View.INVISIBLE);
            numThumbsUpTextView.setVisibility(View.INVISIBLE);
            numThumbsDownTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void findViews() {
        usernameTextView = findViewById(R.id.profile_username);
        nameTextView = findViewById(R.id.profile_name);
        emailTextView = findViewById(R.id.profile_email);
        phoneTextView = findViewById(R.id.profile_phone);
        numThumbsUpTextView = findViewById(R.id.profile_num_thumbsup);
        numThumbsDownTextView = findViewById(R.id.profile_num_thumbsdown);
        thumbsUpImageView = findViewById(R.id.profile_thumbs_up_icon);
        thumbsDownImageView = findViewById(R.id.profile_thumbs_down_icon);
    }

    private void openEditProfileActivity() {
        Intent intent = new Intent(this, EditUserProfileActivity.class);
        startActivity(intent);
    }

}
