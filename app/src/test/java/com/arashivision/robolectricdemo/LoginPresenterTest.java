package com.arashivision.robolectricdemo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 17:08.
 */
public class LoginPresenterTest {

    @Mock
    private UserService mUserService;
    @Mock
    private LoginContract.View mLoginView;
    @Captor
    private ArgumentCaptor<LoginCallback> mLoginCallbackArgumentCaptor;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mLoginPresenter = new LoginPresenter(mUserService, mLoginView);
        when(mLoginView.isActive()).thenReturn(true);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        mLoginPresenter.login("foo@example.com", "123456");
        verify(mUserService).login(eq("foo@example.com"), eq("123456"), mLoginCallbackArgumentCaptor.capture());
        User user = new User("foo@example.com", "123456");
        mLoginCallbackArgumentCaptor.getValue().onLoginSuccess(user);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(mLoginView).showLoginSuccess(userArgumentCaptor.capture());
    }
}