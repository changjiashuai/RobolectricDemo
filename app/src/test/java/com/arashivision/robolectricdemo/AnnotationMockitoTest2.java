package com.arashivision.robolectricdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 14:35.
 */
@RunWith(MockitoJUnitRunner.class)
public class AnnotationMockitoTest2 {

    @Mock
    private List mockList;

    @Test
    public void testAnnotation() throws Exception {
        mockList.add(1);
        verify(mockList).add(1);
    }
}
