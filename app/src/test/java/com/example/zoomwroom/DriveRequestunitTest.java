package com.example.zoomwroom;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.User;
import com.google.android.gms.maps.model.LatLng;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DriveRequestunitTest {

    public DriveRequest Mockrequest() {
        return new DriveRequest("123456 ", new LatLng(53.5232, -113.5263), new LatLng(53.5232, -113.5263));
    }

    /**
     * test setrequestId
     */
    @Test
    void testsetrequestId(){
        DriveRequest mockrequest = Mockrequest();
        mockrequest.setRequestID("001");

        assertEquals("001",mockrequest.getRequestID());
    }

    /**
     * test getrequestId
     */
    @Test
    void testgetrequestId(){
        DriveRequest mockrequest = Mockrequest();
        mockrequest.setRequestID("100");

        assertEquals("100",mockrequest.getRequestID());
    }

    /**
     * test getriderId
     */
    @Test
    void testgetriderId(){
        DriveRequest mockrequest = Mockrequest();
        mockrequest.setRiderID("001");

        assertEquals("001",mockrequest.getRiderID());
    }

    /**
     * test setriderId
     */
    @Test
    void testsetriderId(){
        DriveRequest mockrequest = Mockrequest();
        mockrequest.setRiderID("abcd1234");

        assertEquals("abcd1234",mockrequest.getRiderID());

    }

    /**
     * test getdriverId
     */
    @Test
    void testgetdriverId(){
        DriveRequest mockrequest = Mockrequest();
        mockrequest.setDriverID("001");

        assertEquals("001",mockrequest.getDriverID());

    }

    /**
     * test setDriverId
     */
    @Test
    void testsetDriverId(){
        DriveRequest mockrequest = Mockrequest();
        mockrequest.setDriverID("abcd1234");

        assertEquals("abcd1234",mockrequest.getDriverID());
    }

    /**
     * test setpickuplocation
     */
    @Test
    void testsetpickuplocation(){
        DriveRequest mockrequest = Mockrequest();
        assertEquals(new LatLng(53.5232, -113.5263) ,mockrequest.getPickupLocation());

        mockrequest.setPickupLocation(new LatLng(53.5231, -113.5261));
        assertEquals(new LatLng(53.5231, -113.5261) ,mockrequest.getPickupLocation());
    }

    /**
     * test getpickuplocation
     */
    @Test
    void testgetpickuplocation(){
        DriveRequest mockrequest = Mockrequest();
        assertEquals(new LatLng(53.5232, -113.5263) ,mockrequest.getPickupLocation());

        mockrequest.setPickupLocation(new LatLng(53.5231, -113.5261));
        assertEquals(new LatLng(53.5231, -113.5261) ,mockrequest.getPickupLocation());
    }

    /**
     * test setdeslocation
     */
    @Test
    void testsetdeslocation(){
        DriveRequest mockrequest = Mockrequest();
        assertEquals(new LatLng(53.5232, -113.5263) ,mockrequest.getDestination());

        mockrequest.setDestination(new LatLng(53.5231, -113.5261));
        assertEquals(new LatLng(53.5231, -113.5261) ,mockrequest.getDestination());
    }

    /**
     * test getdeslocation
     */
    @Test
    void testgetdeslocation(){
        DriveRequest mockrequest = Mockrequest();
        assertEquals(new LatLng(53.5232, -113.5263) ,mockrequest.getDestination());

        mockrequest.setDestination(new LatLng(53.5231, -113.5261));
        assertEquals(new LatLng(53.5231, -113.5261) ,mockrequest.getDestination());

    }


    /**
     * test setsuggestfare
     */
    @Test
    void testsetsuggestfare(){
        DriveRequest mockrequest = Mockrequest();

        mockrequest.setSuggestedFare(8);
        assertEquals(8 ,mockrequest.getSuggestedFare());

        mockrequest.setSuggestedFare(1000000000);
        assertEquals(1000000000 ,mockrequest.getSuggestedFare());
    }

    /**
     * test getsuggestfare
     */
    @Test
    void testgetsuggestfare(){
        DriveRequest mockrequest = Mockrequest();

        mockrequest.setSuggestedFare(8);
        assertEquals(8 ,mockrequest.getSuggestedFare());

        mockrequest.setSuggestedFare(1000000000);
        assertEquals(1000000000 ,mockrequest.getSuggestedFare());
    }

    /**
     * test setofferedfare
     */
    @Test
    void testsetofferedfare(){
        DriveRequest mockrequest = Mockrequest();

        mockrequest.setOfferedFare(8);
        assertEquals(8 ,mockrequest.getOfferedFare());

        mockrequest.setOfferedFare(1000000000);
        assertEquals(1000000000 ,mockrequest.getOfferedFare());
    }

    /**
     * test getofferedfare
     */
    @Test
    void testgetofferedfare(){
        DriveRequest mockrequest = Mockrequest();

        mockrequest.setOfferedFare(8);
        assertEquals(8 ,mockrequest.getOfferedFare());

        mockrequest.setOfferedFare(1000000000);
        assertEquals(1000000000 ,mockrequest.getOfferedFare());
    }
}
