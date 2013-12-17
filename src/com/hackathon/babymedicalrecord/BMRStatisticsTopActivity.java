package com.hackathon.babymedicalrecord;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.hackathon.babymedicalrecord.BMRNumberBean.Data;
import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;

public class BMRStatisticsTopActivity extends Activity {

	private ViewPager mViewPager;
	
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_statistics_top);
		
		mViewPager = ((ViewPager) findViewById(R.id.top_pie));
//		initFrameData();
//		initFrameView();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		

		// TODO maybe need move to handler function
		initViewPager(mViewPager);
	}
	

	private void initViewPager(ViewPager paramViewPager) {

		
	}
}
