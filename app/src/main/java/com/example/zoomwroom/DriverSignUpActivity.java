package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DriverSignUpActivity extends AppCompatActivity {
    // You declare this in every activity that needs access to the database
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sign_up);

        //Click Back button return to the main activity

        // Creating another hashmap value
        HashMap<String, String> data = new HashMap<>();
        data.put("2", "Amy");

        // NOTE: Obviously collection path will be drivers but I wanted to test to see if I could add to riders
        // in a different activity!
        final CollectionReference collectionReference = database.collection("Riders");
        collectionReference
                .document("2") // name
                .set(data);

        Button BackBT = findViewById(R.id.driverSignup_BackBtn);
        BackBT.setOnClickListener(new View.OnClickListener() {
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
}
