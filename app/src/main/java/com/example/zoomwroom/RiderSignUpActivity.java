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

import com.example.zoomwroom.Entities.Rider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

        EditText fullName = findViewById(R.id.riderSignupFName);
        EditText emailAddress = findViewById(R.id.riderSignupEmailAddress);
        EditText passWordEditText = findViewById(R.id.riderSignupPassWord);
        EditText userName = findViewById(R.id.riderSignupUserName);
        EditText phoneNumber = findViewById(R.id.riderSignupPhoneNumber);
        Button signUpRider = findViewById(R.id.riderSignupSignupBtn);

        signUpRider.setOnClickListener((View v) -> {
            String email = emailAddress.getText().toString().trim();
            String passWord = passWordEditText.getText().toString().trim();
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

                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RiderSignUpActivity.this, "Signup Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        // Back button functionality
        Button rider_backBT = findViewById(R.id.riderSignupBackBtn);
        rider_backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRiderModeActivity();
            }
        });




//        // Creating a rider test object
//        String name = "Bobby Joe";
//        String username = "booo";
//        String userID = "1";
//        test = new Rider(name,username,userID);
//        /////
//
//        // Remember that hashmaps take in a key value pair! I'm guessing it will be (userID, Rider/Driver)
//        // Note that for now I have <String, String> because I'm getting a separate error with Rider
//        HashMap<String, String> data = new HashMap<>();
//        //start putting in values into the data
//        data.put(userID, name);
//        //this line gets access to the database, with the page called riders! eventually there will be drivers etc.
//        final CollectionReference collectionReference = database.collection("Riders");
//        collectionReference
//                .document(userID) // name
//                .set(data);
    }

    public void OpenRiderModeActivity() {
        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);
    }

}
