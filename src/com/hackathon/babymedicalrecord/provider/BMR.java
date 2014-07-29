package com.hackathon.babymedicalrecord.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public final class BMR {
    public static final String AUTHORITY = "com.mwind.bmr";

    private BMR() {
    }

    public static final String SCHEME = "content://";

    public static final class User implements BaseColumns {

        private User() {
        }

        public static final String TABLE_NAME = "user";
        public static final String PATH_USER = "/user";
        public static final String PATH_USER_ID = "/user_id";
        public static final String PATH_ACCOUNT = "/user_account";

        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_USER);
        public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY
                + PATH_USER_ID + "/#");
        public static final Uri CONTENT_ACCOUNT_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY
                + PATH_ACCOUNT + "/*");

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/user";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/user";

        /**
         * The default sort order for this table
         */
        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String COLUMN_NAME_ACCOUNT = "account";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_PHOTO_ID = "photo_id";
        public static final String COLUMN_NAME_BABY_ID = "baby";
        public static final String COLUMN_NAME_LAST_UPDATED_TIMESTAMP = "last_updated_timestamp";

        public static final String[] PROJECTION = new String[] { _ID, COLUMN_NAME_ACCOUNT,
                COLUMN_NAME_PASSWORD };
    }

    // TODO: Other table enum values
}
