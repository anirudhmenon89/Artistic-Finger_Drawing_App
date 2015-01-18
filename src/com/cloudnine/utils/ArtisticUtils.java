package com.cloudnine.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class ArtisticUtils {
	
	private static boolean mbShowLog = true;
	
	public static void showLongToast(String message, Context c) {
		Toast.makeText(c, message, Toast.LENGTH_LONG).show();
	}
	
	public static void showShortToast(String message, Context c) {
		Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
	}
	

	public static void Logi(String tag, String message) {
		if(mbShowLog) {
			Log.i(tag, message);
		}
	}
	
	public static void Loge(String tag, String message) {
		if(mbShowLog) {
			Log.e(tag, message);
		}
	}
	
	public static void Logv(String tag, String message) {
		if(mbShowLog) {
			Log.v(tag, message);
		}
	}
	
	public static Typeface getRobotoLight(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "robotolight.ttf");
	}
	
	public static int getScreenWidth(Activity a) {
		Display display = a.getWindowManager().getDefaultDisplay(); 
		int width = display.getWidth();  // deprecated
		
		return width;
	}
}
