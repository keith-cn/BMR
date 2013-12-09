package com.hackathon.babymedicalrecord;

import java.net.MalformedURLException;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilter;

import android.app.AlertDialog;
import android.content.Context;

public class BMRUtil {

	public static MobileServiceClient mClient;
	public final static String SERVICE_URL = "https://babymedicalrecord.azure-mobile.net/";
	public static MobileServiceTable<BMRUser> mBMRUser;
	public static MobileServiceTable<BMRData> mBMRData;

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

	public static <E> MobileServiceTable<E> getMobileTable(MobileServiceClient client,
			Class<E> clazz) {

		if (mClient == null) {
			return null;
		}
		// Get the Mobile Service Table instance to use
		return client.getTable(clazz);
	}
	
	public static MobileServiceTable<BMRUser> getBMRUserTable()
	{
		if (mClient == null) {
			return null;
		}
		
		mBMRUser = getMobileTable(mClient, BMRUser.class);
		return mBMRUser;
	}
	
	public static MobileServiceTable<BMRData> getBMRDataTable()
	{
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
