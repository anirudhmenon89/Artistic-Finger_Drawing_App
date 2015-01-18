package com.cloudnine.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.cloudnine.activities.ArtisticActivity;
import com.cloudnine.artistic.R;
import com.cloudnine.utils.ArtisticUtils;

public class StrokeSeekBar extends ArtisticActivity {

	private boolean mbisPencil;
	
	private float mfOldStokeWidth;

	private TextView mtvStrokeValue;
	private TextView mtvPencilEraser;

	private SeekBar msbStroke;

	private ImageButton mbtnSetStroke;
	private ImageButton mbtnCancelStroke;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_strokewidth);

		initViews();
		setFont();

		/* Set dialog width */
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.width = ArtisticUtils.getScreenWidth(StrokeSeekBar.this);  
		this.getWindow().setAttributes(params); 

		/* Set seekbar value */
		if(getIntent()!=null){
			int strokeWidth = (int) getIntent().getExtras().getFloat("strokeWidth");
			mbisPencil = getIntent().getBooleanExtra("isPencil", true);
			msbStroke.setProgress(strokeWidth);
		}
		
		if(mbisPencil) {
			mtvPencilEraser.setText("pencil");
		} else {
			mtvPencilEraser.setText("eraser");
		}
		
		/* Get current stroke width from DrawScreen.java */
		mfOldStokeWidth = getIntent().getExtras().getFloat("strokeWidth");
		mtvStrokeValue.setText("Stroke Width: "+mfOldStokeWidth);


		/* Seekbar change listener */
		msbStroke.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				mtvStrokeValue.setText("Stroke Width: "+progress);
			}
		});

		/* Click listener */
		mbtnSetStroke.setOnClickListener(clickListener);
		mbtnCancelStroke.setOnClickListener(clickListener);
	}


	private void initViews() {
		mtvStrokeValue = (TextView) findViewById(R.id.tvStrokeValue);
		mtvPencilEraser = (TextView) findViewById(R.id.tvPencilEraser);

		msbStroke = (SeekBar) findViewById(R.id.sbStroke);

		mbtnSetStroke = (ImageButton) findViewById(R.id.btnSetStroke);
		mbtnCancelStroke = (ImageButton) findViewById(R.id.btnCancelStroke);
	}

	private void setFont() {
		mtvStrokeValue.setTypeface(getFontRobotoLight());
		mtvPencilEraser.setTypeface(getFontChubgothic());
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSetStroke: {
				Intent i = new Intent();
				i.putExtra("strokeWidth", (float)msbStroke.getProgress());
				setResult(Activity.RESULT_OK, i);
				finish();
				break;
			}

			case R.id.btnCancelStroke: {
				setResult(Activity.RESULT_CANCELED);
				finish();
				break;
			}

			}

		}
	};

}
