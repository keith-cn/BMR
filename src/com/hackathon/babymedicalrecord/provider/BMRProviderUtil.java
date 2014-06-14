package com.hackathon.babymedicalrecord.provider;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

// Utility class for access BMR database
public class BMRProviderUtil {
	private static final String TAG = "BMRProviderUtil";

	public static final class User {
		public static final Uri UserUri = BMR.User.CONTENT_URI;

		public static class UserItem {
			public long id;
			public String account;
			public String password;
		}

		// Standard projection for the interesting columns of a normal item.
		private static final String[] READ_ITEM_PROJECTION = new String[] {
				BMR.User._ID, BMR.User.COLUMN_NAME_ACCOUNT,
				BMR.User.COLUMN_NAME_PASSWORD };

		public static Uri insertItem(Context context, String accountName,
				String password) {
			if (accountName == null || password == null) {
				return null;
			}

			ContentValues values = new ContentValues();
			values.put(BMR.User.COLUMN_NAME_ACCOUNT, accountName);
			values.put(BMR.User.COLUMN_NAME_PASSWORD, password);

			Uri result = context.getContentResolver().insert(UserUri, values);
			return result;
		}

		public static boolean deleteItemById(Context context, long id) {
			int result = context.getContentResolver().delete(UserUri,
					BMR.User._ID + "=?", new String[] { String.valueOf(id) });
			if (result > 0)
				return true;
			return false;
		}

		public static boolean deleteItemByAccountName(Context context,
				String account) {
			int result = context.getContentResolver().delete(UserUri,
					BMR.User.COLUMN_NAME_ACCOUNT + "=?",
					new String[] { account });
			if (result > 0)
				return true;
			return false;
		}

		public static boolean deleteAllItems(Context context) {
			int result = context.getContentResolver().delete(UserUri, null,
					null);
			if (result > 0)
				return true;
			return false;
		}

		public static UserItem getItemById(Context context, long id) {
			String selection = BMR.User._ID + "=?";
			String[] selectionArgs = new String[] { String.valueOf(id) };
			String sortOrder = null;

			Cursor c = context.getContentResolver().query(UserUri,
					READ_ITEM_PROJECTION, selection, selectionArgs, sortOrder);

			if (c == null) {
				Log.i(TAG, "getItemById() return null");
				return null;
			}
			try {
				if (c.getCount() == 1 && c.moveToFirst()) {
					UserItem item = new UserItem();
					int idIndex = c.getColumnIndex(BMR.User._ID);
					int accountIndex = c.getColumnIndex(BMR.User.COLUMN_NAME_ACCOUNT);
					int passwordIndex = c.getColumnIndex(BMR.User.COLUMN_NAME_PASSWORD);
					item.id = c.getLong(idIndex);
					item.account = c.getString(accountIndex);
					item.password = c.getString(passwordIndex);

					Log.i(TAG, "getItemById() return:" + item);
					return item;
				} else {
					Log.i(TAG, "getItemById() return null");
					return null;
				}
			} finally {
				c.close();
				c = null;
			}
		}

		public static UserItem getItemByAccountName(Context context, String account) {
			String selection = BMR.User.COLUMN_NAME_ACCOUNT + "=?";
			String[] selectionArgs = new String[] { account };
			String sortOrder = null;

			Cursor c = context.getContentResolver().query(UserUri,
					READ_ITEM_PROJECTION, selection, selectionArgs, sortOrder);

			if (c == null) {
				Log.i(TAG, "getItemByAccountName() return null");
				return null;
			}
			try {
				if (c.getCount() == 1 && c.moveToFirst()) {
					UserItem item = new UserItem();
					int idIndex = c.getColumnIndex(BMR.User._ID);
					int accountIndex = c.getColumnIndex(BMR.User.COLUMN_NAME_ACCOUNT);
					int passwordIndex = c.getColumnIndex(BMR.User.COLUMN_NAME_PASSWORD);
					item.id = c.getLong(idIndex);
					item.account = c.getString(accountIndex);
					item.password = c.getString(passwordIndex);

					Log.i(TAG, "getItemByAccountName() return:" + item);
					return item;
				} else {
					Log.i(TAG, "getItemByAccountName() return null");
					return null;
				}
			} finally {
				c.close();
				c = null;
			}
		}

		public static ArrayList<UserItem> getAllItems(Context context) {
			Cursor c = context.getContentResolver().query(UserUri,
					READ_ITEM_PROJECTION, null, null, null);
			if (c == null) {
				Log.i(TAG, "getAllItems() return null");
				return null;
			}
			try {
				if (c.getCount() > 0 && c.moveToFirst()) {
					ArrayList<UserItem> list = new ArrayList<UserItem>();
					UserItem item = new UserItem();
					int idIndex = c.getColumnIndex(BMR.User._ID);
					int accountIndex = c.getColumnIndex(BMR.User.COLUMN_NAME_ACCOUNT);
					int passwordIndex = c.getColumnIndex(BMR.User.COLUMN_NAME_PASSWORD);
					item.id = c.getLong(idIndex);
					item.account = c.getString(accountIndex);
					item.password = c.getString(passwordIndex);
					list.add(item); 

					while (c.moveToNext()) {
						item.id = c.getLong(idIndex);
						item.account = c.getString(accountIndex);
						item.password = c.getString(passwordIndex);
						list.add(item);
						Log.e("Keith", "getAllItems: " +item.account);
					}

					Log.i(TAG, "getAllItems() return:" + list);
					return list;
				} else {
					Log.i(TAG, "getAllItems() return null");
					return null;
				}
			} finally {
				c.close();
				c = null;
			}
		}

		public static CursorLoader getCursorLoaderForAllItems(Context context) {
			CursorLoader loader = new CursorLoader(context, UserUri,
					READ_ITEM_PROJECTION, null, null, null);
			return loader;
		}

		public static Cursor getCursorForAllItems(Context context) {
			Cursor result = context.getContentResolver().query(UserUri,
					READ_ITEM_PROJECTION, null, null, null);
			return result;
		}
	}
}
