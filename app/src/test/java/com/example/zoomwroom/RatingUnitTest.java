package com.example.zoomwroom;

import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rating;
import com.example.zoomwroom.Entities.Rider;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RatingUnitTest {

    public Rating MockRating(){
        Driver driver = new Driver("baijin","baijin007","12345");
        Rider rider = new Rider("Marry","Marry007","54321");
        return new Rating(true,driver,rider);
    }
    public Rating MockRating1(){
        Driver driver = new Driver("baijin1","baijin008","123456");
        Rider rider = new Rider("Marry1","Marry008","543210");
        return new Rating(false,driver,rider);
    }

    @Test
    void testgetthumbstatue(){
        Rating rating = MockRating();
        Rating rating1 = MockRating1();


        assertTrue(rating.getThumbStatus());
        assertFalse(rating1.getThumbStatus());

    }

    @Test
    void testsetthumbstatue(){
        Rating rating = MockRating();
        Rating rating1 = MockRating1();

        rating.setThumbStatus(false);
        rating1.setThumbStatus(true);


        assertFalse(rating.getThumbStatus());
        assertTrue(rating1.getThumbStatus());

    }

    @Test
    void testgetrider(){
        Rating rating = MockRating();
        Rating rating1 = MockRating1();

        Rider rider1 = rating.getRider();
        Rider rider2 = rating1.getRider();

        assertEquals("Marry",rider1.getName());
        assertEquals("Marry1",rider2.getName());

        assertEquals("Marry007",rider1.getUserName());
        assertEquals("Marry008",rider2.getUserName());

        assertEquals("54321",rider1.getUserID());
        assertEquals("543210",rider2.getUserID());


    }

    @Test
    void testgetdriver(){
        Rating rating = MockRating();
        Rating rating1 = MockRating1();

        Driver driver1 = rating.getDriver();
        Driver driver2 = rating1.getDriver();

        assertEquals("baijin",driver1.getName());
        assertEquals("baijin1",driver2.getName());

        assertEquals("baijin007",driver1.getUserName());
        assertEquals("baijin008",driver2.getUserName());

        assertEquals("12345",driver1.getUserID());
        assertEquals("123456",driver2.getUserID());


    }

    @Test
    void testsetdriver(){
        Rating rating = MockRating();
        Rating rating1 = MockRating1();

        Driver driver1 = new Driver("bob","bob007","00000");
        Driver driver2 = new Driver("bob1","bob008","00001");

        rating.setDriver(driver1);
        rating1.setDriver(driver2);


        assertEquals("bob",rating.getDriver().getName());
        assertEquals("bob1",rating1.getDriver().getName());

    }

    @Test
    void testsetrider(){
        Rating rating = MockRating();
        Rating rating1 = MockRating1();

        Rider rider1 = new Rider("bob","bob007","00000");
        Rider rider2 = new Rider("bob1","bob008","00001");

        rating.setRider(rider1);
        rating1.setRider(rider2);


        assertEquals("bob",rating.getRider().getName());
        assertEquals("bob1",rating1.getRider().getName());

    }

    @Test
    void testgetdrive(){
        DriveRequest driveRequest = new DriveRequest();
        Rating rating = MockRating();

        rating.setDrive(driveRequest);

        assertEquals(driveRequest,rating.getDrive());

    }

    @Test
    void testsetdrive(){
        DriveRequest driveRequest = new DriveRequest();
        DriveRequest driveRequest1 = new DriveRequest();
        Rating rating = MockRating();

        rating.setDrive(driveRequest);

        assertEquals(driveRequest,rating.getDrive());

        rating.setDrive(driveRequest1);

        assertEquals(driveRequest1,rating.getDrive());

    }







}
