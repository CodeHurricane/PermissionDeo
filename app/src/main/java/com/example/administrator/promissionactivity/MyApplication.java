package com.example.administrator.promissionactivity;

import android.app.Application;

public class MyApplication extends Application {
    public static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication= (MyApplication)getApplicationContext();
    }
}
