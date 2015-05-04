package com.example.mlcircularprimes.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mlcircularprimes.R;
import com.example.mlcircularprimes.R.integer;
import com.example.mlcircularprimes.circularprime.CircularPrime;
import com.example.mlcircularprimes.circularprime.PrimeLib;
import com.example.mlcircularprimes.circularprime.PrimeThread;
import com.example.mlcircularprimes.components.ExpandableListAdapter;
import com.example.mlcircularprimes.fragments.classes.FragmentListener;
import com.example.mlcircularprimes.utils.Utils;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * FragmentMain.java
 *  
 */

@SuppressLint("ValidFragment")
public class FragmentMain extends BaseFragment {
	public Activity baseActivity;
	private ExpandableListView expListView;

	public FragmentMain() {
	}

	public FragmentMain(FragmentListener listener) {
		mListener = listener;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.layout_1_main, container, false);

		expListView = (ExpandableListView) view.findViewById(R.id.lyPrimesMainList);

		// Seteo los labels
		((TextView) view.findViewById(R.id.lyTxtMainProcess)).setTextColor((Color.parseColor("#FF0000")));
		
		// Listener sobre el boton Calcular
		((Button) view.findViewById(R.id.lyFindMainPrimesBtn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String number = ((TextView) view.findViewById(R.id.lyLimitText)).getText().toString();
				Integer primeToValid = null;
				final Integer primeToCheck;
				try {
					primeToValid = Integer.parseInt(number);
					if (primeToValid < 2)
						throw new Exception("Número < 2");
				} catch (Exception e) {
					primeToValid = null;
					Utils.showAlert("", "El número debe ser mayor o igual 2", getActivity());
				}
				primeToCheck = primeToValid;
				if (primeToCheck != null) {
					// Inicio proceso...
					((TextView) view.findViewById(R.id.lyTxtMainProcess)).setVisibility(View.VISIBLE);
					final Dialog progress = Utils.showProgressBar("Calculando...", getActivity());

					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							try {
								Set<Integer> circularPrimes = null;

								boolean isCircularPrime = false;
								if (PrimeLib.isPrime(primeToCheck)) {
									isCircularPrime = true;
									circularPrimes = PrimeLib.getCircularNumbers(primeToCheck);
									for (Integer circularPrime : circularPrimes) {
										if (!PrimeLib.isPrime(circularPrime)) {
											isCircularPrime = false;
											break;
										}
									}
								}
								if (isCircularPrime) {
									((TextView) view.findViewById(R.id.lyTxtMainPrimeValidation)).setTextColor((Color.parseColor("#0000FF")));
									((TextView) view.findViewById(R.id.lyTxtMainPrimeValidation)).setText("El número ingresesado es un primo circular");
									if ((circularPrimes != null) && (circularPrimes.size() > 0)) {
										List<Object[]> listDataHeader = new ArrayList<Object[]>();
										HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();

											Object[] arr = { Integer.toString(primeToCheck), "" };
											listDataHeader.add(arr);
											List<String> child = new ArrayList<String>();
											for (Integer p:circularPrimes) {
												child.add(String.valueOf(p));
											}
											listDataChild.put((String) listDataHeader.get(0)[0], child);
										

										ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
										expListView.setAdapter(listAdapter);
										expListView.expandGroup(0);
									}
								} else {
									((TextView) view.findViewById(R.id.lyTxtMainPrimeValidation)).setTextColor((Color.parseColor("#FF0000")));
									((TextView) view.findViewById(R.id.lyTxtMainPrimeValidation)).setText("El número ingresesado NO es un primo circular");
									ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), new ArrayList<Object[]>(), new HashMap<String, List<String>>());
									expListView.setAdapter(listAdapter);
									
								}
							} finally {
								((TextView) view.findViewById(R.id.lyTxtMainProcess)).setVisibility(View.INVISIBLE);
								progress.dismiss();
							}

						}
					}, 500);
				}
			}
		});

		// Fix para poder el scrolling sobre la ExpandableListView
		expListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					v.getParent().requestDisallowInterceptTouchEvent(true);
					break;

				case MotionEvent.ACTION_UP:
					v.getParent().requestDisallowInterceptTouchEvent(false);
					break;
				}
				v.onTouchEvent(event);
				return true;
			}
		});

		return view;
	}

	public static FragmentMain newInstance() {
		return new FragmentMain();
	}

	public static FragmentMain newInstance(FragmentListener listener) {
		return new FragmentMain(listener);
	}
}