package com.arashivision.robolectricdemo;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static android.os.Build.VERSION_CODES.KITKAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 09:55.
 */
@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 22 /* 23,24,25 ng*/)
//@Config(application = App.class)
@Config(qualifiers = "en")
public class MainActivityTest {

    private Context mContext;

    @Before
    public void setup() throws Exception {
        mContext = RuntimeEnvironment.application;
    }

    @Test
    @Config(sdk = KITKAT)
    public void testOnCreate() throws Exception {
        assertTrue(Robolectric.setupActivity(MainActivity.class) != null);
    }

    @Test
    @Config(qualifiers="en")
    public void shouldUseEnglishResources() {
        assertEquals("qualified resources", mContext.getString(R.string.overridden));
    }

    @Test
    @Config(qualifiers = "zh")
    public void shouldUseChineseResources(){
        assertEquals("不重写", mContext.getString(R.string.not_overridden));
        assertEquals("指定资源", mContext.getString(R.string.overridden));
    }
}