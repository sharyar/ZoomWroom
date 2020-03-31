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
import com.example.zoomwroom.Entities.Driver;
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

public class DriverSignUpActivity extends SignupActivity {


    protected void getViewContent() {
        setContentView(R.layout.activity_driver_sign_up);
        bar = findViewById(R.id.driver_signup_progress_bar);

        // Set fields to views
        firstNameEditText = findViewById(R.id.driverSignupFName);
        lastNameEditText = findViewById(R.id.driverSignupLName);
        emailAddressEditText = findViewById(R.id.driverSignupEmailAddress);
        passWordEditText = findViewById(R.id.driverSignupPassWord);
        userNameEditText = findViewById(R.id.driverSignupUserName);
        phoneNumberEditText = findViewById(R.id.driverSignupPhoneNumber);

        // Set Button to view
        signUpUser = findViewById(R.id.driverSignupSignupBtn);
        BackBtn = findViewById(R.id.driverSignupBackBtn);
    }

    public void CreateUser(FirebaseUser user) {
        //First create a new driver and contact info instance. Then add them to the database.
        Driver newDriver = new Driver(firstNameText + " " + lastNameText, userName, email);
        ContactInformation cInformation = new ContactInformation(phoneNumber, email);
        newDriver.setContactDetails(cInformation);

        assert user != null;

        database.collection("Drivers").document(user.getUid()).set(newDriver)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DriverSignUpActivity.this, "You are now signed up!",
                                Toast.LENGTH_SHORT).show();
                        // opens the home page
                        OpenHomeActivity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error, something went wrong with storing your info to the database.", e);
                    }
                });
    }

    public void ReturnToLogin(){
        Intent intent = new Intent(this, DriverLoginActivity.class);
        startActivity(intent);
    }

    public void OpenHomeActivity() {
        Intent intent = new Intent(this, DriverHomeActivity.class);
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
            Toast.makeText(DriverSignUpActivity.this, "Please ensure you have " +
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
