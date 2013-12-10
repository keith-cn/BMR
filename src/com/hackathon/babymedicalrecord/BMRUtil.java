package com.hackathon.babymedicalrecord;

import java.net.MalformedURLException;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;

public class BMRUtil {

	public static MobileServiceClient mClient;
	public final static String SERVICE_URL = "https://babymedicalrecord.azure-mobile.net/";
	public static MobileServiceTable<BMRUser> mBMRUser;
	public static MobileServiceTable<BMRData> mBMRData;
	public static final int[] COLORS;

	static {
		int[] colors = new int[10];
		colors[0] = Color.parseColor("#4ec0c3");
		colors[1] = Color.parseColor("#dee2e5");
		colors[2] = Color.parseColor("#ab497f");
		colors[3] = Color.parseColor("#ef8032");
		colors[4] = Color.parseColor("#fed246");
		colors[5] = Color.parseColor("#393b38");
		colors[6] = Color.parseColor("#75d3ff");
		colors[7] = Color.parseColor("#ffffff");
		colors[8] = Color.parseColor("#ffffff");
		colors[9] = Color.parseColor("#ffffff");
		COLORS = colors;
	}

	public static MobileServiceClient getMobileService(Context context,
			ServiceFilter filter) {

		try {
			// Create the Mobile Service Client instance, using the provided
			// Mobile Service URL and key
			if (mClient == null) {
				mClient = new MobileServiceClient(SERVICE_URL,
						"HiboUWjGJdFYYBHsmWUYfaSPvjtdZZ89", context)
						.withFilter(filter);
			}
		} catch (MalformedURLException e) {
			BMRUtil.createAndShowDialog(
					new Exception(
							"There was an error creating the Mobile Service. Verify the URL"),
					"Error", context);
		}
		return mClient;
	}

	public static <E> MobileServiceTable<E> getMobileTable(
			MobileServiceClient client, Class<E> clazz) {

		if (mClient == null) {
			return null;
		}
		// Get the Mobile Service Table instance to use
		return client.getTable(clazz);
	}

	public static MobileServiceTable<BMRUser> getBMRUserTable() {
		if (mClient == null) {
			return null;
		}

		mBMRUser = getMobileTable(mClient, BMRUser.class);
		return mBMRUser;
	}

	public static MobileServiceTable<BMRData> getBMRDataTable() {
		if (mClient == null) {
			return null;
		}

		mBMRData = getMobileTable(mClient, BMRData.class);
		return mBMRData;
	}

	/**
	 * Creates a dialog and shows it
	 * 
	 * @param exception
	 *            The exception to show in the dialog
	 * @param title
	 *            The dialog title
	 */
	public static void createAndShowDialog(Exception exception, String title,
			Context context) {
		Throwable ex = exception;
		if (exception.getCause() != null) {
			ex = exception.getCause();
		}
		createAndShowDialog(ex.getMessage(), title, context);
	}

	/**
	 * Creates a dialog and shows it
	 * 
	 * @param message
	 *            The dialog message
	 * @param title
	 *            The dialog title
	 */
	public static void createAndShowDialog(String message, String title,
			Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setMessage(message);
		builder.setTitle(title);
		builder.create().show();
	}

}
