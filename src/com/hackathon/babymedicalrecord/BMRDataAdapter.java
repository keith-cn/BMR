package com.hackathon.babymedicalrecord;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Adapter to bind a BMRData List to a view
 */
public class BMRDataAdapter extends ArrayAdapter<BMRData> {

	/**
	 * Adapter context
	 */
	Context mContext;

	/**
	 * Adapter View layout
	 */
	int mLayoutResourceId;


	public BMRDataAdapter(Context context, int layoutResourceId) {
		super(context, layoutResourceId);

		mContext = context;
		mLayoutResourceId = layoutResourceId;
	}

	/**
	 * Returns the view for a specific item on the list
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		final BMRData currentItem = getItem(position);

		if (row == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			row = inflater.inflate(mLayoutResourceId, parent, false);
		}

		row.setTag(currentItem);
		final TextView text1 = (TextView) row.findViewById(android.R.id.text1);
		final TextView text2 = (TextView) row.findViewById(android.R.id.text2);
		
		text1.setText(currentItem.getSymptomName());
		text1.setText(currentItem.getSymptomTemperature());
		
//		final CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBMRData);
//		checkBox.setText(currentItem.getText());
//		checkBox.setChecked(false);
//		checkBox.setEnabled(true);
//
//		checkBox.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
////				if (checkBox.isChecked()) {
////					checkBox.setEnabled(false);
////					if (mContext instanceof ToDoActivity) {
////						ToDoActivity activity = (ToDoActivity) mContext;
////						activity.checkItem(currentItem);
////					}
////				}
//			}
//		});

		return row;
	}

}
