package com.example.zoomwroom;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.Driver;
import com.example.zoomwroom.Entities.Rating;
import com.example.zoomwroom.Entities.Rider;
import com.example.zoomwroom.Entities.User;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiverUnitTest {

    public Driver MockDriver(){
        return new Driver("baijin","baijin007","1234567");
    }

    @Test
    void testsetrating(){
        Driver mockDriver = MockDriver();
        Rating rating1 = new Rating(true,new Driver("john","john001","1234"),
                new Rider("Marry","Marry001","12345"));

        Rating rating2 = new Rating(true,new Driver("john1","john002","12341234"),
                new Rider("Marry1","Marry002","1234512345"));

        ArrayList<Rating> ratings1 = new ArrayList<>();
        ratings1.add(rating1);
        ratings1.add(rating2);

        mockDriver.setRatings(ratings1);

        assertEquals(ratings1,mockDriver.getRatings());



    }
    @Test
    void testgetrating() {
        Driver mockDriver = MockDriver();
        Rating rating1 = new Rating(true,new Driver("john","john001","1234"),
                new Rider("Marry","Marry001","12345"));

        Rating rating2 = new Rating(true,new Driver("john1","john002","12341234"),
                new Rider("Marry1","Marry002","1234512345"));

        ArrayList<Rating> ratings1 = new ArrayList<>();
        ratings1.add(rating1);
        ratings1.add(rating2);

        mockDriver.setRatings(ratings1);

        assertEquals(ratings1,mockDriver.getRatings());


    }

    @Test
    void testsetrequest(){
        Driver mockDriver = MockDriver();
        DriveRequest driveRequest = new DriveRequest();

        mockDriver.setCurrentRequest(driveRequest);

        assertEquals(driveRequest,mockDriver.getCurrentRequest());

    }

    @Test
    void testgetrequest(){
        Driver mockDriver = MockDriver();
        DriveRequest driveRequest = new DriveRequest();

        mockDriver.setCurrentRequest(driveRequest);

        assertEquals(driveRequest,mockDriver.getCurrentRequest());

    }


}
