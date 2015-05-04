package com.example.mlcircularprimes.fragments;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * BaseFragment.java
 *  
 */

import com.example.mlcircularprimes.activities.FragmentChangeActivity;
import com.example.mlcircularprimes.fragments.classes.FragmentListener;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

	private boolean mShowingChild;

	protected FragmentListener mListener;
	public static FragmentChangeActivity fragmentBase;
	
	public boolean isShowingChild() {
		return mShowingChild;
	}

	public void setShowingChild(boolean showingChild) {
		mShowingChild = showingChild;
	}

}