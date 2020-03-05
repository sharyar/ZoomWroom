package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zoomwroom.Entities.Image;
import com.example.zoomwroom.Entities.Rider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RiderSignUpActivity extends AppCompatActivity {
    Rider test;

    // Write this to get access to the database! NEED!
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_sign_up);

        ///if user click back button it will return back to their mode activity

        Button rider_backBT = findViewById(R.id.rider_BackBT);
        rider_backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenRiderModeActivity();
            }
        });

        // Creating a rider test object
        String name = "Bobby Joe";
        String username = "booo";
        String userID = "1";
        Image image = new Image();
        test = new Rider(name,username,userID,image);
        /////

        // Remember that hashmaps take in a key value pair! I'm guessing it will be (userID, Rider/Driver)
        // Note that for now I have <String, String> because I'm getting a separate error with Rider
        HashMap<String, String> data = new HashMap<>();
        //start putting in values into the data
        data.put(userID, name);
        //this line gets access to the database, with the page called riders! eventually there will be drivers etc.
        final CollectionReference collectionReference = database.collection("Riders");
        collectionReference
                .document(userID) // name
                .set(data);

    }

    public void OpenRiderModeActivity() {

        Intent intent = new Intent(this,RiderModeActivity.class);
        startActivity(intent);
    }



}
