package com.arashivision.robolectricdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 09:55.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 22 /* 23,24,25 ng*/)
public class MainActivityTest {

    @Test
    public void testOnCreate() throws Exception {
        assertTrue(Robolectric.setupActivity(MainActivity.class) != null);
    }
}