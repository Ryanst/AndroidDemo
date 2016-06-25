package com.ryanst.app.activity;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.ryanst.app.core.MainActivity;

/**
 * Created by zhengjuntong on 16/5/6.
 */

public class MainActicityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public MainActicityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStartClose() throws Exception {
        Thread.sleep(1000);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();

    }

}
