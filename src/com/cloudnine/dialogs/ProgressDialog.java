package com.cloudnine.dialogs;

import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudnine.activities.ArtisticActivity;
import com.cloudnine.artistic.R;
import com.cloudnine.utils.ArtisticConstants;

public class ProgressDialog extends ArtisticActivity {
	
	private TextView mtvPrepareImage;
	
	private ImageView mivCircle;
	
	private Animation mAnimLeft;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_progress);
		
		initViews();
		
		String colour = barColours[ArtisticConstants.WALLPAPER_NUMBER];
		
		
		mivCircle.setAnimation(mAnimLeft);
		mivCircle.startAnimation(mAnimLeft);
	}
	
	
	private void initViews() {
		mtvPrepareImage = (TextView) findViewById(R.id.tvPreparingImage);
		
		mAnimLeft = AnimationUtils.loadAnimation(ProgressDialog.this, R.anim.progressbluranimation);
		
		mivCircle = (ImageView) findViewById(R.id.ivCircle);
	}

}
