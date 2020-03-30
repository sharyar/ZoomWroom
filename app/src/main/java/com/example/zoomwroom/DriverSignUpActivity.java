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

public class DriverSignUpActivity extends AppCompatActivity {
    // You declare this in every activity that needs access to the database
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    //Used for user authentication and sign up
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    // Declare variables required for activity
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

    Button signUpDriver;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        //Get Auth instance from Firebase
        mAuth = FirebaseAuth.getInstance();

        ProgressBar bar = findViewById(R.id.driver_signup_progress_bar);
        bar.setVisibility(View.INVISIBLE);
        
        // Set fields to views
        firstNameEditText = findViewById(R.id.driverSignupFName);
        lastNameEditText = findViewById(R.id.driverSignupLName);
        emailAddressEditText = findViewById(R.id.driverSignupEmailAddress);
        passWordEditText = findViewById(R.id.driverSignupPassWord);
        userNameEditText = findViewById(R.id.driverSignupUserName);
        phoneNumberEditText = findViewById(R.id.driverSignupPhoneNumber);
        
        // Set Button to view
        signUpDriver = findViewById(R.id.driverSignupSignupBtn);


        //Uses the email address and password fields to create a new user within the database.
        signUpDriver.setOnClickListener((View v) -> {
            bar.setVisibility(View.VISIBLE);
            email = emailAddressEditText.getText().toString().trim();
            passWord = passWordEditText.getText().toString();
            firstNameText = firstNameEditText.getText().toString().trim();
            lastNameText = lastNameEditText.getText().toString().trim();
            userName = userNameEditText.getText().toString().trim();
            phoneNumber = PhoneNumberUtils.normalizeNumber(phoneNumberEditText.getText().toString().trim());
            
            
            // add validation check here:
            

            if (areFieldsValid()) {
                mAuth.createUserWithEmailAndPassword(email, passWord)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:Success");
                                    //Get the newly created user. We can use this to actually build the contact info page.
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    //Add data from other fields if registration was successful:

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
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error, something went wrong with storing your info to the database.", e);
                                                }
                                            });

                                    // opens the driver home page
                                    openDriverHomeActivity();

                                } else {

                                    bar.setVisibility(View.INVISIBLE);

                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());

                                    if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                        Toast.makeText(DriverSignUpActivity.this,
                                                "Please use a stronger password", Toast.LENGTH_SHORT).show();

                                    } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(DriverSignUpActivity.this,
                                                "This email is already registered, please login or contact support.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(DriverSignUpActivity.this, "Sign up " +
                                                    "Failed, please ensure you are using a correct email address.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                    });

                // Disables loading bar
            } else {
                bar.setVisibility(View.INVISIBLE);
            }
        });

        Button BackBtn = findViewById(R.id.driverSignupBackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDriverModeActivity();
            }
        });

    }

    public void OpenDriverModeActivity(){
        Intent intent = new Intent(this,DriverModeActivity.class);
        startActivity(intent);
    }

    public void openDriverHomeActivity() {
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

        } else if (!UserDataValidation.isEmailValid(email)) {
            Toast.makeText(this, "Email address is invalid. Please input a valid email", Toast.LENGTH_SHORT).show();

            return false;
        } else if (!UserDataValidation.isAlpha(firstNameText) || !UserDataValidation.isAlpha(lastNameText)) {
            Toast.makeText(this, "First & Last Name can only be alphabetic.",
                    Toast.LENGTH_SHORT).show();

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
