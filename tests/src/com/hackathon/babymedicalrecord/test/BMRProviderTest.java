package com.hackathon.babymedicalrecord.test;

import com.hackathon.babymedicalrecord.provider.BMR;
import com.hackathon.babymedicalrecord.provider.BMRProvider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

public class BMRProviderTest extends ProviderTestCase2<BMRProvider> {

    private static final Uri INVALID_URI = Uri.withAppendedPath(BMR.User.CONTENT_URI, "invalid");

    private MockContentResolver mMockResolver;
    private SQLiteDatabase mDb;
    
    private final String[] testAccount = new String[] {
            "account001",
            "account002",
            "account003",
            "account004",
            "account005",
            "account006",
            "account007"
    };
    
    private final String[] testPassword = new String[] {
            "13500000001",
            "13500000002",
            "13500000003",
            "13500000004",
            "01085858585",
            "041178787878",
            "13666"
    };

    private final UserItem[] TEST_ITEMS = {
            new UserItem(testAccount[0], testPassword[0]),
            new UserItem(testAccount[1], testPassword[1]),
            new UserItem(testAccount[2], testPassword[2]),
            new UserItem(testAccount[3], testPassword[3]),
            new UserItem(testAccount[4], testPassword[4]),
            new UserItem(testAccount[5], testPassword[5]),
            new UserItem(testAccount[6], testPassword[6]) };

    public BMRProviderTest() {
        super(BMRProvider.class, BMR.AUTHORITY);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Gets the resolver for this test.
        mMockResolver = getMockContentResolver();

        mDb = getProvider().getOpenHelperForTest().getWritableDatabase();
    }

