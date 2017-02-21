package com.arashivision.robolectricdemo.fragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.FragmentTestUtil;

import static org.junit.Assert.assertNotNull;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/21 10:32.
 */
@RunWith(RobolectricTestRunner.class)
public class MyFragmentTest {

    private MyFragment mMyFragment;

    @Before
    public void setup() throws Exception {
        mMyFragment = new MyFragment();
        // 把Fragment添加到Activity中 ---> android.app.Fragment
        // 如果使用support的Fragment testCompile "org.robolectric:shadows-support-v4:3.0"
        FragmentTestUtil.startFragment(mMyFragment);
    }

    @Test
    public void testFragment() throws Exception {
        assertNotNull(mMyFragment.getView());
    }
}