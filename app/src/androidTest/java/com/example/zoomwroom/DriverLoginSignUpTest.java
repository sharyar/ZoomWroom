package com.example.zoomwroom;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DriverLoginSignUpTest {
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
    public void emptyDriverSignUp(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", DriverSignUpActivity.class);
    }

    /**
     * Creating a valid user
     * Should lead into the driver home page at the end
     * */
    @Test
    public void successfulSignup(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", DriverSignUpActivity.class);
        solo.enterText((EditText) solo.getView(R.id.driverSignupFName), "Johnny");
        solo.enterText((EditText) solo.getView(R.id.driverSignupLName), "Le");
        solo.enterText((EditText) solo.getView(R.id.driverSignupEmailAddress), "johnnyle@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.driverSignupUserName), "johnnyle");
        solo.enterText((EditText) solo.getView(R.id.driverSignupPassWord), "acbd1234");
        solo.enterText((EditText) solo.getView(R.id.driverSignupPhoneNumber), "7801234567");
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);

    }
    /**
     * Login with a user that exists in the database
     * */
    @Test
    public void successfulDriverLogin(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "johnnyle2@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "abcd1234");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);

    }
    /**
     * Try logging in without a username or password
     * */
    @Test
    public void emptyDriverLogin(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
    }

    /**
     * Try logging in with only a username
     * */
    @Test
    public void unsuccessfulDriverUsername(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "anguyen@gmail.com");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);

    }

    /**
     * Try logging in with only password
     * */
    @Test
    public void unsuccessfulDriverPassword(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.driver_password), "anguyen");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
    }

    /**
     * Try logging in with an incorrect password but a correct email
     * */
    @Test
    public void incorrectUserPassword(){
        solo.clickOnButton("Driver");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "anguyen@gmail.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "anguyen");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", DriverModeActivity.class);
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
