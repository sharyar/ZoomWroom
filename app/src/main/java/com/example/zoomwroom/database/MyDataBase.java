package com.example.zoomwroom.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyDataBase {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyDataBase(){ }

    public static String addRequest(DriveRequest driveRequest) {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        String requestID = collectionReference.document().getId();

        collectionReference
                .document(requestID)
                .set(driveRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Driver request", "data storing is successful");
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Driver request", "data addition failed");
                    }
                });
        return requestID;
    }

    public static ArrayList<DriveRequest> findDriverRequest(Driver driver){
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<DriveRequest>();

        collectionReference
                .whereEqualTo("driver", driver)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    DriveRequest request = doc.toObject(DriveRequest.class);
                    driveRequests.add(request);
                }
            }
        });
        return driveRequests;
    }

    public static void setRequestCondition(String requestID, String condition) {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        collectionReference.document(requestID).update("status", condition);
    }

    public static ArrayList<DriveRequest> getActiveRequest(Rider rider){
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> activeRequest = new ArrayList<>();
        collectionReference
                .whereEqualTo("rider", rider)
                .whereEqualTo("status", "ACCEPTED")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    DriveRequest request = doc.toObject(DriveRequest.class);
                    activeRequest.add(request);
                }
            }
        });
        return activeRequest;
    }

    public static ArrayList<DriveRequest> findRiderRequest(Rider rider){
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<DriveRequest>();

        collectionReference
                .whereEqualTo("rider", rider)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    DriveRequest request = doc.toObject(DriveRequest.class);
                    driveRequests.add(request);
                }
            }
        });
        return driveRequests;
    }

    public static String addRider(Rider rider) {
        final CollectionReference collectionReference = db.collection("Riders");
        String riderId = collectionReference.document().getId();

        collectionReference
                .document(riderId)
                .set(rider)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Rider", "data storing is successful");
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Rider", "data addition failed");
                    }
                });
        return riderId;
    }

    public static String addDriver(Driver driver) {
        final CollectionReference collectionReference = db.collection("Riders");
        String driverId = collectionReference.document().getId();

        collectionReference
                .document(driverId)
                .set(driver)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Driver", "data storing is successful");
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Driver", "data addition failed");
                    }
                });
        return driverId;
    }

}
