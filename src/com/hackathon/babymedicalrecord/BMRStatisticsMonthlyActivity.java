package com.hackathon.babymedicalrecord;

import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BMRStatisticsMonthlyActivity extends Activity {

	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_monthly_num_detail);
		
		mViewPager = ((ViewPager) findViewById(R.id.monthly_num_viewpager));
		
		//initFrameData(); 
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		

		// TODO maybe need move to handler function
		initViewPager(mViewPager);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	

	private void initViewPager(ViewPager paramViewPager) {
		
		ArrayList localArrayList = new ArrayList();
		
		View localView = getLayoutInflater().inflate(R.layout.monthly_num_head, null);
		Log.e("Keith", "after inflate monthly_num_head " + localView);
		LinearLayout localLinearLayout = (LinearLayout) localView
				.findViewById(R.id.monthly_num_linear);
		Log.e("Keith", "after inflate monthly_num_linear " + localLinearLayout);
		ListView localListView = (ListView) localView.findViewById(R.id.monthly_listView);
//		DurationTimeAdapter localDurationTimeAdapter;
		BMRNumberAdapter numAdapter;
		// for develop
		BMRNumberBean numberBean = new BMRNumberBean();
		BMRNumberBean.Data[] arrayOfData;
		arrayOfData = numberBean.constructArrays(12);

        int i = 0;
        for (i=0;i<12;i++) {
        	arrayOfData[i] = numberBean.new Data("test1", 12, 34.5);
        }
        numberBean.setDatas(arrayOfData);
		numAdapter = new BMRNumberAdapter(this, numberBean);
		localListView.setAdapter(numAdapter);
//		if (i == 0) {
//			Log.e("Keith", "i == 0 ");
//			localTextView1.setText(2131230812);
//			localTextView2.setText(this.durationTimeBean.getAverage());
//			localDurationTimeAdapter = new DurationTimeAdapter(this,
//					this.durationTimeBean);
//			DurationTimeBean localDurationTimeBean2 = this.durationTimeBean;
			int[] arrayOfInt = new int[1];
			arrayOfInt[0] = Color.parseColor("#45b7ff");
			String[] arrayOfString = new String[1];
			arrayOfString[0] = getString(R.string.add); // TODO for test
			setStackedBarChart(localLinearLayout, arrayOfInt, arrayOfString);
//		}
//		while (true) {
//			localListView.setAdapter(localDurationTimeAdapter);
//			localArrayList.add(localView);
//			i++;
//			break;
//			localTextView1.setText(2131230813);
//			((TextView) localView.findViewById(2131361820)).setText(2131230867);
//			localTextView2.setText(this.dayDurationTimeBean.getAverage());
//			localDurationTimeAdapter = new DurationTimeAdapter(this,
//					this.dayDurationTimeBean);
//			DurationTimeBean localDurationTimeBean1 = this.dayDurationTimeBean;
//			int[] arrayOfInt1 = Constants.SINGLE_COLORS;
//			String[] arrayOfString1 = new String[1];
//			arrayOfString1[0] = getString(2131230801);
//			BitmapManager.setBarChart(localLinearLayout,
//					localDurationTimeBean1, arrayOfInt1, arrayOfString1);
//		}
		
		localArrayList.add(localView);
		
		paramViewPager.setAdapter(new BMRStatisticsPagerAdapter(localArrayList));
		paramViewPager.setCurrentItem(0);
		
	}
	


	private XYMultipleSeriesDataset getBarDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 12;
		Random r = new Random();
		for (int i = 0; i < 1; i++) {
			CategorySeries series = new CategorySeries("Demo series " + (i + 1));
			for (int k = 0; k < nr; k++) {
				series.add(100 + r.nextInt() % 100);
			}
			dataset.addSeries(series.toXYSeries());
		}
		return dataset;
	}

	public void setStackedBarChart(LinearLayout paramLinearLayout,
			int[] paramArrayOfInt, String[] paramArrayOfString) {
		XYMultipleSeriesRenderer localXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
		localXYMultipleSeriesRenderer
				.setAxisTitleTextSize((float) (BMRApplication.width / 42.667000000000002D));
		localXYMultipleSeriesRenderer
				.setChartTitleTextSize((float) (BMRApplication.width / 42.667000000000002D));
		localXYMultipleSeriesRenderer
				.setLabelsTextSize((float) (BMRApplication.width / 42.667000000000002D));
		localXYMultipleSeriesRenderer
				.setLegendTextSize((float) (BMRApplication.width / 42.667000000000002D));
		localXYMultipleSeriesRenderer.setInScroll(true);
		int i = paramArrayOfInt.length;
		for (int j = 0;; j++) {
			if (j >= i) {
				localXYMultipleSeriesRenderer.setXLabelsColor(0);
				localXYMultipleSeriesRenderer.setYLabelsColor(0, 0);
				localXYMultipleSeriesRenderer.setAxesColor(0);
				localXYMultipleSeriesRenderer.setBackgroundColor(Color.argb(0,
						255, 0, 0));
				localXYMultipleSeriesRenderer.setMarginsColor(Color.argb(0,
						255, 0, 0));
				localXYMultipleSeriesRenderer.setXLabels(3);
				localXYMultipleSeriesRenderer.setYLabels(10);
				localXYMultipleSeriesRenderer
						.setXLabelsAlign(Paint.Align.CENTER);
				localXYMultipleSeriesRenderer.setYLabelsAlign(Paint.Align.LEFT);
				localXYMultipleSeriesRenderer.setPanEnabled(false, false);
				localXYMultipleSeriesRenderer.setClickEnabled(true);
				localXYMultipleSeriesRenderer.setBarSpacing(1.618000030517578D);
				localXYMultipleSeriesRenderer.setShowLabels(false);
				paramLinearLayout.addView(ChartFactory.getBarChartView(this,
						getBarDemoDataset(), localXYMultipleSeriesRenderer,
						BarChart.Type.DEFAULT), new ViewGroup.LayoutParams(-1,
						-1));
				return;
			}
			SimpleSeriesRenderer localSimpleSeriesRenderer = new SimpleSeriesRenderer();
			localSimpleSeriesRenderer.setColor(paramArrayOfInt[j]);
			localXYMultipleSeriesRenderer
					.addSeriesRenderer(localSimpleSeriesRenderer);
		}
	}

}
