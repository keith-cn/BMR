package com.hackathon.babymedicalrecord.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.hackathon.babymedicalrecord.BMRLoginActivity;

public class BMRLoginActivityTest extends ActivityInstrumentationTestCase2<BMRLoginActivity> {

    private BMRLoginActivity mBMRLoginActivity;
    private TextView mTextView;

    public BMRLoginActivityTest() {
        super(BMRLoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Sets the initial touch mode for the Activity under test. This must be
        // called before getActivity()
        setActivityInitialTouchMode(true);

        mBMRLoginActivity = getActivity();
//        mTextView = (TextView) mBMRLoginActivity.findViewById(com.hackathon.babymedicalrecord.R.id.textView1);
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    public void testPreconditions() {
        assertNotNull(mBMRLoginActivity);
//        assertNotNull(mTextView);
    }
}
