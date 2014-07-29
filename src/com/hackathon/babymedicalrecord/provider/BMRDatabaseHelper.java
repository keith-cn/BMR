package com.hackathon.babymedicalrecord.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class BMRDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "BMRDatabaseHelper";

    private static final String DATABASE_NAME = "bmr.db";
    private static final int DATABASE_VERSION = 1;
//    public static final String BMR_USER_TABLE = "user";
//    public static final String BMR_BABIES_TABLE = "baby";
//    public static final String BMR_RECORD_RAW_TABLE = "raw_record";
//    public static final String BMR_RECORD_DATA_TABLE = "data";

    public BMRDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create bmr database, version is: " + DATABASE_VERSION);
        db.execSQL("CREATE TABLE " + BMR.User.TABLE_NAME + " (" + 
        		BMR.User._ID + " INTEGER PRIMARY KEY," + // TODO: Whether or not use AUTOINCREMENT flag
        		BMR.User.COLUMN_NAME_ACCOUNT + " TEXT UNIQUE," + 
        		BMR.User.COLUMN_NAME_PASSWORD + " TEXT," +
        		BMR.User.COLUMN_NAME_PHOTO_ID + " INTEGER REFERENCES data(_id)," +
        		BMR.User.COLUMN_NAME_BABY_ID + " INTEGER REFERENCES raw_babies(_id)," +
        		BMR.User.COLUMN_NAME_LAST_UPDATED_TIMESTAMP + " INTEGER" +
        		");");
        
        // TODO: Create other tables
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrade bmr database, oldVersion: " + oldVersion + " newVersion: " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + BMR.User.TABLE_NAME + ";");
        onCreate(db);
    }

}
