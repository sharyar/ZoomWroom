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

public class RiderModeActivity extends AppCompatActivity {
    // Variables required for class
    Button driverModBtn;
    Button riderSignUpBtn;
    Button loginBtn;
    EditText riderEmailEditText;
    EditText riderPasswordEditText;
    private FirebaseAuth mAuth;
    private static final String TAG = "RiderLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_mode);

        mAuth = FirebaseAuth.getInstance();

        riderEmailEditText = findViewById(R.id.rider_email_login);
        riderPasswordEditText = findViewById(R.id.rider_password_login);

        loginBtn = findViewById(R.id.rider_LoginBT);


        //Login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        // change to driver Mode
        driverModBtn = findViewById(R.id.rider_chagentodrivermodeBT);
        driverModBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityDriverMode();
            }
        });
        //rider mode sign up button
        riderSignUpBtn = findViewById(R.id.rider_SignupBT);
        riderSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRiderSignUpActivity();
            }
        });
    }

    public void OpenActivityDriverMode() {
        Intent intent = new Intent(this,DriverModeActivity.class);
        startActivity(intent);
    }

    public void OpenRiderSignUpActivity() {
        Intent intent = new Intent(this,RiderSignUpActivity.class);
        startActivity(intent);


    }

    public void logIn() {
        String riderEmail = riderEmailEditText.getText().toString();
        String riderPassword = riderPasswordEditText.getText().toString();
        if (riderEmail.isEmpty() || riderPassword.isEmpty()) {
            Toast.makeText(RiderModeActivity.this, "Please ensure you have " +
                    "filled out all the fields.", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.signInWithEmailAndPassword(riderEmail, riderPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(RiderModeActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                                OpenRiderHomeActivity();

                            } else {
                                Log.w(TAG, "signInWithEmail: failure", task.getException());
                                Toast.makeText(RiderModeActivity.this, "Login failed. Please check your info and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    /**
     * Opens into the Rider's main page if login is successful
     * */
    public void OpenRiderHomeActivity() {
        Intent intent = new Intent(this,RiderHomeActivity.class);
        startActivity(intent);
    }

}
