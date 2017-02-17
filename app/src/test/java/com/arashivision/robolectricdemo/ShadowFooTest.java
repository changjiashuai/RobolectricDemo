package com.arashivision.robolectricdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.internal.ShadowExtractor;

import static org.junit.Assert.assertEquals;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 15:53.
 */
@RunWith(RobolectricTestRunner.class)
//@Config(shadows = ShadowFoo.class)
public class ShadowFooTest {

    @Test
    public void testGetName() throws Exception {
        ShadowFoo shadowFoo = (ShadowFoo) ShadowExtractor.extract(new Foo("test"));
        assertEquals("Hello, I'm a shadow of Foo: test", shadowFoo.getName());
    }
}
