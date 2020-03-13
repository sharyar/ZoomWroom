package com.example.zoomwroom.database;

import android.util.Log;

import androidx.annotation.NonNull;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rider;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

public class MyDataBase {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyDataBase(){ }

    /**
     * This function adds the request to data base
     * @param driveRequest  a request object
     * @return requestID return the id of request
     */
    public static String addRequest(DriveRequest driveRequest) {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        String requestID = collectionReference.document().getId();
        driveRequest.setRequestID(requestID);
        driveRequest.toFirebaseMode();

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

    /**
     * This function get an array of requests
     * @param driverID  string of driverID
     * @return driveRequests an array of drive requests related to this driver
     */
    public static ArrayList<DriveRequest> getDriverRequest(String driverID){
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<>();

        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("driverID", driverID)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc : task.getResult()) {
            DriveRequest request = doc.toObject(DriveRequest.class);
            request.toLocalMode();
            driveRequests.add(request);
        }

        return driveRequests;
    }

    /**
     * This function adds the request to data base
     * @param driveRequest a request object
     * @return requestID return the id of request
     */
    public static void updateRequest(DriveRequest driveRequest) {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        String requestID = driveRequest.getRequestID();
        driveRequest.toFirebaseMode();
        collectionReference.document(requestID).set(driveRequest);
    }

    /**
     * This function is to update a rider
     * @param rider
     */
    public static void updateRider(final Rider rider){
        final CollectionReference collectionReference = db.collection("Riders");
        collectionReference.whereEqualTo("userID", rider.getUserID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    String id = doc.getId();
                    collectionReference.document(id).set(rider);
                }
            }
        });
    }

    /**
     * similar to updateRider
     * @param driver
     */
    public static void updateDriver(final Driver driver){
        final CollectionReference collectionReference = db.collection("Drivers");
        collectionReference.whereEqualTo("userID", driver.getUserID()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    String id = doc.getId();
                    collectionReference.document(id).set(driver);
                }
            }
        });
    }

    /**
     * similar to getDriverRequest
     * @param riderID
     */
    public static ArrayList<DriveRequest> getRiderRequest(String riderID){
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<>();

        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("riderID", riderID)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc : task.getResult()) {
            DriveRequest request = doc.toObject(DriveRequest.class);
            driveRequests.add(request);
            request.toLocalMode();
        }
        return driveRequests;
    }

    /**
     * Returns open request that have their status as PENDING
     *
     * @return      Arraylist of drive requests that have status pending and have not been
     *              picked by a driver
     */
    public static ArrayList<DriveRequest> getOpenRequests() {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<>();

        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("status", 0)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc: Objects.requireNonNull(task.getResult())) {
            DriveRequest request = doc.toObject(DriveRequest.class);
            driveRequests.add(request);
            request.toLocalMode();

        }
        return driveRequests;
    }

    /**
     * Returns a driveRequest by using its driveID
     *
     * @param driveID
     * @return
     */
    public static DriveRequest getDriveRequestByID(String driveID) {
        DocumentReference docRef = db.collection("DriverRequest").document(driveID);

        Task<DocumentSnapshot> task = docRef
                .get();
        while (!task.isSuccessful()) {}
        DriveRequest request = task.getResult().toObject(DriveRequest.class);
        return request;

    }

    /**
     * get target object rider
     * @param userID
     */
    public static Rider getRider(String userID) {
        final CollectionReference collectionReference = db.collection("Riders");
        ArrayList<Rider> riders = new ArrayList<Rider>();
        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("userID", userID)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc : task.getResult()) {
            Rider rider = doc.toObject(Rider.class);
            riders.add(rider);
        }
        if (riders.size() == 0){
            Log.d("Rider", "no user is found");
            return null;
        }
        if (riders.size() > 1){
            Log.d("Rider", "userID is not unique");
            return null;
        }
        return riders.get(0);
    }

    /**
     * add the rider to the database
     * @param rider
     */
    public static void addRider(Rider rider) {
        final CollectionReference collectionReference = db.collection("Riders");

        collectionReference
                .document()
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
    }

    /**
     * add the driver to database
     * @param driver
     */
    public static void addDriver(Driver driver) {
        final CollectionReference collectionReference = db.collection("Drivers");

        collectionReference
                .document()
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
    }

    /**
     * get the target object driver
     * @param userID
     */
    public static Driver getDriver(String userID) {
        final CollectionReference collectionReference = db.collection("Drivers");
        ArrayList<Driver> drivers = new ArrayList<Driver>();
        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("userID", userID)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc : task.getResult()) {
            Driver driver = doc.toObject(Driver.class);
            drivers.add(driver);
        }
        if (drivers.size() == 0){
            Log.d("Driver", "no user is found");
            return null;
        }
        if (drivers.size() > 1){
            Log.d("Driver", "userID is not unique");
            return null;
        }
        return drivers.get(0);
    }

}
