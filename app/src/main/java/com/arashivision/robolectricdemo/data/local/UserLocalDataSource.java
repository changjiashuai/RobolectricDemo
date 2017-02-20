package com.arashivision.robolectricdemo.data.local;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.arashivision.robolectricdemo.model.User;
import com.arashivision.robolectricdemo.data.UserDataSource;
import com.arashivision.robolectricdemo.login.LoginCallback;
import com.arashivision.robolectricdemo.login.LoginException;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/20 12:01.
 */

public class UserLocalDataSource implements UserDataSource {

    private static final String TAG = "UserLocalDataSource";

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    @Override
    public void update(User user) {

    }

    @Override
    public void login(final String username, final String password, final LoginCallback loginCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: 模拟登陆中...");
                try {
                    Thread.sleep(2000);
                    if (username.equals("foo@example.com") && password.equals("123456")) {
                        final User user = new User(username, password);
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                loginCallback.onLoginSuccess(user);
                            }
                        });
                    } else {
                        final LoginException exception = new LoginException("login error");
                        mUiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                loginCallback.onLoginError(exception);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    LoginException exception = new LoginException(e);
                    loginCallback.onLoginError(exception);
                }
            }
        }).start();
    }
}
