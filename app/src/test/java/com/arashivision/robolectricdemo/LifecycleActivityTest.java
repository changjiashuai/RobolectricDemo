package com.arashivision.robolectricdemo;

import android.content.Intent;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 11:36.
 */
@RunWith(RobolectricTestRunner.class)
public class LifecycleActivityTest {

    @Test
    public void testCreate() throws Exception {
        LifecycleActivity lifecycleActivity = Robolectric.buildActivity(LifecycleActivity.class).create().get();
        assertTrue(lifecycleActivity != null);
    }

    @Test
    public void testResume() throws Exception {
        ActivityController<LifecycleActivity> activityActivityController = Robolectric.buildActivity(LifecycleActivity.class);
        LifecycleActivity lifecycleActivity = activityActivityController.create().get();
        assertEquals("onCreate", lifecycleActivity.getTest());
        activityActivityController.resume();
        assertEquals("onResume", lifecycleActivity.getTest());
    }

    @Test
    public void testVisibleAfterCreate() throws Exception {
        ActivityController<LifecycleActivity> activityActivityController = Robolectric.buildActivity(LifecycleActivity.class);
        activityActivityController.create();
//        activityActivityController.visible();
        LifecycleActivity lifecycleActivity = activityActivityController.get();
        assertTrue("the view hierarchy of an Activity is not attached to the Window", lifecycleActivity.isVisible());
    }

    @Test
    public void testVisibleBeforeCreate() throws Exception {
        ActivityController<LifecycleActivity> activityActivityController = Robolectric.buildActivity(LifecycleActivity.class);
//        activityActivityController.create();
        activityActivityController.visible();
        LifecycleActivity lifecycleActivity = activityActivityController.get();
        assertFalse("the view hierarchy of an Activity is attached to the Window", lifecycleActivity.isVisible());
    }

    @Test
    public void testStartActivityWithIntent() throws Exception {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        LifecycleActivity lifecycleActivity = Robolectric.buildActivity(LifecycleActivity.class).withIntent(intent).create().get();
        assertNotNull("启动失败", lifecycleActivity);
    }

    @Test
    public void testRestoreInstanceState() throws Exception {
        Bundle savedInstanceState = new Bundle();
        String testStr = "I'm from Unit Test";
        savedInstanceState.putString("TEST", testStr);
        LifecycleActivity lifecycleActivity = Robolectric.buildActivity(LifecycleActivity.class)
                .create()
                .restoreInstanceState(savedInstanceState)
                .get();
        assertEquals(testStr, lifecycleActivity.getTest());
    }

    @Test
    public void testShowToast() throws Exception {
        LifecycleActivity lifecycleActivity = Robolectric.setupActivity(LifecycleActivity.class);
        lifecycleActivity.findViewById(R.id.btn_show_toast).performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("I'm a toast"));
    }
}