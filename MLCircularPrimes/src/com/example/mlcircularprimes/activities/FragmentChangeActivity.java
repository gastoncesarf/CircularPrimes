package com.example.mlcircularprimes.activities;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * FragmentChangeActivity.java
 * Este Fragment principal de la aplicacion, captura los eventos del TABHOST, para realizar
 * las correspondientes acciones, entre ellas, mostrar el fragment asociado a cada TAB.
 * En este sencillo ejemplo tenemos solo 2 TABs, ellos estan definidos en el package FRAGMENTS.
 * Para conectar este fragment principal con el resto, usando de puente el TABHOST utilizo una 
 * clase FragmentViewAdapter, esta es la que manejará los eventos de los fragments. Como extra
 * se puede declarar un FragmentListener, en el caso de que haga falta implementar alguna otra 
 * operacion un poco mas complicada.
 * Esta estructura esta pensada para soportar fragments en cada TAB y fragments anidados, y con el
 * manejo adecuado de los eventos se podrá ir de un fragment a otro sin problemas (sin importar si 
 * pertence a otro tab), un problema muy comun durante el desarrollo y manejo de fragments.
 *  
 */

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;

import com.example.mlcircularprimes.R;
import com.example.mlcircularprimes.fragments.classes.FragmentViewAdapter;
import com.example.mlcircularprimes.fragments.classes.TabFactory;
import com.example.mlcircularprimes.utils.Utils;

public class FragmentChangeActivity extends BaseActivity implements OnTabChangeListener, OnPageChangeListener {
	private ViewPager mPager;
	private FragmentViewAdapter mAdapter;
	private TabHost mTabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_widget);

		Utils.rootActivity = this;

		// Configuro mis TABs
		mPager = (ViewPager) findViewById(R.id.viewpager);
		initializesTabHost();
		mAdapter = new FragmentViewAdapter(getSupportFragmentManager());
		mAdapter.activity = this;
		mPager.setAdapter(mAdapter);

		// Configuro mi actionBar
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E0F2F7")));

	}

	private void initializesTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		TabWidget widget = mTabHost.getTabWidget();
		int oldFocusability = widget.getDescendantFocusability();
		widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

		widget.setDescendantFocusability(oldFocusability);

		View mainTabIndicator_1 = LayoutInflater.from(this).inflate(R.layout.tab_button, mTabHost.getTabWidget(), false);
		((ImageView) mainTabIndicator_1.findViewById(R.id.buttonBarIcon)).setImageResource(R.drawable.ic_search);

		View mainTabIndicator_2 = LayoutInflater.from(this).inflate(R.layout.tab_button, mTabHost.getTabWidget(), false);
		((ImageView) mainTabIndicator_2.findViewById(R.id.buttonBarIcon)).setImageResource(R.drawable.ic_numbers);

		View mainTabIndicator_3 = LayoutInflater.from(this).inflate(R.layout.tab_button, mTabHost.getTabWidget(), false);
		((ImageView) mainTabIndicator_3.findViewById(R.id.buttonBarIcon)).setImageResource(R.drawable.ic_action);

		mTabHost.getTabWidget().setDividerDrawable(R.drawable.style_tab_empty_divider);

		AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("").setIndicator(mainTabIndicator_1));
		AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("").setIndicator(mainTabIndicator_2));
		AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("").setIndicator(mainTabIndicator_3));

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			mTabHost.getTabWidget().getChildAt(i).setFocusable(false);
		}

		mTabHost.setOnTabChangedListener(this);
	}

	// Metodo para agregar los TABs al TABHOST
	private void AddTab(Activity activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
		tabSpec.setContent(new TabFactory(activity));
		tabHost.addTab(tabSpec);
	}

	// Listeners del TABHOST
	public void onTabChanged(String tag) {
		int pos = this.mTabHost.getCurrentTab();
		this.mPager.setCurrentItem(pos);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		int pos = this.mPager.getCurrentItem();
		this.mTabHost.setCurrentTab(pos);
	}

	@Override
	public void onPageSelected(int arg0) {
	}
}
