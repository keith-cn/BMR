package com.hackathon.babymedicalrecord;

import com.umeng.analytics.MobclickAgent;

import android.app.Application;

public class BMRApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setDebugMode( true );
    }
}
