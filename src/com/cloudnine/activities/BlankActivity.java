package com.cloudnine.activities;

import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.cloudnine.artistic.R;
import com.cloudnine.utils.ArtisticConstants;

public class BlankActivity extends ArtisticActivity {
	
	private final int MAX_INT_RANDOM = 11;
	private final int MIN_INT_RANDOM = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER,
                WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER);
		
		setContentView(R.layout.activity_blankactivity);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try{
					Random rand = new Random();
					ArtisticConstants.WALLPAPER_NUMBER = rand.nextInt((MAX_INT_RANDOM - MIN_INT_RANDOM) + 1) + MIN_INT_RANDOM;
					
					Intent i = new Intent(BlankActivity.this, SplashScreen.class);
					
					Thread.sleep(500);
					startActivity(i);
					overridePendingTransition(R.anim.nextactivity, R.anim.currentactivity);
					finish();
				} catch(InterruptedException ie) {
					
				}
			}
		}).start();
	}

}
