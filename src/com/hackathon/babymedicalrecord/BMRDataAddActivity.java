package com.hackathon.babymedicalrecord;

import java.util.Calendar;
import java.util.Locale;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class BMRDataAddActivity extends Activity implements
		View.OnClickListener {

	private MobileServiceClient mClient;
	private MobileServiceTable<BMRData> mBMRData;

	private int mSymptomYear;
	private int mSymptomMonth;
	private int mSymptomDay;
	private EditText mSymptomName;
	private EditText mSymptomTemperature;
	private EditText mHospital;
	private EditText mDoctor;
	private EditText mCost;
	private EditText mDiagnosis;
	private EditText mMedicine;
	private EditText mSymptomDetail;
	private Button mDate;

	private Calendar noteCalendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_data);

		mClient = BMRUtil.getMobileService(this, new ProgressFilter());
		mBMRData = BMRUtil.getBMRDataTable();

		findViewById(R.id.add_data_ok).setOnClickListener(this);
		mSymptomName = (EditText) findViewById(R.id.symptom_name_edit);
		mSymptomTemperature = (EditText) findViewById(R.id.temperature_edit);
		mHospital = (EditText) findViewById(R.id.hospital_edit);
		mDoctor = (EditText) findViewById(R.id.doctor_edit);
		mCost = (EditText) findViewById(R.id.cost_edit);
		mDiagnosis = (EditText) findViewById(R.id.diagnosis_edit);
		mMedicine = (EditText) findViewById(R.id.medicine_edit);
		mSymptomDetail = (EditText) findViewById(R.id.others);
		mDate = (Button) findViewById(R.id.date_btn);

		noteCalendar = Calendar.getInstance();
		setDateListener(mDate);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.add_data_ok) {
			Log.e("Keith", "add data onClick");
			// Create a new data
			BMRData data = new BMRData();

			data.setName(BMRLoginActivity.userName);
			data.setSymptomName(mSymptomName.getText().toString());
			data.setSymptomTemperature(mSymptomTemperature.getText().toString());
			data.setHospital(mHospital.getText().toString());
			data.setDoctor(mDoctor.getText().toString());
			//data.setCost(mCost.getTag().toString());
			data.setCost(100);
			data.setDiagnosis(mDiagnosis.getText().toString());
			data.setMedicine(mMedicine.getText().toString());
			data.setSymptomDetail(mSymptomDetail.getText().toString());

			// Insert the new item
			mBMRData.insert(data, new TableOperationCallback<BMRData>() {

				public void onCompleted(BMRData entity, Exception exception,
						ServiceFilterResponse response) {
					//Log.e("Keith", "onCompleted, entity: " + entity.toString());

					if (exception == null) {
						BMRUtil.createAndShowDialog("insert done",
								"Baby Medical Record", BMRDataAddActivity.this);
					} else {
						BMRUtil.createAndShowDialog(exception, "Error",
								BMRDataAddActivity.this);
					}

				}
			});

		}
	}

	private class ProgressFilter implements ServiceFilter {

		@Override
		public void handleRequest(ServiceFilterRequest request,
				NextServiceFilterCallback nextServiceFilterCallback,
				final ServiceFilterResponseCallback responseCallback) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// if (mProgressBar != null)
					// mProgressBar.setVisibility(ProgressBar.VISIBLE);
				}
			});

			nextServiceFilterCallback.onNext(request,
					new ServiceFilterResponseCallback() {

						@Override
						public void onResponse(ServiceFilterResponse response,
								Exception exception) {
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									// if (mProgressBar != null)
									// mProgressBar.setVisibility(ProgressBar.GONE);
								}
							});

							if (responseCallback != null)
								responseCallback
										.onResponse(response, exception);
						}
					});
		}
	}

	public String getWeek(int paramInt) {
		return new String[] { "", "Sun", "Mon", "Tue", "Wen", "Thr", "Fri",
				"Sat" }[paramInt];
	}

	private void setDateListener(View v) {
		v.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				new DatePickerDialog(
						BMRDataAddActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(
									DatePicker paramAnonymous2DatePicker,
									int paramAnonymous2Int1,
									int paramAnonymous2Int2,
									int paramAnonymous2Int3) {
								mSymptomYear = paramAnonymous2Int1;
								mSymptomMonth = paramAnonymous2Int2 + 1;
								mSymptomDay = paramAnonymous2Int3;

								BMRDataAddActivity.this.noteCalendar.set(
										paramAnonymous2Int1,
										paramAnonymous2Int2,
										paramAnonymous2Int3);
								Locale.setDefault(Locale.CHINA);
								BMRDataAddActivity.this.mDate.setText(BMRDataAddActivity.this.noteCalendar
										.get(1)
										+ "-"
										+ (1 + BMRDataAddActivity.this.noteCalendar
												.get(2))
										+ "-"
										+ BMRDataAddActivity.this.noteCalendar
												.get(5)
										+ " "
										+ BMRDataAddActivity.this
												.getWeek(BMRDataAddActivity.this.noteCalendar
														.get(7)));
							}
						}, BMRDataAddActivity.this.noteCalendar.get(1),
						BMRDataAddActivity.this.noteCalendar.get(2),
						BMRDataAddActivity.this.noteCalendar.get(5)).show();
			}
		});
	}

}
