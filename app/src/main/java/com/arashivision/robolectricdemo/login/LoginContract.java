package com.arashivision.robolectricdemo.login;

import com.arashivision.robolectricdemo.BasePresenter;
import com.arashivision.robolectricdemo.BaseView;
import com.arashivision.robolectricdemo.User;

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
