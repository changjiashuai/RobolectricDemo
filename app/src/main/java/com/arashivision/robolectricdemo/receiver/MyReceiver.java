package com.arashivision.robolectricdemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//1. 验证应用程序是否注册了该广播
//2. 验证广播接收器的处理逻辑是否正确，关于逻辑是否正确，可以直接人为的触发onReceive()方法，让然后进行验证
public class MyReceiver extends BroadcastReceiver {
    private String test;

    public String getTest() {
        return test;
    }

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.arashivision.robolectricdemo.receiver")){
            test = intent.getStringExtra("EXTRA_TEST");
        }
    }
}