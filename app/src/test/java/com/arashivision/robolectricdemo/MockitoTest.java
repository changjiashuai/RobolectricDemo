package com.arashivision.robolectricdemo;

import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 11:46.
 */

public class MockitoTest {

    /**
     * 验证行为
     */
    @Test
    public void testVerifyBehaviour() throws Exception {
        // 模拟创建一个List对象
        List mock = mock(List.class);
        // 使用mock的对象
        mock.add(1);
        mock.clear();
        // 验证add(1)和clear()行为是否发生
        verify(mock).add(1);
        verify(mock).clear();
    }

    //--------START--------------

    /**
     * 模拟我们所期望的结果
     */
    @Test
    public void testWhenThenReturn() throws Exception {
        // mock
        Iterator iterator = mock(Iterator.class);
        // 预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        // 使用mock
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        // 验证结果
        assertEquals("hello world world", result);
    }

    @Test(expected = IOException.class)
    public void testWhenThenThrow() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        // 预设当流关闭时抛出IO异常
        doThrow(new IOException()).when(outputStream).close();
        outputStream.close();
    }
    //--------END--------------


    //--------START--------------

    /**
     * 参数匹配
     */
    @Test
    public void testWithArguments() throws Exception {
        Comparable comparable = mock(Comparable.class);
        when(comparable.compareTo("Test")).thenReturn(1);
        when(comparable.compareTo("Hi")).thenReturn(2);
        assertEquals(1, comparable.compareTo("Test"));
        assertEquals(2, comparable.compareTo("Hi"));
        // 没有预设的返回默认值
        assertEquals(0, comparable.compareTo("No stub"));
    }

    @Test
    public void testWithUnspecifiedArguments() throws Exception {
        List list = mock(List.class);
        // 匹配任意参数
        when(list.get(anyInt())).thenReturn(1);
        when(list.contains(argThat(new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Integer argument) {
                return argument == 1 || argument == 2;
            }
        }))).thenReturn(true);
        assertEquals(1, list.get(1));
        assertEquals(1, list.get(999));
        assertTrue(list.contains(1));
        assertTrue(!list.contains(3));
    }

    @Test
    public void testAllArgumentsProvidedByMatchers() {
        Comparator comparator = mock(Comparator.class);
        comparator.compare("hi", "hello");
        // 如果你使用了参数匹配，那么所有的参数都必须通过matchers来匹配
        verify(comparator).compare(anyString(), eq("hello"));
        // 下面的为无效的参数匹配使用
//        verify(comparator).compare(anyString(), "hello");
    }
    //--------END--------------

    /**
     * 验证确切的调用次数
     */
    @Test
    public void testVerifyNumberOfInvocations() throws Exception {
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);

        //验证是否被调用一次，等效于下面的times(1)
        verify(list).add(1);
        verify(list, times(1)).add(1);

        //验证是否被调用2次
        verify(list, times(2)).add(2);

        //验证是否被调用3次
        verify(list, times(3)).add(3);

        //验证是否从未被调用过
        verify(list, never()).add(4);

        //验证至少调用一次
        verify(list, atLeastOnce()).add(1);

        //验证至少调用2次
        verify(list, atLeast(2)).add(2);

        //验证至多调用3次
        verify(list, atLeast(3)).add(3);
    }

    /**
     * 模拟方法体抛出异常
     */
    @Test(expected = RuntimeException.class)
    public void testDoThrowWhen() throws Exception {
        List list = mock(List.class);
        doThrow(new RuntimeException()).when(list).add(1);
        list.add(1);
    }

}