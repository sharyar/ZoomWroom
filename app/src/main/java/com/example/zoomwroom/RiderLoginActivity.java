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

public class RiderLoginActivity extends LoginActivity {


    protected void getViewContent() {
        setContentView(R.layout.activity_rider_mode);
        loginBtn = findViewById(R.id.rider_LoginBT);
        signUpBtn = findViewById(R.id.rider_SignupBT);
        modeBtn = findViewById(R.id.rider_chagentodrivermodeBT);
        emailEditText = findViewById(R.id.rider_email_login);
        passwordEditText = findViewById(R.id.rider_password_login);
    }

    /**
     * switch mode between driver and rider
     */
    public void ChangeMode() {
        Intent intent = new Intent(this, DriverLoginActivity.class);
        startActivity(intent);
    }

    /**
     * change activity to rider signup page for driver to sign up
     */
    public void OpenSignUpActivity() {
        Intent intent = new Intent(this,RiderSignUpActivity.class);
        startActivity(intent);


    }

    /**
     * Opens into the Rider's main page if login is successful
     * */
    public void OpenHomeActivity() {
        Intent intent = new Intent(this,RiderHomeActivity.class);
        startActivity(intent);
    }

}
