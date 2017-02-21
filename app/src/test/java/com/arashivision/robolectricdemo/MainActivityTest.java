package com.arashivision.robolectricdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;

import com.arashivision.robolectricdemo.login.LoginActivity;
import com.arashivision.robolectricdemo.ui.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

import static android.os.Build.VERSION_CODES.KITKAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 2017/2/17 09:55.
 */
@RunWith(RobolectricTestRunner.class)
//@Config(constants = BuildConfig.class, sdk = 22 /* 23,24,25 ng*/)
//@Config(application = App.class)
@Config(qualifiers = "en")
public class MainActivityTest {

    private Context mContext;

    @Before
    public void setup() throws Exception {
        mContext = RuntimeEnvironment.application;
    }

    @Test
    @Config(sdk = KITKAT)
    public void testOnCreate() throws Exception {
        assertTrue(Robolectric.setupActivity(MainActivity.class) != null);
    }

    @Test
    @Config(qualifiers = "en")
    public void shouldUseEnglishResources() throws Exception {
        assertEquals("qualified resources", mContext.getString(R.string.overridden));
    }

    @Test
    @Config(qualifiers = "zh")
    public void shouldUseChineseResources() throws Exception {
        assertEquals("不重写", mContext.getString(R.string.not_overridden));
        assertEquals("指定资源", mContext.getString(R.string.overridden));
    }

    @Test
    public void testJump() throws Exception {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        mainActivity.findViewById(R.id.btn_login).performClick();
        ShadowActivity shadowActivity = shadowOf(mainActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        assertEquals(intent.getComponent().getClassName(), LoginActivity.class.getName());
    }

    @Test
    public void testCheckBoxState() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        CheckBox checkBox = (CheckBox) activity.findViewById(R.id.cb_test);
        //
        assertFalse(checkBox.isChecked());
        //
        checkBox.performClick();
        //
        assertTrue(checkBox.isChecked());
        //
        checkBox.performClick();
        //
        assertFalse(checkBox.isChecked());
    }

    @Test
    public void testShowAlertDialog() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();

        //
        assertNull(dialog);

        Button btnShowDialog = (Button) activity.findViewById(R.id.btn_show_dialog);
        btnShowDialog.performClick();
        Thread.sleep(1000);
        dialog = ShadowAlertDialog.getLatestAlertDialog();
        //
        System.out.println("dialog=" + dialog);
//        assertNotNull(dialog);
//        //
//        ShadowAlertDialog shadowAlertDialog = shadowOf(dialog);
//        assertEquals("警告", shadowAlertDialog.getTitle());
//        assertEquals("测试Alert Dialog", shadowAlertDialog.getMessage());
    }
}