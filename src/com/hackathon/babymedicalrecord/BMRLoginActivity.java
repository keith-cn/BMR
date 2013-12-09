package com.hackathon.babymedicalrecord;

import static com.microsoft.windowsazure.mobileservices.MobileServiceQueryOperations.val;

import java.net.MalformedURLException;
import java.util.List;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponseCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
import com.umeng.analytics.MobclickAgent;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BMRLoginActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "BMRLoginActivity";

	public static String userName;
	private MobileServiceClient mClient;
	private MobileServiceTable<BMRUser> mBMRUser;

	private TextView mName;
	private TextView mPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//
		// try {
		// // Create the Mobile Service Client instance, using the provided
		// // Mobile Service URL and key
		// mClient = new MobileServiceClient(BMRUtil.SERVICE_URL,
		// "HiboUWjGJdFYYBHsmWUYfaSPvjtdZZ89", this)
		// .withFilter(new ProgressFilter());
		//
		// // Get the Mobile Service Table instance to use
		// mBMRUser = mClient.getTable(BMRUser.class);
		//
		// } catch (MalformedURLException e) {
		// BMRUtil.createAndShowDialog(
		// new Exception(
		// "There was an error creating the Mobile Service. Verify the URL"),
		// "Error", this);
		// }

		mClient = BMRUtil.getMobileService(this, new ProgressFilter());
		mBMRUser = BMRUtil.getBMRUserTable();

		mName = (TextView) findViewById(R.id.login_account);
		mPassword = (TextView) findViewById(R.id.login_password);
		// add button listener
		((Button) findViewById(R.id.login_btn)).setOnClickListener(this);
		((Button) findViewById(R.id.register_btn)).setOnClickListener(this);

		// hide the action bar icon
		// getActionBar().setIcon(R.drawable.fake_action_icon);
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			getActionBar().setDisplayShowHomeEnabled(false);
		}
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

		if (v.getId() == R.id.login_btn) {
			if (mName.getText().toString() == null
					|| mPassword.getText().toString() == null) {
				BMRUtil.createAndShowDialog("Wrong user name or password",
						"Babay Medical Record", this);
				return;
			}

			verifyUser();

		} else if (v.getId() == R.id.register_btn) {
			// start Activity
			Intent i = new Intent(this, BMRUserRegisterActivity.class);
			startActivity(i);

			// close self
			finish();
		}

	}

	/**
	 * Refresh the list with the items in the Mobile Service Table
	 */
	private void verifyUser() {

		// Get the items that weren't marked as completed and add them in the
		// adapter
		mBMRUser.where().field("name").eq(val(mName.getText().toString()))
				.and().field("password").eq(mPassword.getText().toString())
				.execute(new TableQueryCallback<BMRUser>() {

					public void onCompleted(List<BMRUser> result, int count,
							Exception exception, ServiceFilterResponse response) {
						if (exception == null) {

							Log.e("Keith", "exception null, " + count);
							for (BMRUser user : result) {
								Log.e("Keith", user.toString());
								Log.e("Keith", "getName() " + user.getName());
								Log.e("Keith",
										"getPassword() " + user.getPassword());
								if (user.getName().equals(
										mName.getText().toString()) == true
										&& user.getPassword().equals(
												mPassword.getText().toString()) == true) {
									userName = user.getName();
									// start Activity
									Intent i = new Intent(
											BMRLoginActivity.this,
											BMRMainActivity.class);
									startActivity(i);
									return;
								}
							}

							BMRUtil.createAndShowDialog(
									"Wrong user name or password",
									"Babay Medical Record",
									BMRLoginActivity.this);

							// close self
							// finish();
						} else {
							BMRUtil.createAndShowDialog(exception, "Error",
									BMRLoginActivity.this);
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
