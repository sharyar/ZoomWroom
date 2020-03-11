//sources: https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account

package com.example.zoomwroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.Rider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class RiderSignUpActivity extends AppCompatActivity {
    Rider test;

    // Write this to get access to the database! NEED!
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    //Used for user authentication and signup
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_sign_up);

        //Get Auth instance from Firebase
        mAuth = FirebaseAuth.getInstance();

        EditText firstNameEditText = findViewById(R.id.riderSignupFName);
        EditText lastNameEditText = findViewById(R.id.riderSignupLName);
        EditText emailAddressEditText = findViewById(R.id.riderSignupEmailAddress);
        EditText passWordEditText = findViewById(R.id.riderSignupPassWord);
        EditText userNameEditText = findViewById(R.id.riderSignupUserName);
        EditText phoneNumberEditText = findViewById(R.id.riderSignupPhoneNumber);
        Button signUpRider = findViewById(R.id.riderSignupSignupBtn);


        signUpRider.setOnClickListener((View v) -> {
            String email = emailAddressEditText.getText().toString().trim();
            String passWord = passWordEditText.getText().toString();
            String firstNameText = firstNameEditText.getText().toString().trim();
            String lastNameText = lastNameEditText.getText().toString().trim();
            String userName = userNameEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();


            // If conditions checks to make sure that the fields have been filled out and are not null. Prevents a crash.
            if (email.isEmpty() || passWord.isEmpty() || firstNameText.isEmpty() ||
                    lastNameText.isEmpty() || userName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(RiderSignUpActivity.this, "Please ensure you have " +
                        "filled out all the fields.", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, passWord)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:Success");
                                    //Get the newly created user. We can use this to actually build the contact info page.
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(RiderSignUpActivity.this, "You are now signed up!",
                                            Toast.LENGTH_SHORT).show();

                                    // Add data from other fields only if registration is successful.

                                    //First create a new rider instance, new contact info instance and then add them to the database

                                    Rider newRider = new Rider(firstNameText + " "+lastNameText, userName, email);
                                    ContactInformation cInformation = new ContactInformation(phoneNumber, email);
                                    newRider.setContactDetails(cInformation);

                                    assert user != null;

                                    database.collection("Riders").document(user.getUid()).set(newRider)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(RiderSignUpActivity.this, "You are now signed up!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });

                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RiderSignUpActivity.this, "Signup Failed, please check the fields",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Back button functionality
        Button rider_backBT = findViewById(R.id.riderSignupBackBtn);
        rider_backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRiderModeActivity();
            }
        });

    }

    public void OpenRiderModeActivity() {
        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);
    }

}
