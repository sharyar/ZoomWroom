package com.example.zoomwroom;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RiderprofileTest {

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
    public void checkprofile(){
        solo.clickOnButton("Rider");
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "dulong@ridertest1.com");
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "123456");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

        solo.clickOnView(solo.getView(R.id.rider_profile_button));

        solo.clickOnButton("Back");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);


    }
    /**
     * check the profile
     * */
    @Test
    public void editprofilecase1(){
        solo.clickOnButton("Rider");
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "dulong@ridertest1.com");
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "123456");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

        solo.clickOnView(solo.getView(R.id.rider_profile_button));

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
    public void editprofilecase2(){
        solo.clickOnButton("Rider");
        solo.enterText((EditText) solo.getView(R.id.rider_email_login), "dulong@ridertest1.com");
        solo.enterText((EditText) solo.getView(R.id.rider_password_login), "123456");
        solo.clickOnButton("Log in");
        solo.assertCurrentActivity("Wrong Activity", RiderHomeActivity.class);

        solo.clickOnView(solo.getView(R.id.rider_profile_button));

        solo.clickOnButton("Edit Profile");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_full_name_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_full_name_editText), "dulongsongl");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_name_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_name_editText), "test001");

        solo.clearEditText( (EditText) solo.getView(R.id.edit_user_phone_editText));
        solo.enterText((EditText) solo.getView(R.id.edit_user_phone_editText), "780-1284-533");

        solo.clickOnButton("Confirm Changes");

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
