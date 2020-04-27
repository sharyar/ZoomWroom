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

    /**
     * test getrating
     */
    @Test
    void testgetrating() {
        Driver mockDriver = MockDriver();
        Rating rating1 = new Rating();

        mockDriver.setRating(rating1);

        assertEquals(rating1,mockDriver.getRating());


    }

    /**
     * test setrating
     */
    @Test
    void testsetrating() {
        Driver mockDriver = MockDriver();
        Rating rating1 = new Rating();

        mockDriver.setRating(rating1);
        assertEquals(rating1,mockDriver.getRating());
    }




}
