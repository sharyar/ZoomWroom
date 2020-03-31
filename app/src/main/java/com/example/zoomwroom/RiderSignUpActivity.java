//sources: https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account

package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.Entities.UserDataValidation;
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RiderSignUpActivity extends AppCompatActivity {
    Rider test;

    // Write this to get access to the database! NEED!
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    //Used for user authentication and sign up
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    // Declare variables required for class

    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText emailAddressEditText;
    EditText passWordEditText;
    EditText userNameEditText;
    EditText phoneNumberEditText;


    String email;
    String passWord;
    String firstNameText;
    String lastNameText;
    String userName;
    String phoneNumber;

    Button signUpRider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_sign_up);

        //Get Auth instance from Firebase
        mAuth = FirebaseAuth.getInstance();

        ProgressBar bar = findViewById(R.id.rider_signup_progress_bar);
        bar.setVisibility(View.INVISIBLE);

        firstNameEditText = findViewById(R.id.riderSignupFName);
        lastNameEditText = findViewById(R.id.riderSignupLName);
        emailAddressEditText = findViewById(R.id.riderSignupEmailAddress);
        passWordEditText = findViewById(R.id.riderSignupPassWord);
        userNameEditText = findViewById(R.id.riderSignupUserName);
        phoneNumberEditText = findViewById(R.id.riderSignupPhoneNumber);
        signUpRider = findViewById(R.id.riderSignupSignupBtn);


        signUpRider.setOnClickListener((View v) -> {
            bar.setVisibility(View.VISIBLE);
            email = emailAddressEditText.getText().toString().trim();
            passWord = passWordEditText.getText().toString();
            firstNameText = firstNameEditText.getText().toString().trim();
            lastNameText = lastNameEditText.getText().toString().trim();
            userName = userNameEditText.getText().toString().trim();
            phoneNumber = PhoneNumberUtils.normalizeNumber(phoneNumberEditText.getText().toString());


            // this if condition checks if all fields are valid and only allows the registration if they are.
            if (areFieldsValid()) {
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
                                                    OpenRiderHomeActivity();

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });

                                } else {

                                    bar.setVisibility(View.INVISIBLE);

                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                    if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                        Toast.makeText(RiderSignUpActivity.this,
                                                "Please use a stronger password", Toast.LENGTH_SHORT).show();

                                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(RiderSignUpActivity.this,
                                                "This email is already registered, please login or contact support.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(RiderSignUpActivity.this, "Sign up " +
                                                    "Failed, please ensure you are using a correct email address.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                bar.setVisibility(View.INVISIBLE);
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
    /**
     * switch mode between driver and rider
     */

    public void OpenRiderModeActivity() {
        Intent intent = new Intent(this, RiderLoginActivity.class);
        startActivity(intent);
    }

    /**
     * Opens into the Rider's main page if login is successful
     * */
    public void OpenRiderHomeActivity() {
        Intent intent = new Intent(this,RiderHomeActivity.class);
        startActivity(intent);
    }

    /**
     * Checks if the user's input is valid based on preset conditions.
     *
     * @return      boolean value indicating if the fields input by the user are valid or not
     */
    boolean areFieldsValid() {
        if (email.isEmpty() || passWord.isEmpty() || firstNameText.isEmpty() ||
                lastNameText.isEmpty() || userName.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(RiderSignUpActivity.this, "Please ensure you have " +
                    "filled out all the fields.", Toast.LENGTH_SHORT).show();

            return false;


        } else if (!UserDataValidation.isAlpha(firstNameText) || !UserDataValidation.isAlpha(lastNameText)) {
            Toast.makeText(this, "First & Last Name can only be alphabetic.",
                    Toast.LENGTH_SHORT).show();

            return false;

        } else if (!UserDataValidation.isEmailValid(email)) {
            Toast.makeText(this, "Email address is invalid. Please input a valid email", Toast.LENGTH_SHORT).show();

            return false;

        } else if (!UserDataValidation.isAlphaNumeric(userName)) {
            Toast.makeText(this, "Username must be a combination of numbers and letters."
                    , Toast.LENGTH_SHORT).show();

            return false;
        } else if (!MyDataBase.isUserNameUnique(userName)) {
            Toast.makeText(this, "This username is already taken, please use a different one",
                    Toast.LENGTH_SHORT).show();

            return false;
        } else if (!UserDataValidation.isPhoneNumberValid(phoneNumber)) {
            Toast.makeText(this, "Please enter a valid phone number." , Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}
