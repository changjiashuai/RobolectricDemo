package com.arashivision.robolectricdemo.login;

import com.arashivision.robolectricdemo.model.User;
import com.arashivision.robolectricdemo.data.UserRepository;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 15:54.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG = "LoginPresenter";
    private final UserRepository mUserRepository;
    private final LoginContract.View mLoginView;

    public LoginPresenter(UserRepository userRepository, LoginContract.View loginView) {
        mUserRepository = userRepository;
        mLoginView = loginView;
        mLoginView.setPresenter(this);
    }

    @Override
    public void login(String username, String password) {
        mUserRepository.login(username, password, new LoginCallback() {
            @Override
            public void onLoginSuccess(User user) {
                mLoginView.showLoginSuccess(user);
            }

            @Override
            public void onLoginError(LoginException e) {
                mLoginView.showLoginError(e);
            }
        });
    }

    @Override
    public void start() {
    }
}
