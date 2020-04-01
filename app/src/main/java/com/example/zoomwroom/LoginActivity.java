package com.example.zoomwroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public abstract class LoginActivity extends AppCompatActivity {
    // Variables required for class
    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected FirebaseAuth mAuth;
    protected Button modeBtn;
    protected Button signUpBtn;
    protected Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        getViewContent();

        //LOGIN BUTTON
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        //SIGNUP BUTTON
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenSignUpActivity();
            }
        });

        //Change mode between rider mode and driver mode
        modeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeMode();
            }
        });
    }

    protected abstract void getViewContent();


    public void logIn() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please ensure you have " +
                    "filled out all the fields.", Toast.LENGTH_SHORT).show();
        }
        else {
            validate(email, password);
        }
    }

    public void validate(String driverEmail, String driverPassword) {
        mAuth.signInWithEmailAndPassword(driverEmail, driverPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this,
                                    "Login Successful.", Toast.LENGTH_SHORT).show();
                            OpenHomeActivity();

                        } else {
                            //Log.w(TAG, "signInWithEmail: failure", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Login failed. Please check your info and try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Opens into the main page if login is successful
     * */
    public abstract void OpenHomeActivity();

    public abstract void OpenSignUpActivity();

    public abstract void ChangeMode();
}
