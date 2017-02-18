package com.arashivision.robolectricdemo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 14:35.
 */

public class AnnotationMockitoTest {

    @Mock
    private List mockList;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAnnotation() throws Exception {
        mockList.add(1);
        verify(mockList).add(1);
    }
}
