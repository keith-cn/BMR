package com.hackathon.babymedicalrecord;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class BMRDatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "BMRDatabaseHelper";

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 2;

	public BMRDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public BMRDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserTable.TABLE_NAME + " ("
                + UserTable._ID + " INTEGER PRIMARY KEY,"
                + UserTable.COLUMN_NAME_DATA + " TEXT"
                + ");");
		
	}


    /**
     *
     * Demonstrates that the provider must consider what happens when the
     * underlying datastore is changed. In this sample, the database is upgraded the database
     * by destroying the existing data.
     * A real application should upgrade the database in place.
     */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Logs that the database is being upgraded
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // Kills the table and existing data
        db.execSQL("DROP TABLE IF EXISTS notes");

        // Recreates the database with a new version
        onCreate(db);
		
	}
	

    /**
     * Definition of the contract for the main table of our provider.
     */
    public static final class UserTable implements BaseColumns {

        // This class cannot be instantiated
        private UserTable() {}

        /**
         * The table name offered by this provider
         */
        public static final String TABLE_NAME = "main";
        
        /**
         * Column name for the single column holding our data.
         * <P>Type: TEXT</P>
         */
        public static final String COLUMN_NAME_DATA = "data";
    }

}
