package com.arashivision.robolectricdemo;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 15:19.
 */

public class UserService {
    private static final String TAG = "UserService";
    public static final int MSG_LOGIN_SUCCESS = 0;
    public static final int MSG_LOGIN_ERROR = 1;
    private UserDao mUserDao;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public UserService() {
    }

    public UserService(UserDao userDao) {
        mUserDao = userDao;
    }

    public void update(String username, String password) {
        mUserDao.update(new User(username, password));
    }

    public void login(final String username, final String password, final LoginCallback loginCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: 模拟登陆中...");
                try {
                    Thread.sleep(2000);
                    if (username.equals("foo@example.com") && password.equals("123456")) {
                        User user = new User(username, password);
                        loginCallback.onLoginSuccess(user);
                    }else {
                        LoginException exception = new LoginException("login error");
                        loginCallback.onLoginError(exception);
                    }
                } catch (InterruptedException e) {
                    LoginException exception = new LoginException(e);
                    loginCallback.onLoginError(exception);
                }
            }
        }).start();
    }
}
