package com.example.zoomwroom;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactinformationUnitTest {

    public ContactInformation MockContactinformation(){
        return new ContactInformation("7808852269","test@gmail.ca");
    }

    /**
     * test setphonenumber
     */
    @Test
    void testsetphonenumber(){
        ContactInformation contactInformation = MockContactinformation();

        assertEquals("7808852269",contactInformation.getPhoneNumber());
        assertNotEquals("7808852268",contactInformation.getPhoneNumber());

        contactInformation.setPhoneNumber("7808852268");
        assertEquals("7808852268",contactInformation.getPhoneNumber());
        assertNotEquals("7808852269",contactInformation.getPhoneNumber());


    }

    /**
     * test setemail
     */
    @Test
    void testsetemail(){
        ContactInformation contactInformation = MockContactinformation();

        assertEquals("test@gmail.ca",contactInformation.getEmail());
        assertNotEquals("test@gmail1.ca",contactInformation.getEmail());

        contactInformation.setEmail("test1@gmail.ca");
        assertEquals("test1@gmail.ca",contactInformation.getEmail());
        assertNotEquals("test1@gmail1.ca",contactInformation.getEmail());
    }

    /**
     * test getphonenumber
     */
    @Test
    void testgetphonenumber(){
        ContactInformation contactInformation = MockContactinformation();

        assertEquals("7808852269",contactInformation.getPhoneNumber());
        assertNotEquals("7808852268",contactInformation.getPhoneNumber());

        contactInformation.setPhoneNumber("7808852268");
        assertEquals("7808852268",contactInformation.getPhoneNumber());
        assertNotEquals("7808852269",contactInformation.getPhoneNumber());


    }

    /**
     * test getemail
     */
    @Test
    void testgetemail(){
        ContactInformation contactInformation = MockContactinformation();

        assertEquals("test@gmail.ca",contactInformation.getEmail());
        assertNotEquals("test@gmail1.ca",contactInformation.getEmail());

        contactInformation.setEmail("test1@gmail.ca");
        assertEquals("test1@gmail.ca",contactInformation.getEmail());
        assertNotEquals("test1@gmail1.ca",contactInformation.getEmail());
    }


}
