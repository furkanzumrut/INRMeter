package com.android.inrmeter.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPager extends FragmentPagerAdapter {

	public TabsPager(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			// Beyaz fragment activity
			return null;
		case 1:
			// Kırmızı fragment activity
			return null;
		case 2:
			// Mavi fragment activity
			return null;
		}

		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3; // Tab sayımız
	}

}