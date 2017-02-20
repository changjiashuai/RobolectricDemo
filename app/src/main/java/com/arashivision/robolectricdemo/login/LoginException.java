package com.arashivision.robolectricdemo.login;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/18 16:36.
 */

public class LoginException extends Exception {
    public LoginException() {
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }
}