    /*
     * This method is called after each test method, to clean up the current
     * fixture.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private void insertData() {
        // Sets up test data
        for (int index = 0; index < TEST_ITEMS.length; index++) {
            // Adds a record to the database.
            mDb.insertOrThrow(BMR.User.TABLE_NAME, null, TEST_ITEMS[index].getContentValues());
        }
    }

    public void testUriAndGetType() {
        // Tests the MIME type for the user table URI.
        String mimeType = mMockResolver.getType(BMR.User.CONTENT_URI);
        assertEquals(BMR.User.CONTENT_TYPE, mimeType);

        // Creates a URI with a pattern for user ids. The id
        // doesn't have to exist.
        Uri listIdUri = ContentUris.withAppendedId(BMR.User.CONTENT_ID_URI_PATTERN, 1);
        mimeType = mMockResolver.getType(listIdUri);
        assertEquals(BMR.User.CONTENT_ITEM_TYPE, mimeType);

        // Creates a URI with a pattern for user account. The account
        // doesn't have to exist.
        Uri listAccountUri = Uri.withAppendedPath(BMR.User.CONTENT_ACCOUNT_URI_PATTERN, "account001");
        mimeType = mMockResolver.getType(listAccountUri);
        assertEquals(BMR.User.CONTENT_ITEM_TYPE, mimeType);

        // Tests an invalid URI. The return value is null, not IllegalArgumentException. But why?
        mimeType = mMockResolver.getType(INVALID_URI);
        assertNull(mimeType);
    }

    public void testQueries() {
        // Defines a projection of column names to return for a query
        final String[] TEST_PROJECTION = { BMR.User.COLUMN_NAME_ACCOUNT,
                BMR.User.COLUMN_NAME_PASSWORD};

        // Defines a selection column for the query. When the selection columns are passed
        // to the query, the selection arguments replace the placeholders.
        final String ACCOUNT_SELECTION = BMR.User.COLUMN_NAME_ACCOUNT + " = " + "?";

        // Defines the selection columns for a query.
        final String SELECTION_COLUMNS = ACCOUNT_SELECTION + " OR " + ACCOUNT_SELECTION + " OR " + ACCOUNT_SELECTION;

        // Defines the arguments for the selection columns.
        final String[] SELECTION_ARGS = { testAccount[0], testAccount[1], testAccount[2] };

        // Defines a query sort order
        final String SORT_ORDER = BMR.User.COLUMN_NAME_ACCOUNT + " ASC";

        // Query subtest 1.
        // If there are no records in the table, the returned cursor from a
        // query should be empty.
        Cursor cursor = mMockResolver.query(BMR.User.CONTENT_URI, null, null, null, null);

        // Asserts that the returned cursor contains no records
        assertEquals(0, cursor.getCount());

        // Query subtest 2.
        // If the table contains records, the returned cursor from a query
        // should contain records.

        // Inserts the test data into the provider's underlying data source
        insertData();

        // Gets all the columns for all the rows in the table
        cursor = mMockResolver.query(BMR.User.CONTENT_URI, null, null, null, null);

        // Asserts that the returned cursor contains the same account of rows as
        // the size of the test data array.
        assertEquals(TEST_ITEMS.length, cursor.getCount());

        // Query subtest 3.
        // A query that uses a projection should return a cursor with the same
        // account of columns as the projection, with the same names, in the same order.
        Cursor projectionCursor = mMockResolver.query(BMR.User.CONTENT_URI, TEST_PROJECTION, null, null,
                null);

        // Asserts that the account of columns in the cursor is the same as in
        // the projection
        assertEquals(TEST_PROJECTION.length, projectionCursor.getColumnCount());

        // Asserts that the names of the columns in the cursor and in the
        // projection are the same.
        // This also verifies that the names are in the same order.
        assertEquals(TEST_PROJECTION[0], projectionCursor.getColumnName(0));
        assertEquals(TEST_PROJECTION[1], projectionCursor.getColumnName(1));

        // Query subtest 4
        // A query that uses selection criteria should return only those rows that match the
        // criteria. Use a projection so that it's easy to get the data in a particular column.
        projectionCursor = mMockResolver.query(BMR.User.CONTENT_URI, TEST_PROJECTION, SELECTION_COLUMNS,
                SELECTION_ARGS, SORT_ORDER);

        // Asserts that the cursor has the same account of rows as the account of
        // selection arguments
        assertEquals(SELECTION_ARGS.length, projectionCursor.getCount());

        int index = 0;

        while (projectionCursor.moveToNext()) {
            // Asserts that the selection argument at the current index matches the value of
            // the title column (column 0) in the current record of the cursor
            assertEquals(SELECTION_ARGS[index], projectionCursor.getString(0));

            index++;
        }

        // Asserts that the index pointer is now the same as the account of selection arguments, so
        // that the account of arguments tested is exactly the same as the account of rows returned.
        assertEquals(SELECTION_ARGS.length, index);
    }

    public void testQueriesOnIdUri() {
        // TODO: Complete this part later.
    }

    public void testInserts() {
        // Creates a new item
        UserItem item = new UserItem("account12345", "password12345");

        Uri rowUri = mMockResolver.insert(BMR.User.CONTENT_URI, item.getContentValues());
        long itemId = ContentUris.parseId(rowUri);

        // Does a full query on the table.
        Cursor cursor = mMockResolver.query(BMR.User.CONTENT_URI, null, null, null, null);

        // Asserts that there should be only 1 record.
        assertEquals(1, cursor.getCount());

        // Moves to the first (and only) record in the cursor and asserts that
        // this worked.
        assertTrue(cursor.moveToFirst());

        // Since no projection was used, get the column indexes of the returned
        // columns
        int numberIndex = cursor.getColumnIndex(BMR.User.COLUMN_NAME_ACCOUNT);
        int passwordIndex = cursor.getColumnIndex(BMR.User.COLUMN_NAME_PASSWORD);

        // Tests each column in the returned cursor against the data that was
        // inserted, comparing the field in the UserItem object to the
        // data at the column index in the cursor.
        assertEquals(item.account, cursor.getString(numberIndex));
        assertEquals(item.password, cursor.getString(passwordIndex));

        // Insert subtest 2.
        // Tests that we can't insert a record whose id value already exists.

        // Defines a ContentValues object so that the test can add a ID to it.
        ContentValues values = item.getContentValues();

        // Adds the item ID retrieved in subtest 1 to the ContentValues object.
        values.put(BMR.User._ID, (int) itemId);

        // Tries to insert this record into the table. This should fail and drop
        // into the catch block. If it succeeds, issue a failure message.
        try {
            rowUri = mMockResolver.insert(BMR.User.CONTENT_URI, values);
            fail("Expected insert failure for existing record but insert succeeded.");
        } catch (Exception e) {
            // succeeded, so do nothing.
        }
    }

    public void testInserts02() {
    	// TODO: For error cases test.
    }

    public void testInserts03() {
        // Creates a new item with long account.
        UserItem item = new UserItem("123456789012345678901234567890", "123456789012345678901234567890");
        Uri rowUri = mMockResolver.insert(BMR.User.CONTENT_URI, item.getContentValues());
        assertNotNull(rowUri);
        
        item.account = "+12345678";
        rowUri = mMockResolver.insert(BMR.User.CONTENT_URI, item.getContentValues());
        assertNotNull(rowUri);
        
        item.account = "+01087654321";
        rowUri = mMockResolver.insert(BMR.User.CONTENT_URI, item.getContentValues());
        assertNotNull(rowUri);

        item.account = "1";
        rowUri = mMockResolver.insert(BMR.User.CONTENT_URI, item.getContentValues());
        assertNotNull(rowUri);

        // Asserts that the returned cursor contains no records
        Cursor cursor = mMockResolver.query(BMR.User.CONTENT_URI, null, null, null, null);
        assertEquals(4, cursor.getCount());
    }

    public void testDeletes() {
        // Subtest 1.
        // Tries to delete a record from a data model that is empty.

        // Sets the selection column to "account"
        final String SELECTION_COLUMNS = BMR.User.COLUMN_NAME_ACCOUNT + " = " + "?";

        // Sets the selection argument testAccount[0]
        final String[] SELECTION_ARGS = { testAccount[0] };

        // Tries to delete rows matching the selection criteria from the data
        // model.
        int rowsDeleted = mMockResolver.delete(BMR.User.CONTENT_URI, SELECTION_COLUMNS, SELECTION_ARGS);

        // Assert that the deletion did not work. The account of deleted rows
        // should be zero.
        assertEquals(0, rowsDeleted);

        // Subtest 2.
        // Tries to delete an existing record. Repeats the previous subtest, but
        // inserts data first.

        // Inserts data into the model.
        insertData();

        // Uses the same parameters to try to delete the row with title "Note0"
        rowsDeleted = mMockResolver.delete(BMR.User.CONTENT_URI, SELECTION_COLUMNS, SELECTION_ARGS);

        // The account of deleted rows should be 1.
        assertEquals(1, rowsDeleted);

        // Tests that the record no longer exists. Tries to get it from the
        // table, and asserts that nothing was returned.

        // Queries the table with the same selection column and argument used to
        // delete the row.
        Cursor cursor = mMockResolver.query(BMR.User.CONTENT_URI, null, SELECTION_COLUMNS,
                SELECTION_ARGS, null);

        // Asserts that the cursor is empty since the record had already been
        // deleted.
        assertEquals(0, cursor.getCount());
    }

    // A utility for converting user data to a ContentValues map.
    private static class UserItem {
        String account;
        String password;

        public UserItem(String a, String p) {
            account = a;
            password = p;
        }

        public ContentValues getContentValues() {
            ContentValues v = new ContentValues();

            v.put(BMR.User.COLUMN_NAME_ACCOUNT, account);
            v.put(BMR.User.COLUMN_NAME_PASSWORD, password);
            return v;
        }
    }
}
