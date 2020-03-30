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

public class DriverLoginActivity extends LoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_driver_mode);
        emailEditText = findViewById(R.id.driver_email_login);
        passwordEditText = findViewById(R.id.driver_password);

        loginBtn = findViewById(R.id.driver_LoginBT);
        signUpBtn = findViewById(R.id.driver_SignupBT);
        modeBtn = findViewById(R.id.driver_chagentoRidermodeBT);
        super.onCreate(savedInstanceState);
    }
    /**
     * change to driver signup page for driver to signup
     */
    public void OpenSignUpActivity(){
        Intent intent = new Intent(this,DriverSignUpActivity.class);
        startActivity(intent);
    }

    /**
     * switch mode between driver and rider
     */
    public void ChangeMode() {
        Intent intent = new Intent(this, RiderLoginActivity.class);
        startActivity(intent);

    }

    public void OpenHomeActivity(){
        Intent intent = new Intent(this, DriverHomeActivity.class);
        startActivity(intent);
    }


}
