package com.example.zoomwroom;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.zoomwroom.ScannerActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class QRScannerTest {

    private Solo solo;
    public ActivityTestRule<ScannerActivity> rule =
            new ActivityTestRule<>(ScannerActivity.class, true, true);

    @Test
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }
}
