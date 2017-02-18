package com.arashivision.robolectricdemo;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
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

    /**
     * 验证执行顺序
     */
    @Test
    public void testVerifyInOrder() throws Exception {
        List list = mock(List.class);
        List list2 = mock(List.class);
        list.add(1);
        list2.add("hello");
        list.add(2);
        list2.add("world");
        InOrder inOrder = inOrder(list, list2);
        // 下面的代码不能颠倒顺序，验证执行顺序
        inOrder.verify(list).add(1);
        inOrder.verify(list2).add("hello");
        inOrder.verify(list).add(2);
        inOrder.verify(list2).add("world");
    }

    /**
     * 确保模拟对象上无互动发生
     */
    @Test
    public void testVerifyInteraction() throws Exception {
        List list = mock(List.class);
        List list2 = mock(List.class);
        List list3 = mock(List.class);
        list.add(1);
        verify(list).add(1);
        verify(list, never()).add(2);
        //验证零互动行为
        verifyNoMoreInteractions(list2, list3);
    }

    /**
     * 找出冗余的互动（即未被验证到的）
     */
    @Test
    public void testFindRedUndantInteraction(){
        List list = mock(List.class);
        list.add(1);
        list.add(2);
        verify(list, times(2)).add(anyInt());
        //检查是否有未被验证的互动行为，因为add(1)和add(2)都会被上面的anyInt()验证到，所以下面的代码会通过
        verifyNoMoreInteractions(list);

        List list2 = mock(List.class);
        list2.add(1);
        list2.add(2);
        verify(list2).add(1);
        //检查是否有未被验证的互动行为，因为add(2)没有被验证，所以下面的代码会失败抛出异常
//        verifyNoMoreInteractions(list2);
    }

    /**
     * 连续调用
     */
    @Test(expected = RuntimeException.class)
    public void testConsecutiveCalls() throws Exception {
        List list = mock(List.class);
        //模拟连续调用返回期望值，预设如果分开，则只有最后一个有效
        when(list.get(0)).thenReturn(0);
        when(list.get(0)).thenReturn(1);
        when(list.get(0)).thenReturn(2);
        when(list.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());
        assertEquals(2, list.get(0));
        assertEquals(2, list.get(0));
        assertEquals(0, list.get(1));
        assertEquals(1, list.get(1));
        //第三次或更多调用都会抛出异常
        list.get(1);
    }

    /**
     * 使用回调生成期望值
     */
    @Test
    public void testAnswerWithCallback() throws Exception {
        List list = mock(List.class);
        //使用Answer来生成我们我们期望的返回
        when(list.get(anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return "hello world:" + args[0];
            }
        });
        assertEquals("hello world:0", list.get(0));
        assertEquals("hello world:999", list.get(999));
    }

    /**
     * 监控真实对象
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSpyOnRealObjects() throws Exception {
        List list = new LinkedList();
        List spy = spy(list);
        //下面预设的spy.get(0)会报错，因为会调用真实对象的get(0)，所以会抛出越界异常
//        when(spy.get(0)).thenReturn(3);

        //使用doReturn-when可以避免when-thenReturn调用真实对象api
        doReturn(999).when(spy).get(999);
        when(spy.size()).thenReturn(100);
//        doReturn(100).when(spy).size();
        spy.add(1);
        spy.add(2);
        assertEquals(100, spy.size());
        assertEquals(1, spy.get(0));
        assertEquals(2, spy.get(1));
        verify(spy).add(1);
        verify(spy).add(2);
        assertEquals(999, spy.get(999));
        // throw IndexOutOfBoundsException
        spy.get(2);
    }

    /**
     * 修改对未预设的调用返回默认期望值
     */
    @Test
    public void testUnstubbedInvocations() throws Exception {
        //mock对象使用Answer来对未预设的调用返回默认期望值
        List mock = mock(List.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999;
            }
        });
        //下面的get(1)没有预设，通常情况下会返回NULL，但是使用了Answer改变了默认期望值
        assertEquals(999, mock.get(1));
        //下面的size()没有预设，通常情况下会返回0，但是使用了Answer改变了默认期望值
        assertEquals(999, mock.size());
    }

    /**
     * 捕获参数来进一步断言
     */
    @Test
    public void testCapturingArgs() throws Exception {
        UserDao userDao = mock(UserDao.class);
        UserService userService = new UserService(userDao);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        userService.update("test", "123456");
        verify(userDao).update(argumentCaptor.capture());
        assertEquals("test", argumentCaptor.getValue().getUsername());
        assertEquals("123456", argumentCaptor.getValue().getPassword());
    }

    /**
     * 真实的部分mock
     */
    @Test
    public void testRealPartialMock(){
        List list = spy(new ArrayList());
        assertEquals(0, list.size());
        A a = mock(A.class);
        when(a.doSomething(anyInt())).thenCallRealMethod();
        assertEquals(999, a.doSomething(999));
    }

    class A {
        public int doSomething(int i){
            return i;
        }
    }
}