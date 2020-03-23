//sources: https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account

package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.zoomwroom.database.MyDataBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DriverSignUpActivity extends AppCompatActivity {
    // You declare this in every activity that needs access to the database
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    //Used for user authentication and signup
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        //Get Auth instance from Firebase
        mAuth = FirebaseAuth.getInstance();

        ProgressBar bar = findViewById(R.id.driver_signup_progress_bar);
        bar.setVisibility(View.INVISIBLE);

        EditText firstNameEditText = findViewById(R.id.driverSignupFName);
        EditText lastNameEditText = findViewById(R.id.driverSignupLName);
        EditText emailAddressEditText = findViewById(R.id.driverSignupEmailAddress);
        EditText passWordEditText = findViewById(R.id.driverSignupPassWord);
        EditText userNameEditText = findViewById(R.id.driverSignupUserName);
        EditText phoneNumberEditText = findViewById(R.id.driverSignupPhoneNumber);
        Button signUpDriver = findViewById(R.id.driverSignupSignupBtn);


        //Uses the email address and password fields to create a new user within the database.
        signUpDriver.setOnClickListener((View v) -> {
            bar.setVisibility(View.VISIBLE);
            String email = emailAddressEditText.getText().toString().trim();
            String passWord = passWordEditText.getText().toString();
            String firstNameText = firstNameEditText.getText().toString().trim();
            String lastNameText = lastNameEditText.getText().toString().trim();
            String userName = userNameEditText.getText().toString().trim();
            String phoneNumber = phoneNumberEditText.getText().toString().trim();

            if (email.isEmpty() || passWord.isEmpty() || firstNameText.isEmpty() ||
                    lastNameText.isEmpty() || userName.isEmpty() || phoneNumber.isEmpty()) {
                Toast.makeText(DriverSignUpActivity.this, "Please ensure you have " +
                        "filled out all the fields.", Toast.LENGTH_SHORT).show();


            } else if (MyDataBase.isUserNameUnique(userName)) {
                Toast.makeText(this, "This username is already taken, please use a new one", Toast.LENGTH_SHORT).show();
            } else {

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


                                    // Open the driver's profile after they signup - temporary
                                    openDriverHomeActivity();


                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(DriverSignUpActivity.this, "Sign up Failed, please check your fields",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        Button BackBtn = findViewById(R.id.driverSignupBackBtn);
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDriverModeActivity();
            }
        });

//        // Creating another hashmap value
//        HashMap<String, String> data = new HashMap<>();
//        data.put("2", "Amy");
//
//        // NOTE: Obviously collection path will be drivers but I wanted to test to see if I could add to riders
//        // in a different activity!
//        final CollectionReference collectionReference = database.collection("Riders");
//        collectionReference
//                .document("2") // name
//                .set(data);
//

    }
    public void OpenDriverModeActivity(){
        Intent intent = new Intent(this,DriverModeActivity.class);
        startActivity(intent);
    }

    public void openDriverHomeActivity() {
        Intent intent = new Intent(this, DriverHomeActivity.class);
        startActivity(intent);
    }
}
