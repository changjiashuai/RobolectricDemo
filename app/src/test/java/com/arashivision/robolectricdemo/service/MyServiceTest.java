package com.arashivision.robolectricdemo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ServiceController;

import static org.junit.Assert.assertEquals;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/21 10:24.
 */
@RunWith(RobolectricTestRunner.class)
public class MyServiceTest {

    private ServiceController<MyService> mServiceController;
    private MyService mMyService;

    @Before
    public void setup() throws Exception {
        mServiceController = Robolectric.buildService(MyService.class);
        mMyService = mServiceController.get();
    }

    @Test
    public void testLifecycle() throws Exception {
        mServiceController.create();
        assertEquals("onCreate", mMyService.getState());
        mServiceController.startCommand(0, 0);
        assertEquals("onStartCommand", mMyService.getState());
        mServiceController.bind();
        assertEquals("onBind", mMyService.getState());
        mServiceController.unbind();
        assertEquals("onUnbind", mMyService.getState());
        mServiceController.destroy();
        assertEquals("onDestroy", mMyService.getState());
    }
}