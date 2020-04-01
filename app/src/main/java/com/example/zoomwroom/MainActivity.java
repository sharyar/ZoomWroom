package com.example.zoomwroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Driver Button
        Button driverBT = findViewById(R.id.DriverBT);
        driverBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityDriverMode();
            }
        });
        //Rider Button
        Button riderBT = findViewById(R.id.RiderBT);
        riderBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivityRiderMode();
            }
        });
        //TESTBT
//        Button testbt = findViewById(R.id.TESTBT);
//        testbt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(v.getContext(),DriverCompleteActivity.class);
////                startActivity(intent);
//                DriverCompleteRequestFragment completeRequestForDiverFragment = new DriverCompleteRequestFragment();
//                completeRequestForDiverFragment.show(getSupportFragmentManager(),"hello");
//            }
//        });



    }


    /**
     * switch mode between driver and rider
     */

    public void OpenActivityRiderMode(){
        Intent intent = new Intent(this, RiderLoginActivity.class);
        startActivity(intent);
    }

    /**
     * switch mode between driver and rider
     */

    public void OpenActivityDriverMode() {

        Intent intent = new Intent(this, DriverLoginActivity.class);
        startActivity(intent);

    }



    ///////////////////////////////////////////////

}
