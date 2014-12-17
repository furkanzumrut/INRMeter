package com.android.inrmeter.tests;

import com.android.inrmeter.activity.HospitalList;

import android.test.ActivityInstrumentationTestCase2;


public class HospitalListTest extends ActivityInstrumentationTestCase2<HospitalList> {

	private HospitalList hospitallist;
	

	public HospitalListTest() {
		super(HospitalList.class);
	}

	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		hospitallist = getActivity();
		
		//assertEquals(2,hospitallist.getListAdapter().getCount());
//	    final String expected =
//	            mFirstTestActivity.getString(R.string.my_first_test);
//	    final String actual = mFirstTestText.getText().toString();
//	    assertEquals(expected, actual);
	}
}