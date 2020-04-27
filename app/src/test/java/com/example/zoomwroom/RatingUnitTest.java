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
        return new Rating();
    }

    /**
     * test setthumbsup
     */

    @Test
    void testsetthumbsup(){
        Rating rating = MockRating();

        rating.setThumbsUp(20);
        assertEquals(20,rating.getThumbsUp());

        rating.setThumbsUp(1000000000);
        assertEquals(1000000000,rating.getThumbsUp());

        rating.setThumbsUp(-1000000000);
        assertEquals(-1000000000,rating.getThumbsUp());





    }
    /**
     * test getthumbsup
     */


    @Test
    void testgetthumbsup(){
        Rating rating = MockRating();

        rating.setThumbsUp(20);
        assertEquals(20,rating.getThumbsUp());

        rating.setThumbsUp(1000000000);
        assertEquals(1000000000,rating.getThumbsUp());

        rating.setThumbsUp(-1000000000);
        assertEquals(-1000000000,rating.getThumbsUp());





    }
    /**
     * test gsetthumbsdown
     */

    @Test
    void testsetthumbsdown(){
        Rating rating = MockRating();

        rating.setThumbsDown(20);
        assertEquals(20,rating.getThumbsDown());

        rating.setThumbsDown(1000000000);
        assertEquals(1000000000,rating.getThumbsDown());

        rating.setThumbsDown(-1000000000);
        assertEquals(-1000000000,rating.getThumbsDown());





    }
    /**
     * test getthumbsdown
     */

    @Test
    void testgetthumbsdown(){
        Rating rating = MockRating();

        rating.setThumbsDown(20);
        assertEquals(20,rating.getThumbsDown());

        rating.setThumbsDown(1000000000);
        assertEquals(1000000000,rating.getThumbsDown());

        rating.setThumbsDown(-1000000000);
        assertEquals(-1000000000,rating.getThumbsDown());

    }





}
