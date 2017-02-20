package com.arashivision.robolectricdemo.data;

import com.arashivision.robolectricdemo.model.User;
import com.arashivision.robolectricdemo.login.LoginCallback;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/20 11:57.
 */

public class UserRepository implements UserDataSource {

    // localDataSource
    private UserDataSource mUserDataSource;

    public UserRepository(UserDataSource userDataSource) {
        mUserDataSource = userDataSource;
    }

    @Override
    public void update(User user) {
        mUserDataSource.update(user);
    }

    @Override
    public void login(String username, String password, LoginCallback loginCallback) {
        mUserDataSource.login(username, password, loginCallback);
    }
}
