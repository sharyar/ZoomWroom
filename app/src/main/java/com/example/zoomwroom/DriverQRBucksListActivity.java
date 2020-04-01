package com.example.zoomwroom;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zoomwroom.Entities.QRBucks;
import com.example.zoomwroom.database.MyDataBase;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DriverQRBucksListActivity extends AppCompatActivity implements QRBucksAdapter.OnQRBucksClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String driverID;
    private ArrayList<QRBucks> qrBucks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrbucks_list_driver);

        driverID = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        qrBucks = MyDataBase.getQRBucksByDriverID(driverID);

        recyclerView = findViewById(R.id.qrBucks_recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new QRBucksAdapter(this, qrBucks, this::onQrBucksClick);

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onQrBucksClick(int position) {
        qrBucks.get(position).collectMoney();

    }


}
