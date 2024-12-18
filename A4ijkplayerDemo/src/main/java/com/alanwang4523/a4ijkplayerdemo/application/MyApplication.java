package com.alanwang4523.a4ijkplayerdemo.application;

import androidx.multidex.MultiDexApplication;

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        MyFlutterEngine.init(this, "/detail");
    }
}
