package com.hackathon.babymedicalrecord;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BMRNumberAdapter extends BaseAdapter {
	private Context context;
	private BMRNumberBean.Data[] datas;

	public BMRNumberAdapter(Context paramContext, BMRNumberBean numberBean) {
		context = paramContext;
		int i = 0;
		BMRNumberBean nubBean = new BMRNumberBean();

		Log.e("Keith", "BMRNumberAdapter constructor: "
				+ numberBean.getDatas().length);
		if ((numberBean != null) && (numberBean.getDatas() != null)) {
			datas = numberBean.constructArrays(numberBean.getDatas().length);

			Log.e("Keith", "BMRNumberAdapter foreach, datas: " + datas);
			for (BMRNumberBean.Data data : numberBean.getDatas()) {
				datas[i] = nubBean.new Data();
				Log.e("Keith", "BMRNumberAdapter foreach, data " + data);
				Log.e("Keith", "BMRNumberAdapter foreach, datas[i].key "
						+ datas[i].key);
				datas[i].key = data.key;
				datas[i].num = data.num;
				datas[i].percent = data.percent;
				i++;
			}
		}
	}

	public int getCount() {
		if (datas == null)
			return 0;

		Log.e("Keith", "BMRNumberAdapter getCount, data length: "
				+ datas.length);
		return datas.length;
	}

	public Object getItem(int paramInt) {
		if ((datas == null) || (paramInt >= datas.length))
			return null;
		return datas[paramInt];
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		View localView = paramView;
		ViewHolder localViewHolder;

		Log.e("Keith", "BMRNumberAdapter getView, paramInt: " + paramInt);
		Log.e("Keith", "BMRNumberAdapter getView, paramView: " + paramView);
		Log.e("Keith", "BMRNumberAdapter getView, paramViewGroup: "
				+ paramViewGroup);

		Log.e("Keith", "BMRNumberAdapter getView, datas[paramInt].key: "
				+ datas[paramInt].key);
		Log.e("Keith", "BMRNumberAdapter getView, datas[paramInt].num: "
				+ datas[paramInt].num);
		Log.e("Keith", "BMRNumberAdapter getView, datas[paramInt].percent: "
				+ datas[paramInt].percent);

		if (localView == null) {
			localView = LayoutInflater.from(context).inflate(
					R.layout.statistics_listitem, null);
			localViewHolder = new ViewHolder();
			localViewHolder.titleTextView = ((TextView) localView
					.findViewById(R.id.title));
			localViewHolder.numberTextView = ((TextView) localView
					.findViewById(R.id.number));
			localViewHolder.percentageTextView = ((TextView) localView
					.findViewById(R.id.percentage));
			localView.setTag(localViewHolder);
			// while (true)
			// {
			localViewHolder.titleTextView.setText(datas[paramInt].key);
			localViewHolder.numberTextView.setText(String
					.valueOf(datas[paramInt].num));
			localViewHolder.percentageTextView.setText(String
					.valueOf(datas[paramInt].percent) + "%");

			// localViewHolder.titleTextView.setText("ttttt");
			// localViewHolder.numberTextView.setText("12");
			// localViewHolder.percentageTextView.setText(123 + "%");
			localViewHolder = (ViewHolder) localView.getTag();
			return localView;
			// }
		}
		return localView;
	}

	static class ViewHolder {
		TextView titleTextView;
		TextView numberTextView;
		TextView percentageTextView;
	}
}
