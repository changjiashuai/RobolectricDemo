package com.arashivision.robolectricdemo.ui.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.arashivision.robolectricdemo.R;
import com.squareup.spoon.Spoon;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/4/17 10:18.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private static final String TAG = "LoginActivityTest";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginWithWrongPassword(){
        Spoon.screenshot(mActivityTestRule.getActivity(), TAG);

        onView(withId(R.id.email)).perform(typeText("foo@example.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("test"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

//        onView(withId(R.id.btn_show_toast)).check(doesNotExist());

        onView((withText("e:" + mActivityTestRule.getActivity()
                .getResources().getString(R.string.error_incorrect_login))))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().
                        getWindow().getDecorView())))).
                check(matches(isDisplayed()));

        Spoon.screenshot(mActivityTestRule.getActivity(), TAG);
    }

    @Test
    public void loginWithRightPassword(){
        Spoon.screenshot(mActivityTestRule.getActivity(), TAG);

        onView(withId(R.id.email)).perform(typeText("foo@example.com"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.btn_show_toast)).check(matches(isDisplayed()));

        Spoon.screenshot(mActivityTestRule.getActivity(), TAG);
    }
}