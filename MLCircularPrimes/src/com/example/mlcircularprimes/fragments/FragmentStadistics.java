package com.example.mlcircularprimes.fragments;


/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * FragmentStadistics.java
 *  
 */

import java.text.DecimalFormat;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.example.mlcircularprimes.R;
import com.example.mlcircularprimes.circularprime.CircularPrime;
import com.example.mlcircularprimes.fragments.classes.FragmentListener;
import com.example.mlcircularprimes.utils.Utils;

@SuppressLint("ValidFragment")
public class FragmentStadistics extends BaseFragment {
	private XYPlot plot;

	public FragmentStadistics() {
	}

	public FragmentStadistics(FragmentListener listener) {
		mListener = listener;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.layout_3_stadistics, container, false);
		
		// Listener sobre el boton Calcular
		((Button) view.findViewById(R.id.lyFindPrimesBtn)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Inicio proceso...
				((TextView) view.findViewById(R.id.lyTxtProcess)).setTextColor((Color.parseColor("#FF0000")));
				((TextView) view.findViewById(R.id.lyTxtProcess)).setVisibility(View.VISIBLE);
				final Dialog progress = Utils.showProgressBar("Calculando...", getActivity());
				
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						CircularPrime cpThread = new CircularPrime();
						CircularPrime cpSiege = new CircularPrime();
						try {
							Number timeWithThread[] = new Number[6];
							Number timeWithSiege[] = new Number[6];
							for (int i = 0; i < 6; i++) {
								int N = ((Double) Math.pow(10, (double) i + 1)).intValue();
								cpThread.solveCircularPrimesUsingThreads(N);
								cpSiege.solveCircularPrimesUsingSieveEratosthenes(N);
								timeWithThread[i] = cpThread.getProcessTime();
								timeWithSiege[i] = cpSiege.getProcessTime();
							}
							plot = (XYPlot) view.findViewById(R.id.mySimpleXYPlot);
							plot.clear();
							Number[] series1Numbers = timeWithThread;
							Number[] series2Numbers = timeWithSiege;
						        
							Number[] xNumbers = { 1, 2, 3, 4, 5, 6};

							plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
							plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 50);

							plot.getGraphWidget().setDomainValueFormat(new DecimalFormat(""));
							plot.getGraphWidget().setRangeValueFormat(new DecimalFormat(""));

							XYSeries series1 = new SimpleXYSeries(Arrays.asList(xNumbers), Arrays.asList(series1Numbers), "Threads");
							XYSeries series2 = new SimpleXYSeries(Arrays.asList(xNumbers), Arrays.asList(series2Numbers), "Criba de Eratóstenes");

							LineAndPointFormatter series1Format = new LineAndPointFormatter();
							series1Format.setPointLabelFormatter(new PointLabelFormatter());
							series1Format.configure(getActivity().getApplicationContext(), R.xml.line_point_formatter_with_plf1);

							plot.addSeries(series1, series1Format);

							LineAndPointFormatter series2Format = new LineAndPointFormatter();
							series2Format.setPointLabelFormatter(new PointLabelFormatter());
							series2Format.configure(getActivity().getApplicationContext(), R.xml.line_point_formatter_with_plf2);
							plot.addSeries(series2, series2Format);

							plot.setTicksPerRangeLabel(5);

							// Angulo de rotacion de los labels del eje X
							plot.getGraphWidget().setDomainLabelOrientation(-90);
							plot.redraw();
						} catch (Exception e) {
							Utils.showAlert("", "Ha ocurrido un problema al realizar la operación, por favor intente nuevamente", getActivity());
						} finally {
							((TextView) view.findViewById(R.id.lyTxtProcess)).setVisibility(View.INVISIBLE);
							progress.dismiss();
						}
					}
				}, 500);

			}
		});

		return view;
	}

	public static FragmentStadistics newInstance() {
		return new FragmentStadistics();
	}

	public static FragmentStadistics newInstance(FragmentListener listener) {
		return new FragmentStadistics(listener);
	}
}
