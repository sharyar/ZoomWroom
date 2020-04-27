package com.example.zoomwroom;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class DriverprofileTest {

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
     * check the profile
     * */
    @Test
    public void drivercheckprofile() throws InterruptedException {
        solo.clickOnButton("Driver");
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "dulong@driver.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "123456");
        solo.clickOnButton("Log in");
        TimeUnit.SECONDS.sleep(5);
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);

        solo.clickOnView(solo.getView(R.id.view_user_profile_btn_driver_home));

        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);


    }
    /**
     * check the profile
     * */
    @Test
    public void drivereditprofilecase1() throws InterruptedException {
        solo.clickOnButton("Driver");
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "dulong@driver.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "123456");
        solo.clickOnButton("Log in");
        TimeUnit.SECONDS.sleep(5);
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);

        solo.clickOnView(solo.getView(R.id.view_user_profile_btn_driver_home));

        solo.clickOnButton("Edit Profile");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_full_name_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_full_name_editText), "dulongsong021");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_name_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_name_editText), "test002");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_phone_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_phone_editText), "7809999999");

        solo.clickOnButton("Confirm Changes");

       // solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);


    }
    /**
     * edit profile with correct format
     * */
    @Test
    public void editprofilecase2() throws InterruptedException {
        solo.clickOnButton("Driver");
        solo.enterText((EditText) solo.getView(R.id.driver_email_login), "dulong@driver.com");
        solo.enterText((EditText) solo.getView(R.id.driver_password), "123456");
        solo.clickOnButton("Log in");
        TimeUnit.SECONDS.sleep(5);
        solo.assertCurrentActivity("Wrong Activity", DriverHomeActivity.class);
        solo.clickOnView(solo.getView(R.id.view_user_profile_btn_driver_home));
        solo.clickOnButton("Edit Profile");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_full_name_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_full_name_editText), "baijingtao");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_name_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_name_editText), "baijin7394");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_phone_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_phone_editText), "780-995-533");

        solo.clickOnButton("Confirm Changes");
        solo.clickOnButton("Back");
        solo.clickOnView(solo.getView(R.id.view_user_profile_btn_driver_home));

        solo.assertCurrentActivity("Wrong Activity",EditUserProfileActivity.class);


        // solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);


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
