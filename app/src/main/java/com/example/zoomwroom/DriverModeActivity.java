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

public class DriverModeActivity extends AppCompatActivity {
    // Variables required for activity
    Button riderModeBtn;
    Button driverSignUpBtn;
    Button loginBtn;
    EditText driverEmail;
    EditText driverPassword;
    private FirebaseAuth mAuth;
    private static final String TAG = "DriverLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_mode);

        mAuth = FirebaseAuth.getInstance();

        driverEmail = findViewById(R.id.driver_email_login);
        driverPassword = findViewById(R.id.driver_password);

        loginBtn = findViewById(R.id.driver_LoginBT);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
                openDriverHome();
            }
        });

        //SIGNUP BUTTON
        driverSignUpBtn = findViewById(R.id.driver_SignupBT);
        driverSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenDriverSignUpActivity();
            }
        });

        //Change mode between rider mode and driver mode
        riderModeBtn = findViewById(R.id.driver_chagentoRidermodeBT);
        riderModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityRiderMode();
            }
        });

    }

    public void OpenDriverSignUpActivity(){
        Intent intent = new Intent(this,DriverSignUpActivity.class);
        startActivity(intent);


    }
    public void OpenActivityRiderMode() {
        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);

    }

    public void logIn() {
        mAuth.signInWithEmailAndPassword(driverEmail.getText().toString(), driverPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(DriverModeActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.w(TAG, "signInWithEmail: failure", task.getException());
                            Toast.makeText(DriverModeActivity.this, "Login failed. Please check your info and try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void openDriverHome() {
        Intent intent = new Intent(this, DriverHomeActivity.class);
        startActivity(intent);
    }
}
