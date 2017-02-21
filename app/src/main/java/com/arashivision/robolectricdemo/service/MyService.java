package com.arashivision.robolectricdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    private String state;

    public String getState() {
        return state;
    }

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        state = "onCreate";
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        state = "onStartCommand";
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        state = "onBind";
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        state = "onUnbind";
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        state = "onDestroy";
    }
}