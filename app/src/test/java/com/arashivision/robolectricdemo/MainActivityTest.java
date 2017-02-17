package com.arashivision.robolectricdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static android.os.Build.VERSION_CODES.KITKAT;
import static org.junit.Assert.assertTrue;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 09:55.
 */
@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 22 /* 23,24,25 ng*/)
@Config(application = App.class)
public class MainActivityTest {

    @Test
    @Config(sdk = KITKAT)
    public void testOnCreate() throws Exception {
        assertTrue(Robolectric.setupActivity(MainActivity.class) != null);
    }
}