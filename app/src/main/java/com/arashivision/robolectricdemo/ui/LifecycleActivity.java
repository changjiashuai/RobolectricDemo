package com.arashivision.robolectricdemo.ui;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.arashivision.robolectricdemo.R;

public class LifecycleActivity extends AppCompatActivity {

    private String test;
    private boolean isVisible = false;

    public String getTest() {
        return test;
    }

    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test = "onCreate";
        setContentView(R.layout.activity_lifecycle);
        isVisible = true;

        findViewById(R.id.btn_show_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LifecycleActivity.this, "I'm a toast", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        test = "onResume";
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            test = savedInstanceState.getString("TEST", null);
        }
    }
}
