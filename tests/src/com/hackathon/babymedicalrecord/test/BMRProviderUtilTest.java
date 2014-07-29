package com.hackathon.babymedicalrecord.test;

import java.util.ArrayList;

import com.hackathon.babymedicalrecord.provider.BMR;
import com.hackathon.babymedicalrecord.provider.BMRProviderUtil;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class BMRProviderUtilTest extends AndroidTestCase {
    private Context mContext;

    private final BMRProviderUtil.User.UserItem[] TEST_ITEMS = {
            new BMRProviderUtil.User.UserItem(), new BMRProviderUtil.User.UserItem(),
            new BMRProviderUtil.User.UserItem(), new BMRProviderUtil.User.UserItem(),
            new BMRProviderUtil.User.UserItem(), new BMRProviderUtil.User.UserItem(),
            new BMRProviderUtil.User.UserItem(), new BMRProviderUtil.User.UserItem(),
            new BMRProviderUtil.User.UserItem() };
    private final int TEST_ALEN = TEST_ITEMS.length;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Delete all items before test cases
        mContext = getContext();
        BMRProviderUtil.User.deleteAllItems(mContext);
    }

    @Override
    protected void tearDown() throws Exception {
        // Delete the whole database table after test cases
        BMRProviderUtil.User.deleteAllItems(mContext);

        super.tearDown();
    }

    private void insertData() {
        ContentValues values = new ContentValues();

        // Sets up test data
        TEST_ITEMS[0].account = "account001";
        TEST_ITEMS[0].password = "password001";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[0].account, TEST_ITEMS[0].password);

        TEST_ITEMS[1].account = "account002";
        TEST_ITEMS[1].password = "password002";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[1].account, TEST_ITEMS[1].password);

        TEST_ITEMS[2].account = "account003";
        TEST_ITEMS[2].password = "password003";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[2].account, TEST_ITEMS[2].password);

        TEST_ITEMS[3].account = "account004";
        TEST_ITEMS[3].password = "password004";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[3].account, TEST_ITEMS[3].password);

        TEST_ITEMS[4].account = "account005";
        TEST_ITEMS[4].password = "password005";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[4].account, TEST_ITEMS[4].password);

        TEST_ITEMS[5].account = "account006";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[5].account, TEST_ITEMS[5].password);

        TEST_ITEMS[6].account = "1717171";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[6].account, TEST_ITEMS[6].password);

        TEST_ITEMS[7].account = "account007";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[7].account, TEST_ITEMS[7].password);

        TEST_ITEMS[8].account = "account008";
        BMRProviderUtil.User.insertItem(mContext, TEST_ITEMS[8].account, TEST_ITEMS[8].password);
    }

    public void testInsertItem() {
        // Create a new item for insert test
        BMRProviderUtil.User.UserItem item = new BMRProviderUtil.User.UserItem();
        item.account = "account001";
        item.password = "password001";

        Uri rowUri = BMRProviderUtil.User.insertItem(mContext, item.account, item.password);
        assertNotNull(rowUri);

        long itemId = ContentUris.parseId(rowUri);

        // Does a full query on the table.
        Cursor cursor = BMRProviderUtil.User.getCursorForAllItems(mContext);

        // Asserts that there should be only 1 record.
        assertEquals(1, cursor.getCount());

        // Moves to the first (and only) record in the cursor and asserts that
        // this worked.
        assertTrue(cursor.moveToFirst());

        // Since no projection was used, get the column indexes of the returned
        // columns
        int accountIndex = cursor.getColumnIndex(BMR.User.COLUMN_NAME_ACCOUNT);
        int passwordIndex = cursor.getColumnIndex(BMR.User.COLUMN_NAME_PASSWORD);

        // Tests each column in the returned cursor against the data that was
        // inserted, comparing the field in the BMRProviderUtil.User.UserItem
        // object to the data at the column index in the cursor.
        assertEquals(item.account, cursor.getString(accountIndex));
        assertEquals(item.password, cursor.getString(passwordIndex));

        // Insert subtest 2.
        // Tests that we can't insert a record whose account value already
        // exists.
        item.account = "account001";
        item.password = "password002"; // different password

        // Tries to insert a record with existed account value into the table.
        // This should fail and drop
        // into the catch block. If it succeeds, issue a failure message.
        try {
            rowUri = BMRProviderUtil.User.insertItem(mContext, item.account, item.password);
            fail("Expected insert failure for existing record but insert succeeded.");
        } catch (Exception e) {
            // succeeded, so do nothing.
        }

        // Insert subtest 3.
        // Tests that we can insert a record whose password value already
        // exists.
        item.account = "account003";
        item.password = "password001";

        // Tries to insert a record with existed password value into the table.
        rowUri = BMRProviderUtil.User.insertItem(mContext, item.account, item.password);
        assertNotNull(rowUri);
    }

    public void testDeleteItemById() {
        // Create a new item for delete test
        BMRProviderUtil.User.UserItem item = new BMRProviderUtil.User.UserItem();
        item.account = "account001";
        item.password = "password001";

        // First, insert two rows
        Uri rowUri1 = BMRProviderUtil.User.insertItem(mContext, item.account, item.password);
        long itemId1 = ContentUris.parseId(rowUri1);
        item.account = "account002";
        item.password = "password002";
        Uri rowUri2 = BMRProviderUtil.User.insertItem(mContext, item.account, item.password);
        long itemId2 = ContentUris.parseId(rowUri2);

        // Delete these two rows
        boolean deleteResult = BMRProviderUtil.User.deleteItemById(mContext, itemId1);
        assertTrue(deleteResult);
        deleteResult = BMRProviderUtil.User.deleteItemById(mContext, itemId2);
        assertTrue(deleteResult);
    }

    public void testDeleteItemByAccountName() {
        // Create two new items for delete test
        BMRProviderUtil.User.UserItem item1 = new BMRProviderUtil.User.UserItem();
        item1.account = "account001";
        item1.password = "password001";
        BMRProviderUtil.User.UserItem item2 = new BMRProviderUtil.User.UserItem();
        item2.account = "account002";
        item2.password = "password002";

        // First, insert two rows
        Uri rowUri1 = BMRProviderUtil.User.insertItem(mContext, item1.account, item1.password);
        long itemId1 = ContentUris.parseId(rowUri1);
        Uri rowUri2 = BMRProviderUtil.User.insertItem(mContext, item2.account, item2.password);
        long itemId2 = ContentUris.parseId(rowUri2);

        // Delete these two rows
        boolean deleteResult = BMRProviderUtil.User
                .deleteItemByAccountName(mContext, item1.account);
        assertTrue(deleteResult);
        deleteResult = BMRProviderUtil.User.deleteItemByAccountName(mContext, item2.account);
        assertTrue(deleteResult);
    }

    public void testDeleteAllItems() {
        insertData();

        boolean deleteResult = BMRProviderUtil.User.deleteAllItems(mContext);
        assertTrue(deleteResult);

        // Test to delete a blank table. Reture false when the table is blank.
        deleteResult = BMRProviderUtil.User.deleteAllItems(mContext);
        assertFalse(deleteResult);
    }

    public void testGetItemById() {
        insertData();

        BMRProviderUtil.User.UserItem item = BMRProviderUtil.User.getItemById(mContext, 1);
        assertNotNull(item);

        item = BMRProviderUtil.User.getItemById(mContext, 2);
        assertNotNull(item);

        item = BMRProviderUtil.User.getItemById(mContext, 3);
        assertNotNull(item);

        // Tests an invalid ID.
        item = BMRProviderUtil.User.getItemById(mContext, 1000);
        assertNull(item);
    }

    public void testgetItemByAccountName() {
        insertData();

        BMRProviderUtil.User.UserItem item = BMRProviderUtil.User.getItemByAccountName(mContext,
                TEST_ITEMS[0].account);
        assertNotNull(item);
        assertEquals(TEST_ITEMS[0].account, item.account);
        assertEquals(TEST_ITEMS[0].password, item.password);

        item = BMRProviderUtil.User.getItemByAccountName(mContext, TEST_ITEMS[1].account);
        assertNotNull(item);
        assertEquals(TEST_ITEMS[1].account, item.account);
        assertEquals(TEST_ITEMS[1].password, item.password);
    }

    public void testGetAllItems() {

        ArrayList<BMRProviderUtil.User.UserItem> listTest;
        for (int i = 0; i < TEST_ALEN; i++) {
            TEST_ITEMS[i].account = "account00" + i;
            TEST_ITEMS[i].password = "password00" + i;
            BMRProviderUtil.User
                    .insertItem(mContext, TEST_ITEMS[i].account, TEST_ITEMS[i].password);
        }

        listTest = BMRProviderUtil.User.getAllItems(mContext);
        assertNotNull(listTest);
        assertEquals(TEST_ALEN, listTest.size());
        // Test the default sort is ASC.
        int i = 0;
        for (BMRProviderUtil.User.UserItem item : listTest) {
            assertTrue(i < TEST_ALEN);
            assertTrue(i >= 0);
            assertEquals("account00" + i, item.account);
            assertEquals("password00" + i, item.password);
            i++;
        }
    }

    public void testGetCursorLoaderForAllItems() {
        CursorLoader loader = BMRProviderUtil.User.getCursorLoaderForAllItems(mContext);
        assertNotNull(loader);
    }

    public void testGetCursorForAllItems() {
        Cursor curs = BMRProviderUtil.User.getCursorForAllItems(mContext);
        assertNotNull(curs);
        for (int i = 0; i < TEST_ALEN; i++) {
            TEST_ITEMS[i].account = "account00" + i;
            TEST_ITEMS[i].password = "password00" + i;
            BMRProviderUtil.User
                    .insertItem(mContext, TEST_ITEMS[i].account, TEST_ITEMS[i].password);
        }
        curs = BMRProviderUtil.User.getCursorForAllItems(mContext);
        assertEquals(TEST_ALEN, curs.getCount());
        assertTrue(curs.moveToFirst());
        // Test the default sort is ASC.
        int i = 0;
        do {
            int accountIndex = curs.getColumnIndex(BMR.User.COLUMN_NAME_ACCOUNT);
            int passwordIndex = curs.getColumnIndex(BMR.User.COLUMN_NAME_PASSWORD);
            assertTrue(i < TEST_ALEN);
            assertTrue(i >= 0);
            assertEquals("account00" + i, curs.getString(accountIndex));
            assertEquals("password00" + i, curs.getString(passwordIndex));
            i++;
        } while (curs.moveToNext());
    }
}
