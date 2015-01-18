package com.cloudnine.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;

import com.bugsense.trace.BugSenseHandler;
import com.cloudnine.artistic.R;

public class ArtisticActivity extends Activity {
	
	private boolean mbShowLog = true;
	
	protected int[] blurWallpaper = {
		R.drawable.splashimage0blur, 
		R.drawable.splashimage1blur, 
		R.drawable.splashimage2blur, 
		R.drawable.splashimage3blur, 
		R.drawable.splashimage4blur, 
		R.drawable.splashimage5blur, 
		R.drawable.splashimage6blur, 
		R.drawable.splashimage7blur, 
		R.drawable.splashimage8blur, 
		R.drawable.splashimage9blur, 
		R.drawable.splashimage10blur, 
		R.drawable.splashimage11blur
	};

	protected int[] normalWallpaper = {
		R.drawable.splashimage0, 
		R.drawable.splashimage1, 
		R.drawable.splashimage2,
		R.drawable.splashimage3, 
		R.drawable.splashimage4, 
		R.drawable.splashimage5, 
		R.drawable.splashimage6, 
		R.drawable.splashimage7, 
		R.drawable.splashimage8, 
		R.drawable.splashimage9, 
		R.drawable.splashimage10, 
		R.drawable.splashimage11
	};
	
	protected String[] barColours = {
		"#d39876",
		"#2c630d",
		"#f57131",
		"#060606",
		"#9fa7b2",
		"#4a424a",
		"#0d2d88",
		"#880d87",
		"#4FA856",
		"#4FA856",
		"#d1862d",
		"#d1862d",
	};

	/* Draw values */
	protected int COLOUR = Color.BLACK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(ArtisticActivity.this, "e615a761");
	}
	
	protected void Logi(String tag, String message) {
		if(mbShowLog)
			Log.i(tag, message);
	}
	
	protected void Loge(String tag, String message) {
		if(mbShowLog)
			Log.e(tag, message);
	}
	
	protected Typeface getFontChubgothic() {
		return Typeface.createFromAsset(getAssets(), "chubgothic1.ttf");
	}
	
	protected Typeface getFontRobotoLight() {
		return Typeface.createFromAsset(getAssets(), "robotolight.ttf");
	}
}
