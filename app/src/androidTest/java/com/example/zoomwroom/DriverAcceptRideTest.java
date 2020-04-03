package com.example.zoomwroom;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DriverAcceptRideTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance
     * @throws Exception
     * */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }


    /**
     * confirm ride after create the two geo-location
     * */
    @Test
    public void succesconfirmRide(){
        solo.clickOnButton("Rider");
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "dulong@rider.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "123456");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);




        solo.clickOnScreen(22,190);
        solo.clickOnButton("create a ride");
        solo.clickOnButton("CONFIRM");
//        solo.clickOnButton("CANCEL");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

        solo.goBackToActivity("MainActivity");
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("Driver");

        solo.assertCurrentActivity("Wrong Activity", DriverLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "dulong@ridertest1.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "123456");
        solo.clickOnButton("Log in");

        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);
        solo.clickOnScreen(22,90);
        solo.clickOnScreen(22,190);







    }
    /**
     * give a fare lower that the suggested
     * */
    @Test
    public void lowerandhigerfarepay(){
        solo.clickOnButton("Rider");
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "dulong@rider.com");
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "123456");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

        solo.clickOnScreen(45,80);
        solo.clickOnScreen(57,180);
        solo.clickOnButton("create a ride");

        solo.clearEditText( (EditText) solo.getView(R.id.fare_text));
        solo.enterText((EditText) solo.getView(R.id.fare_text), "1.00");
        solo.clickOnButton("CONFIRM");

        //
        solo.clearEditText( (EditText) solo.getView(R.id.fare_text));
        solo.enterText((EditText) solo.getView(R.id.fare_text), "6.12");

        solo.clickOnButton("CONFIRM");
        solo.clickOnButton("CANCEL");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);


    }




//        /**
//     * Login with a user that exists in the database
//     * */
//    @Test
//    public void successCreateRide(){
//        solo.clickOnButton("Rider");
//        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "dulong@rider.com");
//        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "123456");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//        solo.clickOnScreen(22,90);
//        solo.clickOnScreen(22,190);
//        solo.clickOnButton("create a ride");
//
//        solo.clickOnButton("CANCEL");
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//
//    }

//    /**
//     * create a ride without selecting two geo
//     * */
//    @Test
//    public void successcreateRidenogeo(){
//        solo.clickOnButton("Rider");
//        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "dulong@rider.com");
//        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "123456");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//        solo.clickOnButton("create a ride");
//        solo.clickOnButton("CANCEL");
//
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//
//    }


//    /**
//     * direct create aride without point two geo-location
//     * */
//    @Test
//    public void directCreatearideandconfirm(){
//        solo.clickOnButton("Rider");
//        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "johnnyle2@gmail.com");
//        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "abcd1234");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//        solo.clickOnButton("create a ride");
//        solo.clickOnButton("CONFIRM");
//
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//
//    }
//    /**
//     * direct cancel a ride after confirm
//     * */
//    @Test
//    public void directCreatearideandcancel(){
//        solo.clickOnButton("Rider");
//        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "johnnyle2@gmail.com");
//        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "abcd1234");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//        solo.clickOnButton("CANCEL");
//
//        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);
//
//
//    }



//    /**
//     * Try logging in without a username or password
//     * */
//    @Test
//    public void emptyRiderLogin(){
//        solo.clickOnButton("Rider");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//    }
//
//    /**
//     * Try logging in with only a username
//     * */
//    @Test
//    public void unsuccessfulRiderUsername(){
//        solo.clickOnButton("Rider");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "anguyen@gmail.com");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//
//    }
//
//    /**
//     * Try logging in with only password
//     * */
//    @Test
//    public void unsuccessfulRiderPassword(){
//        solo.clickOnButton("Rider");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "anguyen");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//    }
//
//    /**
//     * Try logging in with an incorrect password but a correct email
//     * */
//    @Test
//    public void incorrectUserPassword(){
//        solo.clickOnButton("Rider");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "anguyen@gmail.com");
//        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "anguyen");
//        solo.clickOnButton("Log in");
//        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
//    }

    /**
     * Close activity after each test
     * @throws Exception
     * */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }






}
