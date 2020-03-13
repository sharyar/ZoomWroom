package com.example.zoomwroom.Entities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomwroom.R;
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
    private TextView getNumThumbsDownTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);
    }


    @Override
    public void onResume() {
        super.onResume();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            currentUser = MyDataBase.getDriver(user.getEmail());
            if (currentUser == null) {
                currentUser = MyDataBase.getRider(user.getEmail());
            }
        }
        findViews();
        usernameTextView.setText(currentUser.getUserID());
        nameTextView.setText(currentUser.getName());
        emailTextView.setText(currentUser.getContactDetails().getEmail());
        phoneTextView.setText(currentUser.getContactDetails().getPhoneNumber());
        // TODO: num of thumbs up
        // TODO: num of thumbs down
    }

    private void findViews() {
        usernameTextView = findViewById(R.id.profile_username);
        nameTextView = findViewById(R.id.profile_name);
        emailTextView = findViewById(R.id.profile_email);
        phoneTextView = findViewById(R.id.profile_phone);
        numThumbsUpTextView = findViewById(R.id.profile_num_thumbsup);
        getNumThumbsDownTextView = findViewById(R.id.profile_num_thumbsdown);
    }

}
