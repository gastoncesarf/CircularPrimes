package com.example.mlcircularprimes.fragments.classes;


/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * FragmentViewAdapter.java
 *  
 */

import com.example.mlcircularprimes.activities.FragmentChangeActivity;
import com.example.mlcircularprimes.fragments.BaseFragment;
import com.example.mlcircularprimes.fragments.FragmentMain;
import com.example.mlcircularprimes.fragments.FragmentStadistics;
import com.example.mlcircularprimes.fragments.FragmentTest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentViewAdapter extends FragmentPagerAdapter {
	public FragmentChangeActivity activity;
	private BaseFragment mFragmentAtPos0;
	private BaseFragment mFragmentAtPos1;
	private BaseFragment mFragmentAtPos2;
	private static final int NUM_OF_ITEMS = 3;

	public FragmentViewAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		if(position == 0){
			if (mFragmentAtPos0 == null) {
				mFragmentAtPos0 = FragmentMain.newInstance();
			}
			return mFragmentAtPos0;
		}else if(position == 1){
			if (mFragmentAtPos1 == null) {
				mFragmentAtPos1 = FragmentTest.newInstance();
			}
			return mFragmentAtPos1;
		}else if(position == 2){
			if (mFragmentAtPos2 == null) {
				mFragmentAtPos2 = FragmentStadistics.newInstance();
			}
			return mFragmentAtPos2;
		}else{
			return mFragmentAtPos0;
		}
	}

	@Override
	public int getCount() {
		return NUM_OF_ITEMS;
	}
}
