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
    EditText riderEmail;
    EditText riderPassword;
    private FirebaseAuth mAuth;
    private static final String TAG = "RiderLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_mode);

        mAuth = FirebaseAuth.getInstance();

        riderEmail = findViewById(R.id.rider_email_login);
        riderPassword = findViewById(R.id.rider_password_login);

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
        mAuth.signInWithEmailAndPassword(riderEmail.getText().toString(), riderPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RiderModeActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            // go to rider mod comes here.
                        } else {
                            Log.w(TAG, "signInWithEmail: failure", task.getException());
                            Toast.makeText(RiderModeActivity.this, "Login failed. Please check your info and try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
