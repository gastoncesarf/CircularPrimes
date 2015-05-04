package com.example.mlcircularprimes.fragments;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * FragmentTest.java
 *  
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mlcircularprimes.R;
import com.example.mlcircularprimes.circularprime.CircularPrime;
import com.example.mlcircularprimes.components.ExpandableListAdapter;
import com.example.mlcircularprimes.fragments.classes.FragmentListener;
import com.example.mlcircularprimes.utils.Utils;

@SuppressLint("ValidFragment")
public class FragmentTest extends BaseFragment {
	private ExpandableListView expListView;
	public Activity baseActivity;

	public FragmentTest() {
	}

	public FragmentTest(FragmentListener listener) {
		mListener = listener;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.layout_2_test, container, false);

		expListView = (ExpandableListView) view.findViewById(R.id.lyPrimesList);

		// Inicializo el spiner con las opciones (10 - 100 - 1000 - 10000 -
		// 100000 - 1000000)
		String arrSpinner[] = new String[6];
		for (int i = 0; i < 6; i++)
			arrSpinner[i] = String.valueOf(((Double) Math.pow(10, (double) i + 1)).intValue());
		final Spinner limit = (Spinner) view.findViewById(R.id.lyLimitSpinner);
		limit.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrSpinner));
		// Selecciono por default el solicitado en el ejercicio a resolver
		// 1000000
		limit.setSelection(5);

		baseActivity = getActivity();

		// Seteo los labels
		((TextView) view.findViewById(R.id.lyTxtProcessors)).setTextColor((Color.parseColor("#0000FF")));
		((TextView) view.findViewById(R.id.lyTxtProcessors)).setText("Procesadores detectados: " + Runtime.getRuntime().availableProcessors());
		((TextView) view.findViewById(R.id.lyTxtProcess)).setTextColor((Color.parseColor("#FF0000")));

		// Listener sobre el boton Calcular
		((Button) view.findViewById(R.id.lyFindPrimesBtn)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int nLimit = Integer.parseInt(limit.getSelectedItem().toString());

				final RadioButton rd = (RadioButton) view.findViewById(R.id.lyRd1);
				final CircularPrime cp = new CircularPrime();

				// Inicio proceso...
				((TextView) view.findViewById(R.id.lyTxtProcess)).setVisibility(View.VISIBLE);
				final Dialog progress = Utils.showProgressBar("Calculando...", getActivity());

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							/*
							 * Verifico el algoritmo a utilizar 1) Usando
							 * Threads y un mecanismo similar al de fuerza
							 * bruta, aunque un poco mas optimizado 2) Criba de
							 * Eratosthenes, un algoritmo optimo para la
							 * resolicion de este problema
							 */
							if (rd.isChecked()) {
								cp.solveCircularPrimesUsingThreads(nLimit);
							} else {
								cp.solveCircularPrimesUsingSieveEratosthenes(nLimit);
							}
						} catch (Exception e) {
							Utils.showAlert("", "Ha ocurrido un problema al realizar la operación, por favor intente nuevamente", getActivity());
						} finally {
							((TextView) view.findViewById(R.id.lyTxtProcess)).setVisibility(View.INVISIBLE);
							progress.dismiss();
						}
						SparseArray<List<Object>> results = cp.getCircularPrimesResult();
						if ((results != null) && (results.size() > 0)) {
							// Muestro los valores generados por el proceso
							List<Object[]> listDataHeader = new ArrayList<Object[]>();
							HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
							((TextView) view.findViewById(R.id.lyTxtTotal)).setText("Primos Circulares: " + String.valueOf(cp.getCircularPrimesResult().size()));
							((TextView) view.findViewById(R.id.lyTxtTime)).setText("Tiempo (ms): " + String.valueOf(cp.getProcessTime()));

							// Armo la estructura que llenará el
							// ExpandableListAdapter agrupando PRIMO CIRCULAR
							// PRINCIPAL con sus respectivos PRIMOS
							for (int i = 0; i < results.size(); i++) {
								int key = results.keyAt(i);
								Object[] arr = { Integer.toString(key), "" };
								listDataHeader.add(arr);
								List<String> child = new ArrayList<String>();
								List<Object> primes = results.get(key);
								for (int k = 0; k < primes.size(); k++) {
									child.add(String.valueOf(primes.get(k)));
								}
								listDataChild.put((String) listDataHeader.get(i)[0], child);
							}

							ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
							expListView.setAdapter(listAdapter);
						}
					}
				}, 500);

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

	public static FragmentTest newInstance() {
		return new FragmentTest();
	}

	public static FragmentTest newInstance(FragmentListener listener) {
		return new FragmentTest(listener);
	}
}
