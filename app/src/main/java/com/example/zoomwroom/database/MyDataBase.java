package com.example.zoomwroom.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.QRBucks;
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
    private static final MyDataBase instance = new MyDataBase();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MyDataBase(){ }

    public static MyDataBase getInstance() {
        return instance;
    }

    /**
     * This function adds the request to data base
     * @param driveRequest  a request object
     * @return requestID return the id of request
     */
    public String addRequest(DriveRequest driveRequest) {
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
    public ArrayList<DriveRequest> getDriverRequest(String driverID){
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
     * Returns an arraylist of DriveRequests for a specific driverID and status of the request
     * @param driverID      Driver's unique id (their email address)
     * @param status        status of the DriveRequests to be provided
     * @return              Arraylist of DriveRequests with the specific status for that Driver
     */
    public ArrayList<DriveRequest> getDriveRequestsByDriverIDAndStatus(String driverID, int status) {
        ArrayList<DriveRequest> unfilteredList = getDriverRequest(driverID);
        ArrayList<DriveRequest> filteredList = new ArrayList<>();

        for (DriveRequest driveRequest: unfilteredList) {
            if (driveRequest.getStatus() == status) {
                filteredList.add(driveRequest);
            }
        }
        return filteredList;
    }

    /**
     * This function adds the request to data base
     * @param driveRequest a request object
     */
    public void updateRequest(DriveRequest driveRequest) {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        String requestID = driveRequest.getRequestID();
        driveRequest.toFirebaseMode();
        collectionReference.document(requestID).set(driveRequest);
    }

    /**
     * This function returns the current active request of a user
     * @param userID user ID
     * @param dataType user type(riderID or driverID)
     * @return return the current request
     */
    public DriveRequest getCurrentRequest(String userID, String dataType){
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<>();

        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo(dataType, userID)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc: Objects.requireNonNull(task.getResult())) {
            DriveRequest request = doc.toObject(DriveRequest.class);
            request.toLocalMode();
            if (request.getStatus() == 1 || request.getStatus() == 2 || request.getStatus() == 3 || request.getStatus() == 4) {
                driveRequests.add(request);
            }
        }
        if (driveRequests.size() == 0){
            return null;
        }
        if (driveRequests.size() > 1){
            Log.d("Note", "Multiple requests!!!!!!!!!!!!!");
        }
        return driveRequests.get(0);
    }

    /**
     * This function is to update a rider
     * @param rider
     */
    public void updateRider(final Rider rider){
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
    public void updateDriver(final Driver driver){
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
    public ArrayList<DriveRequest> getRiderRequest(String riderID){
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
    public ArrayList<DriveRequest> getOpenRequests() {
        final CollectionReference collectionReference = db.collection("DriverRequest");
        ArrayList<DriveRequest> driveRequests = new ArrayList<>();

        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("status", 0)
                .get();
        while (!task.isSuccessful()) {}
        for (QueryDocumentSnapshot doc: Objects.requireNonNull(task.getResult())) {
            DriveRequest request = doc.toObject(DriveRequest.class);
            request.toLocalMode();
            driveRequests.add(request);
        }
        return driveRequests;
    }

    /**
     * Returns a driveRequest by using its driveID
     *
     * @param driveID
     * @return
     */
    public DriveRequest getDriveRequestByID(String driveID) {
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
    public Rider getRider(String userID) {
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
    public void addRider(Rider rider) {
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
    public void addDriver(Driver driver) {
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
    public Driver getDriver(String userID) {
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

    /**
     * checks if the username is unique. If it is unique and no other user with that username exists
     * returns true, otherwise returns false.
     *
     * @param userName username to check for uniqueness
     * @return boolean value indicating if the username is unique or not
     */
    public Boolean isUserNameUnique(String userName) {
        final CollectionReference driverCollectionReference = db.collection("Drivers");
        final CollectionReference riderCollectionReference = db.collection("Riders");

        ArrayList<Driver> drivers = new ArrayList<>();
        ArrayList<Rider> riders = new ArrayList<>();

        Task<QuerySnapshot> task1 = driverCollectionReference
                .whereEqualTo("userName", userName)
                .get();

        Task<QuerySnapshot> task2 = riderCollectionReference
                .whereEqualTo("userName", userName)
                .get();

        while (!task1.isSuccessful()) {
        }
        while (!task2.isSuccessful()) {
        }

        for (QueryDocumentSnapshot doc : task1.getResult()) {
            Driver user = doc.toObject(Driver.class);
            drivers.add(user);
        }

        for (QueryDocumentSnapshot doc : task2.getResult()) {
            Rider user = doc.toObject(Rider.class);
            riders.add(user);
        }
        return (riders.isEmpty() && drivers.isEmpty());
    }

    /**
     * Add QRBucks instance to firestore database. This should be called when scanning the QRCode.
     *
     * @param buck    instance of QRBucks to add to the database.
     */
    public void addQRBucks(QRBucks buck) {
        final CollectionReference collectionReference = db.collection("QRBucks");

        if(getQRBuckByDriveID(buck.getDriveRequestID()) != null) {
            collectionReference
                    .document()
                    .set(buck)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("QR Bucks", "QRBucks saved to database successfully");
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("QR Bucks", "data addition failed");
                        }
                    });
        }
    }

    /**
     * Returns a single QRBuck by the driveID it is associated to.
     *
     * @param driveRequestID    driveRequestID to look up the associated QRBuck instance for
     * @return                  QRBuck instance associated to the DriveRequest
     */
    public QRBucks getQRBuckByDriveID(String driveRequestID) {
        final CollectionReference collectionReference = db.collection("QRBucks");
        ArrayList<QRBucks> qrBucks = new ArrayList<>();
        Task<QuerySnapshot> task = collectionReference
                .whereEqualTo("driveRequestID", driveRequestID)
                .get();
        while (!task.isSuccessful()) {
        }
        for (QueryDocumentSnapshot doc : task.getResult()) {
            QRBucks qrBuck = doc.toObject(QRBucks.class);
            qrBucks.add(qrBuck);
        }
        if (qrBucks.size() == 0) {
            Log.d("QRBucks", "no bucks found");
            return null;
        }/*
        if (qrBucks.size() > 1) {
            Log.d("QRBucks", "DriveRequestID is not unique");
            return null;
        }*/
        return qrBucks.get(0);
    }

    /**
     * May need to change status to Concluded once that is implemented.
     * @param driverID
     * @return
     */
    public ArrayList<QRBucks> getQRBucksByDriverID(String driverID) {
        ArrayList<QRBucks> qrBucks = new ArrayList<>();
        QRBucks buck;
        ArrayList<DriveRequest> driveRequests =
                getDriveRequestsByDriverIDAndStatus(driverID, DriveRequest.Status.FINALIZED);

        for (DriveRequest d: driveRequests) {
            buck = getQRBuckByDriveID(d.getRequestID());
            if (buck != null ) {
                qrBucks.add(buck);
            }
        }
        return qrBucks;
    }
}