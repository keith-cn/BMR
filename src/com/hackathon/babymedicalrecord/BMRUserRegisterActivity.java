package com.hackathon.babymedicalrecord;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.val;

import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
import com.umeng.analytics.MobclickAgent;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

public class BMRUserRegisterActivity extends Activity implements
		View.OnClickListener {

	/**
	 * Mobile Service Client reference
	 */
	private MobileServiceClient mClient;

	/**
	 * Mobile Service Table used to access data
	 */
	private MobileServiceTable<BMRUser> mBMRUser;

	private Calendar noteCalendar;
	private EditText mUserName;
	private EditText mPassword;
	private EditText mBabyName;
	private RadioGroup mGender;
	private Button mBirthday;
	private int mBabyYear;
	private int mBabyMonth;
	private int mBabyDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		noteCalendar = Calendar.getInstance();

		// add button listener
		((Button) findViewById(R.id.reg_ok)).setOnClickListener(this);
		mUserName = (EditText) findViewById(R.id.reg_name_edit);
		mPassword = (EditText) findViewById(R.id.reg_password_edit);
		mBabyName = (EditText) findViewById(R.id.reg_baby_name_edit);
		mGender = (RadioGroup) findViewById(R.id.reg_gender);
		mBirthday = (Button) findViewById(R.id.reg_birthday_btn);
		setBirthdayListener(mBirthday);

		try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			mClient = new MobileServiceClient(
					BMRUtil.SERVICE_URL,
					"HiboUWjGJdFYYBHsmWUYfaSPvjtdZZ89", this)
					.withFilter(new ProgressFilter());

			// Get the Mobile Service Table instance to use
			mBMRUser = mClient.getTable(BMRUser.class);

		} catch (MalformedURLException e) {
			BMRUtil.createAndShowDialog(
					new Exception(
							"There was an error creating the Mobile Service. Verify the URL"),
					"Error", this);
		}
		

//        mClient = BMRUtil.getMobileService(this, null);
//        mBMRUser = BMRUtil.getBMRUserTable(this, null);

		// hide the action bar icon
		// getActionBar().setIcon(R.drawable.fake_action_icon);
		// ActionBar actionBar = getActionBar();
		// if (actionBar != null) {
		// getActionBar().setDisplayShowHomeEnabled(false);
		// }
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.reg_ok) {
			if (mClient == null) {
				return;
			}

			// Create a new user
			BMRUser user = new BMRUser();
			user.setName(mUserName.getText().toString());
			user.setPassword(mPassword.getText().toString());
			user.setBabyName(mBabyName.getText().toString());
			if (mGender.getCheckedRadioButtonId() == R.id.reg_boy) {
				user.setGender(true);
			} else {
				user.setGender(false);
			}
			user.setBabyYear(mBabyYear);
			user.setBabyMonth(mBabyMonth);
			user.setBabyDay(mBabyDay);

			// Insert the new item
			mBMRUser.insert(user, new TableOperationCallback<BMRUser>() {

				public void onCompleted(BMRUser entity, Exception exception,
						ServiceFilterResponse response) {

					if (exception == null) {
						BMRUtil.createAndShowDialog("insert done",
								"Baby Medical Record", BMRUserRegisterActivity.this);
					} else {
						BMRUtil.createAndShowDialog(exception, "Error", BMRUserRegisterActivity.this);
					}

				}
			});

			refreshItemsFromTable();
		} else if (v.getId() == R.id.reg_birthday_btn) {
		}

	}

	private void setBirthdayListener(View v) {
		v.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				new DatePickerDialog(
						BMRUserRegisterActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							public void onDateSet(
									DatePicker paramAnonymous2DatePicker,
									int paramAnonymous2Int1,
									int paramAnonymous2Int2,
									int paramAnonymous2Int3) {
								mBabyYear = paramAnonymous2Int1;
								mBabyMonth = paramAnonymous2Int2 + 1;
								mBabyDay = paramAnonymous2Int3;

								BMRUserRegisterActivity.this.noteCalendar.set(
										paramAnonymous2Int1,
										paramAnonymous2Int2,
										paramAnonymous2Int3);
								Locale.setDefault(Locale.CHINA);
								BMRUserRegisterActivity.this.mBirthday.setText(BMRUserRegisterActivity.this.noteCalendar
										.get(1)
										+ "-"
										+ (1 + BMRUserRegisterActivity.this.noteCalendar
												.get(2))
										+ "-"
										+ BMRUserRegisterActivity.this.noteCalendar
												.get(5)
										+ " "
										+ BMRUserRegisterActivity.this
												.getWeek(BMRUserRegisterActivity.this.noteCalendar
														.get(7)));
							}
						}, BMRUserRegisterActivity.this.noteCalendar.get(1),
						BMRUserRegisterActivity.this.noteCalendar.get(2),
						BMRUserRegisterActivity.this.noteCalendar.get(5))
						.show();
			}
		});
	}

	public String getWeek(int paramInt) {
		return new String[] { "", "Sun", "Mon", "Tue", "Wen", "Thr", "Fri",
				"Sat" }[paramInt];
	}

	/**
	 * Refresh the list with the items in the Mobile Service Table
	 */
	private void refreshItemsFromTable() {

		// Get the items that weren't marked as completed and add them in the
		// adapter
		mBMRUser.where().field("complete").eq(val(false))
				.execute(new TableQueryCallback<BMRUser>() {

					public void onCompleted(List<BMRUser> result, int count,
							Exception exception, ServiceFilterResponse response) {
						if (exception == null) {

						} else {
							BMRUtil.createAndShowDialog(exception, "Error", BMRUserRegisterActivity.this);
						}
					}
				});
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
}
