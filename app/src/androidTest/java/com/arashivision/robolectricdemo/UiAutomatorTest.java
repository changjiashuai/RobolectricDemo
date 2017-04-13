package com.arashivision.robolectricdemo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/4/12 15:30.
 */
@RunWith(AndroidJUnit4.class)
public class UiAutomatorTest {

    private static final String TAG = "UiAutomatorTest";
    private static final String BASIC_SAMPLE_PACKAGE
            = "com.arashivision.robolectricdemo";

    private static final int LAUNCH_TIMEOUT = 5000;

    private UiDevice mUiDevice;
    private Context mContext;

    @Before
    public void testStartMainActivityFromHomeScreen() {
        mUiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mUiDevice.pressHome();

        mContext = InstrumentationRegistry.getContext();

        String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mUiDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mUiDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkPreconditions() {
        assertThat(mUiDevice, notNullValue());
    }


    private File createFile(){
        String filename = System.currentTimeMillis() + ".png";
        return new File(mContext.getExternalCacheDir(), filename);
    }

    private void screenshot(){
        File file = createFile();
        mUiDevice.takeScreenshot(file);
        Spoon.save(mContext, file);
    }

    @Test
    public void testShowAlertDialog() {
        mUiDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btn_show_dialog"))
                .click();

        screenshot();

        UiObject2 dialogMessage = mUiDevice.wait(Until
                .findObject(By.text("测试Alert Dialog")), 500);
        assertThat(dialogMessage.getText(), is(equalTo("测试Alert Dialog")));

        screenshot();

    }

    @Test
    public void testGoToLogin(){
        mUiDevice.findObject(By.res(BASIC_SAMPLE_PACKAGE, "btn_login"))
                .click();
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
