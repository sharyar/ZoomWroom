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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;

public class DriverSignUpActivity extends AppCompatActivity {
    // You declare this in every activity that needs access to the database
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    //Used for user authentication and signup
    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    //Declare variables required for this activity




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        //Get Auth instance from Firebase
        mAuth = FirebaseAuth.getInstance();

        EditText fullName = findViewById(R.id.driverSignupFullName);
        EditText emailAddress = findViewById(R.id.driverSignupEmailAddress);
        EditText passWordEditText = findViewById(R.id.driverSignupPassWord);
        EditText userName = findViewById(R.id.driverSignupUserName);
        EditText phoneNumber = findViewById(R.id.driverSignupPhoneNumber);
        Button signUpDriver = findViewById(R.id.driverSignupSignupBtn);


        //Uses the email address and password fields to create a new user within the database.
        signUpDriver.setOnClickListener((View v) -> {
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
                                Toast.makeText(DriverSignUpActivity.this, "You are now signed up!",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(DriverSignUpActivity.this, "Signup Failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
}
