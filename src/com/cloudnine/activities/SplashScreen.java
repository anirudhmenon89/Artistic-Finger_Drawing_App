package com.cloudnine.activities;

import java.util.Random;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bugsense.trace.BugSenseHandler;
import com.cloudnine.artistic.R;
import com.cloudnine.customviews.ParallaxImageView;
import com.cloudnine.customviews.Titanic;
import com.cloudnine.utils.ArtisticConstants;

public class SplashScreen extends ArtisticActivity {

	private final int MAX_INT_RANDOM = 11;
	private final int MIN_INT_RANDOM = 0;
	
	private Button mbtnEnter;

	private ParallaxImageView mivBlurBackground;
	private ImageView mivDraw;
	private ImageView mivArtistic;

	private Titanic mTitanic;

	private Typeface mTypeface;

	private RelativeLayout mRelativeLayout;

	private Animation mAnimationBackground;
	private Animation mAnimationArtisticAlpha;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

		Random rand = new Random();
		ArtisticConstants.WALLPAPER_NUMBER = rand.nextInt((MAX_INT_RANDOM - MIN_INT_RANDOM) + 1) + MIN_INT_RANDOM;

		initViews();

		setBackground(ArtisticConstants.WALLPAPER_NUMBER);

		mivBlurBackground.setAnimation(mAnimationBackground); 

		mAnimationBackground.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mivArtistic.setVisibility(View.VISIBLE);
				mbtnEnter.setVisibility(View.VISIBLE);
				mivArtistic.setAnimation(mAnimationArtisticAlpha);
				mbtnEnter.setAnimation(mAnimationArtisticAlpha);

			}
		});

		/* Set fonts */
		mbtnEnter.setTypeface(mTypeface);

		/* Set listener */
		mbtnEnter.setOnClickListener(clickListener);

		/* Parallax blur image */
		mivBlurBackground.registerSensorManager();
		mivBlurBackground.setForwardTiltOffset(.35f);
		mivBlurBackground.setParallaxIntensity(10.0f);
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		BugSenseHandler.startSession(SplashScreen.this);
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
		BugSenseHandler.closeSession(SplashScreen.this);
	}


	@Override
	protected void onResume() {
		super.onResume();

	}


	@Override
	protected void onPause() {
		super.onPause();
	}


	private void initViews() {
		mbtnEnter = (Button) findViewById(R.id.btnEnter);

		mivBlurBackground = (ParallaxImageView) findViewById(R.id.ivBlurBackground);
		mivArtistic = (ImageView) findViewById(R.id.ivArtistic);

		mTitanic = new Titanic();

		mRelativeLayout = (RelativeLayout) findViewById(R.id.rlSplashRoot);

		mTypeface = Typeface.createFromAsset(getAssets(), "chubgothic1.ttf");

		mAnimationBackground = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bluralpha);
		mAnimationArtisticAlpha = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.artisticalpha);

	}


	private void setBackground(int number) {
		switch (number) {
		case 0: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage0);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage0blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#d39876"));
			break;
		}

		case 1: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage1);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage1blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#2c630d"));
			break;
		}

		case 2: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage2);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage2blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#f57131"));
			break;
		}
		case 3: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage3);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage3blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#060606"));
			break;
		}

		case 4: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage4);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage4blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#9fa7b2"));
			break;
		}

		case 5: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage5);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage5blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#4a424a"));
			break;
		}

		case 6: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage6);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage6blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#0d2d88"));
			break;
		}

		case 7: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage7);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage7blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#880d87"));
			break;
		}

		case 8: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage8);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage8blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#4FA856"));
			break;
		}

		case 9: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage9);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage9blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#4FA856"));
			break;
		}

		case 10: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage10);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage10blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#d1862d"));
			break;
		}

		case 11: {
			mRelativeLayout.setBackgroundResource(R.drawable.splashimage11);
			mivBlurBackground.setBackgroundResource(R.drawable.splashimage11blur);
			mbtnEnter.setBackgroundColor(Color.parseColor("#d1862d"));
			break;
		}
		default:
			break;
		}
	}


	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnEnter: {
				Intent i = new Intent(SplashScreen.this, DrawScreen.class);
				startActivity(i);
				overridePendingTransition(R.anim.nextactivity, R.anim.currentactivity);
				finish();
			}
			}
		}
	};

}
