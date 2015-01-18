package com.cloudnine.activities;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bugsense.trace.BugSenseHandler;
import com.cloudnine.artistic.R;
import com.cloudnine.utils.ArtisticConstants;
import com.cloudnine.utils.ArtisticUtils;

public class AboutUs extends ArtisticActivity {

	private TextView mtvAppVersion;
	private TextView mtvRateUsText;
	private TextView mtvCreatedBy;
	
	private Button mbtnAboutUsClose;

	private Button mbtnRateNow;

	private RelativeLayout mllRootLayout;

	private ObjectAnimator moaFlip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);

		initViews();

		setFont();

		/* Set background */
		mllRootLayout.setBackgroundResource(blurWallpaper[ArtisticConstants.WALLPAPER_NUMBER]);

		/* Rate us colour */
		String colour = barColours[ArtisticConstants.WALLPAPER_NUMBER];
		mbtnRateNow.setBackgroundColor(Color.parseColor(colour));

		/* Set click listener */
		mbtnAboutUsClose.setOnClickListener(clickListener);
	}

	
	@Override
	protected void onStart() {
		super.onStart();
		BugSenseHandler.startSession(AboutUs.this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		BugSenseHandler.closeSession(AboutUs.this);
	}


	private void initViews() {
		mtvAppVersion = (TextView) findViewById(R.id.tvAboutUsVersion);
		mtvRateUsText = (TextView) findViewById(R.id.tvRateUsText);
		mtvCreatedBy = (TextView) findViewById(R.id.tvCreatedBy);

		mbtnRateNow = (Button) findViewById(R.id.btnRate);

		mllRootLayout = (RelativeLayout) findViewById(R.id.llAboutUsRootLayout);

		mbtnAboutUsClose = (Button) findViewById(R.id.btnAboutUsClose);

		moaFlip = (ObjectAnimator) AnimatorInflater.loadAnimator(AboutUs.this, R.animator.flip);
	}

	private void setFont() {
		mtvAppVersion.setTypeface(ArtisticUtils.getRobotoLight(AboutUs.this));
		mtvRateUsText.setTypeface(ArtisticUtils.getRobotoLight(AboutUs.this));
		mbtnRateNow.setTypeface(ArtisticUtils.getRobotoLight(AboutUs.this));
		mtvCreatedBy.setTypeface(ArtisticUtils.getRobotoLight(AboutUs.this));
	}

	@Override
	public void onBackPressed() {
		moaFlip.setTarget(mbtnAboutUsClose);
		moaFlip.setDuration(1000);
		moaFlip.start();
	}	

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnAboutUsClose: {
				finish();
				break;
			}
			}
		}
	};

}
