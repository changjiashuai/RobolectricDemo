package com.arashivision.robolectricdemo.receiver;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/21 10:05.
 */
@RunWith(RobolectricTestRunner.class)
public class MyReceiverTest {

    @Test
    public void testRegister() throws Exception {
        ShadowApplication shadowApplication = ShadowApplication.getInstance();
        String action = "com.arashivision.robolectricdemo.receiver";
        Intent intent = new Intent(action);
        assertTrue(shadowApplication.hasReceiverForIntent(intent));
    }

    @Test
    public void testReceive() throws Exception {
        String action = "com.arashivision.robolectricdemo.receiver";
        Intent intent = new Intent(action);
        intent.putExtra("EXTRA_TEST", "testReceive");
        MyReceiver myReceiver = new MyReceiver();
        myReceiver.onReceive(RuntimeEnvironment.application, intent);
        assertEquals("testReceive", myReceiver.getTest());
    }
}