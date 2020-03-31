//sources: https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account

package com.example.zoomwroom;

import android.content.Intent;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.Rider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseUser;

public class RiderSignUpActivity extends SignupActivity {

    protected void CreateUser(FirebaseUser user) {
        //First create a new rider instance, new contact info instance and then add them to the database
        Rider newRider = new Rider(firstNameText + " " + lastNameText, userName, email);
        ContactInformation cInformation = new ContactInformation(phoneNumber, email);
        newRider.setContactDetails(cInformation);

        assert user != null;

        database.collection("Riders").document(user.getUid()).set(newRider)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RiderSignUpActivity.this, "You are now signed up!",
                                Toast.LENGTH_SHORT).show();
                        OpenHomeActivity();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    protected void getViewContent() {
        setContentView(R.layout.activity_rider_sign_up);
        bar = findViewById(R.id.rider_signup_progress_bar);

        firstNameEditText = findViewById(R.id.riderSignupFName);
        lastNameEditText = findViewById(R.id.riderSignupLName);
        emailAddressEditText = findViewById(R.id.riderSignupEmailAddress);
        passWordEditText = findViewById(R.id.riderSignupPassWord);
        userNameEditText = findViewById(R.id.riderSignupUserName);
        phoneNumberEditText = findViewById(R.id.riderSignupPhoneNumber);

        signUpUser = findViewById(R.id.riderSignupSignupBtn);
        BackBtn = findViewById(R.id.riderSignupBackBtn);
    }

    /**
     * switch mode between driver and rider
     */

    public void ReturnToLogin() {
        Intent intent = new Intent(this, RiderLoginActivity.class);
        startActivity(intent);
    }

    /**
     * Opens into the Rider's main page if login is successful
     */
    public void OpenHomeActivity() {
        Intent intent = new Intent(this, RiderHomeActivity.class);
        startActivity(intent);
    }
}
