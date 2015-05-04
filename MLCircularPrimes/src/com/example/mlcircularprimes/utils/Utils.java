package com.example.mlcircularprimes.utils;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.TextView;

import com.example.mlcircularprimes.R;

public final class Utils {
	public static Activity rootActivity;
	
	public static void setImage(Activity activity, ImageSwitcher imageSwitcher, int resource, int effectIn,
			int effectOut, int duration) {
		Animation in = AnimationUtils.loadAnimation(activity, effectIn);
		in.setDuration(duration);
		Animation out = AnimationUtils.loadAnimation(activity, effectOut);
		out.setDuration(duration);
		imageSwitcher.setInAnimation(in);
		imageSwitcher.setOutAnimation(out);
		imageSwitcher.setImageResource(resource);
	}
	
	
	public static void showAlert(String title, String message, Activity activity){
		showAlert(title, message, activity, null);
	}
	
	public static void showAlert(String title, String message, Activity activity, Integer timeout){
		if(activity != null){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
			alertDialogBuilder.setTitle(title);
			alertDialogBuilder.setMessage(message);
			final AlertDialog a = alertDialogBuilder.show();
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				public void run() {
					if(a!=null){
						a.dismiss();	
					}
				}
			}, (timeout != null)?timeout : 5000);
		}
	}
	
	public static boolean isSignificant(Object obj){
		if(obj==null)return false;
		
		if(obj instanceof List){
			return (((List)obj).size()>0);
		}else if(obj instanceof String){
			return !((String) obj).trim().equals("");
		}
		
		return false;
	}
	
	public static Dialog showProgressBar(String msg, Activity act) {
		if((act == null) && (rootActivity == null)){
			return null;
		}else if(act == null){
			act = rootActivity;			
		}
		Dialog dialog = new Dialog(act);
		if(dialog != null){
			dialog.dismiss();
		}
		dialog = new Dialog(Utils.rootActivity, android.R.style.Theme_Translucent_NoTitleBar);
		dialog.setContentView(R.layout.dialog_progress);
		
		if(msg != null){
			TextView txtDialog = (TextView)dialog.findViewById(R.id.dialog_proccess_txt);
			txtDialog.setText(msg);
		}
		dialog.show();
		final Dialog tempDialog = dialog;
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				if(tempDialog != null){
					tempDialog.dismiss();
				}
			}
		}, 240000);
		return dialog;
	}
	
}
