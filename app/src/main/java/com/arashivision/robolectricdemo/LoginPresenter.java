package com.arashivision.robolectricdemo;

import android.util.Log;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 15:54.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";
    private final UserService mUserService;
    private final LoginContract.View mLoginView;

    public LoginPresenter(UserService userService, LoginContract.View loginView) {
        mUserService = userService;
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        mUserService.login(username, password, new LoginCallback() {
            @Override
            public void onLoginSuccess(User user) {
//                Log.i(TAG, "onLoginSuccess: ");
                mLoginView.showLoginSuccess(user);
            }

            @Override
            public void onLoginError(LoginException e) {
//                Log.i(TAG, "onLoginError: ");
                mLoginView.showLoginError(e);
            }
        });
    }

    @Override
    public void start() {
    }
}
