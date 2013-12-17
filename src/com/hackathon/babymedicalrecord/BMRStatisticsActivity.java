package com.hackathon.babymedicalrecord;

import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.chart.BarChart;

import com.umeng.analytics.MobclickAgent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class BMRStatisticsActivity extends Activity implements
		View.OnClickListener {

	private LinearLayout monthlyLinearLayout;
	private LinearLayout diseaseLinearLayout;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			initFrameView();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);

		monthlyLinearLayout = ((LinearLayout) findViewById(R.id.sta_num_monthly_linear));
		diseaseLinearLayout = ((LinearLayout) findViewById(R.id.sta_disease_liner));

		// initFrameData();
	}

	private void initFrameData() {
		// TODO start a thread and show a popup to get the data
		Message localMessage = new Message();
		handler.sendMessage(localMessage);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);

		// TODO maybe need move to handler function
		initFrameView();
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void initFrameView() {
		findViewById(R.id.monthly_linerlayout).setOnClickListener(this);
		findViewById(R.id.disease_linerlayout).setOnClickListener(this);
		initChartView();
	}

	private void initChartView() {
		// monthly chart
		int[] arrayOfInt = new int[1];
		arrayOfInt[0] = Color.parseColor("#45b7ff");
		String[] arrayOfString = new String[1];
		arrayOfString[0] = getString(R.string.add); // TODO for test
		setStackedBarChart(monthlyLinearLayout, arrayOfInt, arrayOfString);

		// illness chart
		SimpleSeriesRenderer localSimpleSeriesRenderer1 = new SimpleSeriesRenderer();
		SimpleSeriesRenderer localSimpleSeriesRenderer2 = new SimpleSeriesRenderer();
		SimpleSeriesRenderer localSimpleSeriesRenderer3 = new SimpleSeriesRenderer();
		SimpleSeriesRenderer localSimpleSeriesRenderer4 = new SimpleSeriesRenderer();
		SimpleSeriesRenderer localSimpleSeriesRenderer5 = new SimpleSeriesRenderer();
		GraphicalView mChartView;
		CategorySeries pieChartSeries = new CategorySeries("disease 1");
		DefaultRenderer pieChartRenderer = new DefaultRenderer();

		pieChartSeries.add("illness 1", 10);
		pieChartSeries.add("illness 2", 20);
		pieChartSeries.add("illness 3", 30);
		pieChartSeries.add("illness 4", 5);
		pieChartSeries.add("illness 5", 25);

		// (localAppVersion.getVersion(), new Double(
		// localAppVersion.getTotalInstallRate()).doubleValue());
		localSimpleSeriesRenderer1
				.setColor(BMRUtil.COLORS[((-1 + pieChartSeries.getItemCount()) % BMRUtil.COLORS.length)]);
		localSimpleSeriesRenderer2.setColor(BMRUtil.COLORS[((-1
				+ pieChartSeries.getItemCount() + 1) % BMRUtil.COLORS.length)]);
		localSimpleSeriesRenderer3.setColor(BMRUtil.COLORS[((-1
				+ pieChartSeries.getItemCount() + 2) % BMRUtil.COLORS.length)]);
		localSimpleSeriesRenderer4.setColor(BMRUtil.COLORS[((-1
				+ pieChartSeries.getItemCount() + 3) % BMRUtil.COLORS.length)]);
		localSimpleSeriesRenderer5.setColor(BMRUtil.COLORS[((-1
				+ pieChartSeries.getItemCount() + 4) % BMRUtil.COLORS.length)]);
		pieChartRenderer.addSeriesRenderer(localSimpleSeriesRenderer1);
		pieChartRenderer.addSeriesRenderer(localSimpleSeriesRenderer2);
		pieChartRenderer.addSeriesRenderer(localSimpleSeriesRenderer3);
		pieChartRenderer.addSeriesRenderer(localSimpleSeriesRenderer4);
		pieChartRenderer.addSeriesRenderer(localSimpleSeriesRenderer5);

		mChartView = ChartFactory.getPieChartView(this, pieChartSeries,
				pieChartRenderer);
		diseaseLinearLayout.addView(mChartView, new WindowManager.LayoutParams(
				-1, -1));
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.monthly_linerlayout) {
			// start Activity
			Intent i = new Intent(this, BMRStatisticsMonthlyActivity.class);
			startActivity(i);
		} else if (v.getId() == R.id.disease_linerlayout) {
			// start Activity
			Intent i = new Intent(this, BMRStatisticsTopActivity.class);
			startActivity(i);
		}
	}

	private XYMultipleSeriesDataset getBarDemoDataset() {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 12;
		Random r = new Random();
		for (int i = 0; i < 1; i++) {
			CategorySeries series = new CategorySeries("Number of babies "
					+ (i + 1));
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
