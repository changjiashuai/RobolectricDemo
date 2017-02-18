package com.arashivision.robolectricdemo;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 15:59.
 */

public interface LoginCallback {
    void onLoginSuccess(User user);
    void onLoginError(LoginException e);
}
