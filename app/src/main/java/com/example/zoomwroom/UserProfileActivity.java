/**
 * @author Dulong Sang
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

        Button backButton = findViewById(R.id.profile_back_button);
        backButton.setOnClickListener(v -> {
            finish();
        });

        findViews();
    }


    /**
     * This method will be called when the activity is resumed.
     * It will retrieve the current user from firebase in order to get the up-to-date info, and
     * display the user info.
     */
    @Override
    public void onResume() {
        super.onResume();

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
            return;
        }

        usernameTextView.setText(currentUser.getUserName());
        nameTextView.setText(currentUser.getName());
        emailTextView.setText(currentUser.getContactDetails().getEmail());
        phoneTextView.setText(currentUser.getContactDetails().getPhoneNumber());

        // display the number of thumbs up and down if the user is a driver.
        if (currentUser instanceof Driver) {
            numThumbsUpTextView.setText(String.valueOf(((Driver) currentUser).getRating().getThumbsUp()));
            numThumbsDownTextView.setText(String.valueOf(((Driver) currentUser).getRating().getThumbsDown()));
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
