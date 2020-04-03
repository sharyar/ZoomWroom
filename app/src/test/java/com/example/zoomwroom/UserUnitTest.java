package com.example.zoomwroom;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.DriveRequest;
import com.example.zoomwroom.Entities.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {

    public User MockUser(){
        return new User("baijin","baijin007","1234567");
    }


    /**
     * test getname
     */
    @Test
    void testgetname(){
        User mockuser = MockUser();
        User user = new User("baijin","baijin007","12334567");
        assertEquals("baijin",mockuser.getName());

    }

    /**
     * test getuserName
     */
    @Test
    void testgetuserName(){
        User mockuser = MockUser();
        User user = new User("baijin","baijin007","12334567");
        assertEquals("baijin007",mockuser.getUserName());

    }

    /**
     * test getuserId
     */
    @Test
    void testgetuserId(){
        User mockuser = MockUser();
        User user = new User("baijin","baijin007","12334567");
        assertEquals("1234567",mockuser.getUserID());

    }

    /**
     * test testusername
     */
    @Test
    void testsetname(){
        User mockuser = MockUser();
        mockuser.setName("baijintao");

        assertEquals("baijintao",mockuser.getName());

    }

    /**
     * test setusername
     */
    @Test
    void testsetusername(){
        User mockuser = MockUser();
        mockuser.setUserName("baijin008");

        assertEquals("baijin008",mockuser.getUserName());

    }

    /**
     * test setuserid
     */
    @Test
    void testsetuserid(){
        User mockuser = MockUser();
        mockuser.setUserID("1234");

        assertEquals("1234",mockuser.getUserID());

    }

    /**
     * test getcontactInformation
     */
    @Test
    void TestgetcontactInformation(){
        User mockuser = MockUser();
        ContactInformation contactInformation = mockuser.getContactDetails();
        assertFalse((contactInformation instanceof ContactInformation));

        mockuser.setContactDetails(new ContactInformation("7808852269","275475610@qq.com"));
        ContactInformation contactInformation_new = mockuser.getContactDetails();
        assertTrue((contactInformation_new instanceof ContactInformation));


    }

    /**
     * test setcontactInformation
     */
    @Test
    void TestsetcontactInformation(){
        User mockuser = MockUser();
        mockuser.setContactDetails(new ContactInformation("7808852269","275475610@qq.com"));
        assertEquals("7808852269",mockuser.getContactDetails().getPhoneNumber());
        assertEquals("275475610@qq.com",mockuser.getContactDetails().getEmail());

        mockuser.setContactDetails(new ContactInformation("",""));
        assertEquals("",mockuser.getContactDetails().getPhoneNumber());
        assertEquals("",mockuser.getContactDetails().getEmail());

        mockuser.setContactDetails(new ContactInformation(null,null));
        assertEquals(null,mockuser.getContactDetails().getPhoneNumber());
        assertEquals(null,mockuser.getContactDetails().getEmail());



    }

    /**
     * test getCurrentrequest
     */
    @Test
    void TestgetCurrentrequest(){
        User mockuser = MockUser();
        DriveRequest currentrequest = new DriveRequest();
        assertTrue((currentrequest instanceof DriveRequest));


    }
    /**
     * test SetCurrentrequest
     */
    @Test
    void TestSetCurrentrequest(){
        User mockuser = MockUser();
        DriveRequest currentrequest = new DriveRequest();
        mockuser.setCurrentRequest(currentrequest);
        assertTrue((currentrequest instanceof DriveRequest));

    }

}
