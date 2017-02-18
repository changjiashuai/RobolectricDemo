package com.arashivision.robolectricdemo;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 15:19.
 */

public class UserService {
    private UserDao mUserDao;

    public UserService(UserDao userDao) {
        mUserDao = userDao;
    }

    public void update(String username, String password){
        mUserDao.update(new User(username, password));
    }
}
