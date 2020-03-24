package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.database.MyDataBase;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;

/**
 *
 * Source: https://developer.android.com/guide/topics/ui/layout/recyclerview#java
 */
public class AcceptedRequestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String driverID;
    private ArrayList<DriveRequest> currentRequests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_requests);

        driverID = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        currentRequests = MyDataBase.getDriverRequest(driverID);

        recyclerView = (RecyclerView) findViewById(R.id.accepted_requests_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RequestAdapter(this, currentRequests);
        recyclerView.setAdapter(mAdapter);



    }
}
