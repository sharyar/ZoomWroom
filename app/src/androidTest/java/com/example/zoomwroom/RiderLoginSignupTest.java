package com.example.zoomwroom;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RiderLoginSignupTest {

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
     * Leaving every single field empty and try signing up
     * */
    @Test
    public void emptyRiderSignUp(){
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong Activity", RiderModeActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", RiderSignUpActivity.class);
    }

    /**
     * Creating a valid user
     * Should lead into the rider home page at the end
     * */
    @Test
    public void successfulSignup(){
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong Activity", RiderModeActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", RiderSignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.riderSignupFName), "Johnny");
        solo.enterText((EditText) solo.getView(R.id.riderSignupLName), "Le");
        solo.enterText((EditText) solo.getView(R.id.riderSignupEmailAddress), "johnnyle@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.riderSignupUserName), "johnnyle");
        solo.enterText((EditText) solo.getView(R.id.riderSignupPassWord), "acbd1234");
        solo.enterText((EditText) solo.getView(R.id.riderSignupPhoneNumber), "7801234567");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

    }
    /**
     * Login with a user that exists in the database
     * */
    @Test
    public void successfulRiderLogin(){
        solo.clickOnButton("Rider");
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "johnnyle2@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "abcd1234");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

    }
    /**
     * 1. Try logging in without a username or password
     * 2. Try logging in with only a username
     * 3. Try logging in with only password
     * */
    @Test
    public void unsuccessfulRiderLogin(){
        solo.clickOnButton("Rider");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderModeActivity.class);

        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "anguyen@gmail.com");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderModeActivity.class);
        solo.clearEditText((EditText) solo.getView(R.id.rider_email_login));


        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "anguyen");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderModeActivity.class);

    }

    /**
     * Close activity after each test
     * @throws Exception
     * */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }






}
