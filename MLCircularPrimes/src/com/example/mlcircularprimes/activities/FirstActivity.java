package com.example.mlcircularprimes.activities;

/* 
 * 2015-02-05 
 * FLORES GASTON - PRIMOS CIRCULARES
 * 
 * FirstActivity.java
 *  
 */

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.mlcircularprimes.R;
import com.example.mlcircularprimes.utils.Utils;

public class FirstActivity extends BaseActivity {

	private CrazyHandler handler = new CrazyHandler();
	private TextView crazyNumbers[] = null;
	final Activity thisActivity = this;

	protected void onCreate(Bundle savedInstanceState) {
		final Timer imageTimer = new Timer();
		final int txtIdBase = 9999;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_0_first);

		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/font_numbers.ttf");
		crazyNumbers = new TextView[36];
		LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		int cont = 0;
		for (int i = 1; i <= 6; i++) {
			LinearLayout row = (LinearLayout) findViewById(this.getResources().getIdentifier("lyGridSplashRow" + i, "id", this.getPackageName()));
			for (int j = 1; j <= 6; j++) {
				TextView t = new TextView(this);
				t.setTypeface(tf);
				t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
				t.setLayoutParams(lp);
				t.setId(txtIdBase + cont);
				crazyNumbers[cont] = t;
				row.addView(t);
				cont++;
			}

		}

		// Inicializacion de los ImageSwitcher
		final ImageSwitcher topImageSwitcher = (ImageSwitcher) findViewById(R.id.lySplashImageSwitcher1);
		topImageSwitcher.bringToFront();
		final ImageSwitcher bottomImageSwitcher = (ImageSwitcher) findViewById(R.id.lySplashImageSwitcher2);
		ViewFactory vf = new ViewFactory() {
			@SuppressWarnings("deprecation")
			@Override
			public View makeView() {
				ImageView myView = new ImageView(getApplicationContext());
				myView.setScaleType(ImageView.ScaleType.FIT_END);
				myView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
				return myView;
			}
		};
		topImageSwitcher.setFactory(vf);
		bottomImageSwitcher.setFactory(vf);

		// Handlers - Animacion de Foto
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Utils.setImage(thisActivity, topImageSwitcher, R.drawable.img_splash01, android.R.anim.fade_in, android.R.anim.fade_out, 2000);
			}
		}, 500);

		// Handlers - Animacion sobre los ImageSwitcher
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Utils.setImage(thisActivity, bottomImageSwitcher, R.drawable.img_splash02, android.R.anim.fade_in, android.R.anim.fade_out,
								2000);
						findViewById(R.id.lySplashButtonSplash).setVisibility(View.VISIBLE);
					}
				}, 500);
			}
		}, 1250);

		// Handlers - Animacion de numeros
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				imageTimer.schedule(new TickClass(), 500, 200);
			}
		}, 2000);

		// Listener
		((Button) findViewById(R.id.lySplashButtonSplash)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(thisActivity, FragmentChangeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(intent);
				thisActivity.overridePendingTransition(R.drawable.eff_push_right_in, R.drawable.eff_push_right_out);
				imageTimer.cancel();
				finish();
			}
		});

	}

	/*
	 * Timer y Handler para el manejo de la animacion de los numeros
	 */
	private class TickClass extends TimerTask {
		@Override
		public void run() {
			handler.sendEmptyMessage(0);
		}
	}

	@SuppressLint("HandlerLeak")
	private class CrazyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Random rand = new Random();
			for (int j = 0; j < 36; j++) {
				int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
				crazyNumbers[j].setTextColor(color);
				crazyNumbers[j].setText(Integer.toString(rand.nextInt(100)));
			}
		}
	}
}
