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
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
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
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", RiderSignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.riderSignupFName), "Sarah");
        solo.enterText((EditText) solo.getView(R.id.riderSignupLName), "Le");
        solo.enterText((EditText) solo.getView(R.id.riderSignupEmailAddress), "Sarahle@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.riderSignupUserName), "sarahle");
        solo.enterText((EditText) solo.getView(R.id.riderSignupPassWord), "password1234");
        solo.enterText((EditText) solo.getView(R.id.riderSignupPhoneNumber), "780-123-2341");
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
     * Try logging in without a username or password
     * */
    @Test
    public void emptyRiderLogin(){
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
    }

    /**
     * Try logging in with only a username
     * */
    @Test
    public void unsuccessfulRiderUsername(){
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "anguyen@gmail.com");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);

    }

    /**
     * Try logging in with only password
     * */
    @Test
    public void unsuccessfulRiderPassword(){
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "anguyen");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
    }

    /**
     * Try logging in with an incorrect password but a correct email
     * */
    @Test
    public void incorrectUserPassword(){
        solo.clickOnButton("Rider");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "anguyen@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "anguyen");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderLoginActivity.class);
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
