package com.hackathon.babymedicalrecord;

import android.app.Application;

public class BMRApplication extends Application {

	public static int height = 0;
	public static int width = 0;
	private static BMRApplication instance;

	public static BMRApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}
}
