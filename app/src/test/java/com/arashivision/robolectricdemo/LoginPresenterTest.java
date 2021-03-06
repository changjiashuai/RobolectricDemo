package com.arashivision.robolectricdemo;

import android.content.Intent;
import android.widget.EditText;

import com.arashivision.robolectricdemo.data.UserRepository;
import com.arashivision.robolectricdemo.model.User;
import com.arashivision.robolectricdemo.ui.LifecycleActivity;
import com.arashivision.robolectricdemo.ui.login.LoginActivity;
import com.arashivision.robolectricdemo.ui.login.LoginCallback;
import com.arashivision.robolectricdemo.ui.login.LoginContract;
import com.arashivision.robolectricdemo.ui.login.LoginException;
import com.arashivision.robolectricdemo.ui.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.Scheduler;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 17:08.
 */
@RunWith(RobolectricTestRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginContract.View mLoginView;
    @Captor
    private ArgumentCaptor<LoginCallback> mLoginCallbackArgumentCaptor;
    @Mock
    private UserRepository mUserRepository;

    private LoginPresenter mLoginPresenter;
    private User successUser;
    private User errorUser;
    private LoginException loginException;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mLoginPresenter = new LoginPresenter(mUserRepository, mLoginView);
        successUser = new User("foo@example.com", "123456");
        errorUser = new User("error@example.com", "error");
        loginException = new LoginException("用户名或密码不正确");
        // make sure ui is visible
        when(mLoginView.isActive()).thenReturn(true);
    }

    // business logic flow
    @Test
    public void testLoginSuccess() throws Exception {
        mLoginPresenter.login(successUser.getUsername(), successUser.getPassword());
        //verify callback is called.
        verify(mUserRepository).login(eq("foo@example.com"), eq("123456"), mLoginCallbackArgumentCaptor.capture());

        mLoginCallbackArgumentCaptor.getValue().onLoginSuccess(successUser);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        //verify callback method onLoginSuccess is called.
        verify(mLoginView).showLoginSuccess(userArgumentCaptor.capture());
        assertEquals(successUser.getUsername(), userArgumentCaptor.getValue().getUsername());
        assertEquals(successUser.getPassword(), userArgumentCaptor.getValue().getPassword());
    }

    // Robolectric test exec logic flow
    @Test
    public void testLoginSuccessAndJumpWithCallback() {
        final LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        //mock click login button
        mUserRepository.login("fo@example.com", "123456", new LoginCallback() {
            @Override
            public void onLoginSuccess(User user) {
                ShadowActivity shadowActivity = shadowOf(loginActivity);
                Intent intent = shadowActivity.getNextStartedActivity();
                assertNotNull("Activity not started.", intent);
                assertEquals(intent.getComponent().getClassName(), LifecycleActivity.class.getName());
            }

            @Override
            public void onLoginError(LoginException e) {
                fail();
            }
        });
    }

    // user use flow
    @Test
    public void testLoginSuccessAndJump() throws Exception {
        final LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText mEmailView = (EditText) loginActivity.findViewById(R.id.email);
        EditText mPasswordView = (EditText) loginActivity.findViewById(R.id.password);
        mEmailView.setText("foo@example.com");
        mPasswordView.setText("123456");
        loginActivity.findViewById(R.id.email_sign_in_button).performClick();
        mockAsyncTask();
        ShadowActivity shadowActivity = shadowOf(loginActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertNotNull("Activity not started.", intent);
        assertEquals(intent.getComponent().getClassName(), LifecycleActivity.class.getName());
    }

    @Test
    public void testLoginWithEmptyUsernameAndPassword() throws Exception {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        loginActivity.findViewById(R.id.email_sign_in_button).performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("e:必填项"));
    }

    // -----------async callback---------------//
    @Test
    public void testLoginWithErrorUsernameAndErrorPassword() throws Exception {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText mEmailView = (EditText) loginActivity.findViewById(R.id.email);
        EditText mPasswordView = (EditText) loginActivity.findViewById(R.id.password);
        mEmailView.setText("fodfo@example.com");
        mPasswordView.setText("errorpwd");
        loginActivity.findViewById(R.id.email_sign_in_button).performClick();
        mockAsyncTask();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("e:用户名或密码不正确"));
    }

    @Test
    public void testLoginWithUsernameAndErrorPassword() throws Exception {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText mEmailView = (EditText) loginActivity.findViewById(R.id.email);
        EditText mPasswordView = (EditText) loginActivity.findViewById(R.id.password);
        mEmailView.setText("foo@example.com");
        mPasswordView.setText("errorpwd");
        loginActivity.findViewById(R.id.email_sign_in_button).performClick();
        mockAsyncTask();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("e:用户名或密码不正确"));
    }

    @Test
    public void testLoginWithErrorUsernameAndPassword() throws Exception {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        EditText mEmailView = (EditText) loginActivity.findViewById(R.id.email);
        EditText mPasswordView = (EditText) loginActivity.findViewById(R.id.password);
        mEmailView.setText("fodfo@example.com");
        mPasswordView.setText("123456");
        loginActivity.findViewById(R.id.email_sign_in_button).performClick();
        mockAsyncTask();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("e:用户名或密码不正确"));
    }

    private void mockAsyncTask() {
//        // 指示任务何时结束
//        final CountDownLatch signal = new CountDownLatch(1);
//        // 异步的任务
//        AsyncTask<String, Void, String> myTask = new AsyncTask<String, Void, String>() {
//            @Override
//            protected String doInBackground(String... arg0) {
//                //Do something meaningful.
//                try {
//                    // 模拟耗时 2s
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                return "something happened!";
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//                System.out.println("-->" + result);
//                signal.countDown();
//            }
//        };
//        // 开始异步任务
//        myTask.execute("Do something");
//
//        // 暂停当前的线程,等待异步任务完成
//        try {
//            signal.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        //获取主线程的消息队列的调度者，通过它可以知道消息队列的情况
        //并驱动主线程主动轮询消息队列
        Scheduler scheduler = Robolectric.getForegroundThreadScheduler();
        //因为调用请求方法后 后台线程请求需要一段时间才能请求完毕，然后才会通知主线程
        // 所以在这里进行等待，直到消息队列里存在消息
        System.out.println("size=" + scheduler.size());
        while (scheduler.size() == 1) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //轮询消息队列，这样就会在主线程进行通知
        scheduler.runOneTask();
        System.out.println("--> end");

    }
    // -----------END---------------//

    @Test
    public void testLoginError() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                LoginCallback callback = (LoginCallback) arguments[2];
                callback.onLoginError(loginException);
                return loginException;
            }
        }).when(mUserRepository).login(not(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return !argument.equals("foo@example.com");
            }
        })), not(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return !argument.equals("123456");
            }
        })), any(LoginCallback.class));
        mLoginPresenter.login(errorUser.getUsername(), errorUser.getPassword());
        // verify callback is called.
        verify(mUserRepository).login(eq(errorUser.getUsername()), eq(errorUser.getPassword()), mLoginCallbackArgumentCaptor.capture());

        mLoginCallbackArgumentCaptor.getValue().onLoginError(loginException);
        ArgumentCaptor<LoginException> loginExceptionArgumentCaptor = ArgumentCaptor.forClass(LoginException.class);
        // verify callback method onLoginError is called.
        verify(mLoginView).showLoginError(loginExceptionArgumentCaptor.capture());
    }
}