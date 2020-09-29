package com.example.posthometask.ui;

import android.app.Application;
import android.content.SharedPreferences;

import com.example.posthometask.data.local.PreferenceUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceUtils.init(this);
    }
}
