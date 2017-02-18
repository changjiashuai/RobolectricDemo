package com.arashivision.robolectricdemo;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 15:47.
 */

public interface LoginContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();
        void showLoginSuccess(User user);
        void showLoginError(LoginException e);
    }

    interface Presenter extends BasePresenter {
        void login(String username, String password);
    }
}
