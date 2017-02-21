package com.arashivision.robolectricdemo.data;

import com.arashivision.robolectricdemo.model.User;
import com.arashivision.robolectricdemo.ui.login.LoginCallback;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/20 11:56.
 */

public interface UserDataSource {

    void update(User user);

    void login(String username, String password, LoginCallback loginCallback);
}
