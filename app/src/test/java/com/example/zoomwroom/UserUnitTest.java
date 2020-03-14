package com.example.zoomwroom;

import com.example.zoomwroom.Entities.ContactInformation;
import com.example.zoomwroom.Entities.User;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserUnitTest {

    public User MockUser(){
        return new User("baijin","baijin007","1234567");
    }

    @Test
    void testgetname(){
        User mockuser = MockUser();
        User user = new User("baijin","baijin007","12334567");
        assertEquals("baijin",mockuser.getName());

    }

    @Test
    void testgetuserName(){
        User mockuser = MockUser();
        User user = new User("baijin","baijin007","12334567");
        assertEquals("baijin007",mockuser.getUserName());

    }

    @Test
    void testgetuserId(){
        User mockuser = MockUser();
        User user = new User("baijin","baijin007","12334567");
        assertEquals("1234567",mockuser.getUserID());

    }

    @Test
    void testsetname(){
        User mockuser = MockUser();
        mockuser.setName("baijintao");

        assertEquals("baijintao",mockuser.getName());

    }

    @Test
    void testsetusername(){
        User mockuser = MockUser();
        mockuser.setUserName("baijin008");

        assertEquals("baijin008",mockuser.getUserName());

    }
    @Test
    void testsetuserid(){
        User mockuser = MockUser();
        mockuser.setUserID("1234");

        assertEquals("1234",mockuser.getUserID());

    }

    @Test
    void testsetbalance(){
        User mockuser = MockUser();

        mockuser.setBalance(0);
        assertEquals(0,mockuser.getBalance());

        mockuser.setBalance(1);
        assertEquals(1,mockuser.getBalance());

        mockuser.setBalance(1.2f);
        assertEquals(1.2f,mockuser.getBalance());

        mockuser.setBalance(1000000000000.00000000f);
        assertEquals(1000000000000.00000000f,mockuser.getBalance());

    }

    @Test
    void testgetbalcance(){
        User mockuser = MockUser();
        mockuser.setBalance(0);
        assertEquals(0,mockuser.getBalance());

        mockuser.setBalance(1);
        assertEquals(1,mockuser.getBalance());

        mockuser.setBalance(1.2f);
        assertEquals(1.2f,mockuser.getBalance());

        mockuser.setBalance(1000000000000.00000000f);
        assertEquals(1000000000000.00000000f,mockuser.getBalance());


    }
    @Test
    void testaddbalance() {
        User mockuser = MockUser();
        mockuser.setBalance(0);
        mockuser.addBalance(1.2f);

        assertEquals(1.2f,mockuser.getBalance());

        mockuser.setBalance(100.3f);
        mockuser.addBalance(1.2f);

        assertEquals(101.5f,mockuser.getBalance());

        mockuser.setBalance(100000000000000000.3f);
        mockuser.addBalance(1.2f);

        assertEquals(100000000000000001.5f,mockuser.getBalance());



    }
    @Test
    void TestgetcontactInformation(){
        User mockuser = MockUser();
        ContactInformation contactInformation = mockuser.getContactDetails();
        assertFalse((contactInformation instanceof ContactInformation));

        mockuser.setContactDetails(new ContactInformation("7808852269","275475610@qq.com"));
        ContactInformation contactInformation_new = mockuser.getContactDetails();
        assertTrue((contactInformation_new instanceof ContactInformation));


    }

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

}
