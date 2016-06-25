package com.ryanst.app.activity.test;

import android.test.ActivityInstrumentationTestCase2;

import com.ryanst.app.activity.JunitTestActivity;

/**
 * Created by zhengjuntong on 16/5/5.
 */
public class JunitTestActivityTest extends ActivityInstrumentationTestCase2<JunitTestActivity> {

    private JunitTestActivity activity;

    public JunitTestActivityTest() {
        super(JunitTestActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testPreconditions() {
        assertNotNull("mFirstTestActivity is null", activity);
    }

    public void testAddNum() throws Exception {
        int sum = 1 + 2;
        assertEquals(sum, activity.addNum(1, 2));
    }
}