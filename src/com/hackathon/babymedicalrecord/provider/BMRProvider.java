package com.hackathon.babymedicalrecord.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class BMRProvider extends ContentProvider {
    private static final String TAG = "BMRProvider";

    private static final int BMR_USER = 0;
    private static final int BMR_USER_ID = 1;
    private static final int BMR_USER_ACCOUNT = 2;

    private BMRDatabaseHelper mOpenHelper;
    private ContentResolver mContentResolver;
    private static final UriMatcher sUriMatcher;

    static {
        // Creates and initializes the URI matcher
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Add patterns for user table
        sUriMatcher.addURI(BMR.AUTHORITY, BMR.User.PATH_USER, BMR_USER);
        sUriMatcher.addURI(BMR.AUTHORITY, BMR.User.PATH_USER_ID + "/#", BMR_USER_ID);
        sUriMatcher.addURI(BMR.AUTHORITY, BMR.User.PATH_ACCOUNT + "/*", BMR_USER_ACCOUNT);
        
        // TODO: For other tables
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate()");
        mOpenHelper = new BMRDatabaseHelper(getContext());
        mContentResolver = getContext().getContentResolver();
        return true;
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        Log.i(TAG, "delete(), uri:" + uri + " where:" + where + " whereArgs:" + whereArgs);
        int count;
        String finalWhere;

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {

        // If the incoming pattern matches the general pattern for user table,
        // does a delete based on the incoming "where" columns and arguments.
        case BMR_USER:
            count = db.delete(BMR.User.TABLE_NAME, where, whereArgs);
            if (count > 0) {
                mContentResolver.notifyChange(BMR.User.CONTENT_URI, null);
            }
            break;

        // If the incoming URI matches a single user ID, does the
        // delete based on the incoming data, but modifies the "where" clause to
        // restrict it to the particular ID.
        case BMR_USER_ID:
            finalWhere = BMR.User._ID + " = " + uri.getPathSegments().get(1);

            // If there were additional selection criteria, append them to the
            // final WHERE clause
            if (where != null) {
                finalWhere = finalWhere + " AND " + where;
            }

            count = db.delete(BMR.User.TABLE_NAME, finalWhere, whereArgs);
            if (count > 0) {
                mContentResolver.notifyChange(BMR.User.CONTENT_URI, null);
            }
            break;

        // If the incoming URI matches a single blocking number, does the
        // delete based on the incoming data, but modifies the "where" clause to
        // restrict it to the particular number.
        case BMR_USER_ACCOUNT:
            finalWhere = BMR.User.COLUMN_NAME_ACCOUNT + " = " + uri.getPathSegments().get(1);

            // If there were additional selection criteria, append them to the
            // final WHERE clause
            if (where != null) {
                finalWhere = finalWhere + " AND " + where;
            }

            count = db.delete(BMR.User.TABLE_NAME, finalWhere, whereArgs);
            Log.i(TAG, "delete(), count:" + count);
            if (count > 0) {
                mContentResolver.notifyChange(BMR.User.CONTENT_URI, null);
            }
            break;

        default:
            throw new UnsupportedOperationException("Cannot delete that URL: " + uri);
        }

        return count;
    }

    @Override
    public String getType(Uri uri) {
        Log.i(TAG, "getType(), uri:" + uri);
        switch (sUriMatcher.match(uri)) {
        case BMR_USER:
            return BMR.User.CONTENT_TYPE;

        case BMR_USER_ID:
        case BMR_USER_ACCOUNT:
            return BMR.User.CONTENT_ITEM_TYPE;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        Log.i(TAG, "insert(), uri:" + uri + " initialValues:" + initialValues);
        ContentValues values;

        // TODO: This is just checking the user URI, need to add other check later.
        if (sUriMatcher.match(uri) != BMR_USER) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (initialValues == null || initialValues.containsKey(BMR.User.COLUMN_NAME_ACCOUNT) == false)
            return null;
        
        if (initialValues.getAsString(BMR.User.COLUMN_NAME_ACCOUNT) == null)
            return null;

        values = new ContentValues(initialValues);

        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // Performs the insert and returns the ID of the new item.
        long rowId = db.insert(BMR.User.TABLE_NAME, null, values);
        Log.i(TAG, "insert(), rowId:" + rowId);

        // If the insert succeeded, the row ID exists.
        if (rowId > 0) {
            Uri resultUri = ContentUris.withAppendedId(BMR.User.CONTENT_URI, rowId);

            // Notifies observers registered against this provider that the data
            // changed.
            mContentResolver.notifyChange(BMR.User.CONTENT_URI, null);
            // Here we use general uri, may be we can use actual uri to replace.
            return resultUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.i(TAG, "query(), uri:" + uri + "projection:" + projection + " selection:" + selection + " selectionArgs:"
                + selectionArgs);

        // Constructs a new query builder and sets its table name
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(BMR.User.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
        // The incoming URI is for all the items
        case BMR_USER:
            break;

        // If the incoming URI is for a single item identified by its ID,
        // appends "_ID = <ID>" to the where clause, so that it selects that
        // single item
        case BMR_USER_ID:
            qb.appendWhere(BMR.User._ID + "=" + uri.getPathSegments().get(1));
            break;

        // If the incoming URI is for a single item identified by its account name,
        // appends "account = <account name>" to the where clause, so that it selects
        // that single item
        case BMR_USER_ACCOUNT:
            qb.appendWhere(BMR.User.COLUMN_NAME_ACCOUNT + "=" + uri.getPathSegments().get(1));
            break;

        default:
            // If the URI doesn't match any of the known patterns, throw an exception.
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        String orderBy;
        // If no sort order is specified, uses the default
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = BMR.User.DEFAULT_SORT_ORDER;
        } else {
            // otherwise, uses the incoming sort order
            orderBy = sortOrder;
        }

        // Opens the database object in "read" mode, since no writes need to be done.
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        // Performs the query. If no problems occur trying to read the database,
        // then a Cursor object is returned; otherwise, the cursor variable
        // contains null. If no records were selected, then the Cursor object is
        // empty, and Cursor.getCount() returns 0.
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        Log.i(TAG, "query(), c:" + c);

        // Tells the Cursor what URI to watch, so it knows when its source data changes
        c.setNotificationUri(mContentResolver, uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        Log.i(TAG, "update(), uri:" + uri + " where:" + where + " whereArgs:" + whereArgs);
        // Opens the database object in "write" mode.
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        String finalWhere;

        switch (sUriMatcher.match(uri)) {
        case BMR_USER:
            count = db.update(BMR.User.TABLE_NAME, values, where, whereArgs);
            break;

        case BMR_USER_ID:
            // Starts creating the final WHERE clause by restricting it to the
            // incoming item ID.
            finalWhere = BMR.User._ID + " = " + uri.getPathSegments().get(1);

            // If there were additional selection criteria, append them to the
            // final WHERE clause
            if (where != null) {
                finalWhere = finalWhere + " AND " + where;
            }

            // Does the update and returns the number of rows updated.
            count = db.update(BMR.User.TABLE_NAME, values, finalWhere, whereArgs);
            break;

        case BMR_USER_ACCOUNT:
            // Starts creating the final WHERE clause by restricting it to the
            // incoming account name.
            finalWhere = BMR.User.COLUMN_NAME_ACCOUNT + " = " + uri.getPathSegments().get(1);

            // If there were additional selection criteria, append them to the
            // final WHERE clause
            if (where != null) {
                finalWhere = finalWhere + " AND " + where;
            }

            // Does the update and returns the number of rows updated.
            count = db.update(BMR.User.TABLE_NAME, values, finalWhere, whereArgs);
            break;

        default:
            // If the URI doesn't match any of the known patterns, throw an
            // exception.
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    public BMRDatabaseHelper getOpenHelperForTest() {
        return mOpenHelper;
    }
}
