package com.cloudnine.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloudnine.activities.ArtisticActivity;
import com.cloudnine.artistic.R;
import com.cloudnine.utils.ArtisticConstants;
import com.cloudnine.utils.ArtisticUtils;

public class AlertDialog extends ArtisticActivity {

	private String msLineColour;
	private String msAlertMessage;
	private String msAlertTitle;

	private TextView mtvAlertTitle; 
	private TextView mtvAlertMessage;

	private View mvLine;

	private ImageButton mibOK;
	private ImageButton mibCancel;

	private RelativeLayout mrlDialogRoot;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_alert);

		initViews();
		setFonts();
		getIntentData();

		mtvAlertMessage.setText(msAlertMessage);
		mtvAlertTitle.setText(msAlertTitle);
		mvLine.setBackgroundColor(Color.parseColor(msLineColour));

		/* Set dialog width */
		WindowManager.LayoutParams params = getWindow().getAttributes();  
		params.width = ArtisticUtils.getScreenWidth(AlertDialog.this);  
		this.getWindow().setAttributes(params); 

		/* Click listener */
		mibCancel.setOnClickListener(clickListener);
		mibOK.setOnClickListener(clickListener);

	}


	private void initViews() {
		mtvAlertMessage = (TextView) findViewById(R.id.tvAlertMessage);
		mtvAlertTitle = (TextView) findViewById(R.id.tvAlertTitle);

		mvLine = findViewById(R.id.line);

		mibCancel = (ImageButton) findViewById(R.id.btnAlertCancel);
		mibOK = (ImageButton) findViewById(R.id.btnAlertOK);

		mrlDialogRoot = (RelativeLayout) findViewById(R.id.rldialogRoot);

		intent = new Intent();
	}

	private void setFonts() {
		mtvAlertMessage.setTypeface(ArtisticUtils.getRobotoLight(AlertDialog.this));
		mtvAlertTitle.setTypeface(ArtisticUtils.getRobotoLight(AlertDialog.this));
	}

	private void getIntentData() {
		msAlertMessage = getIntent().getStringExtra("alertmessage");
		msAlertTitle = getIntent().getStringExtra("alerttitle");

		msLineColour = barColours[ArtisticConstants.WALLPAPER_NUMBER];
	}


	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.btnAlertCancel: {
				intent.putExtra("alertaction", 0);
				setResult(Activity.RESULT_OK, intent);
				finish();

				break;
			}

			case R.id.btnAlertOK: {
				intent.putExtra("alertaction", 1);
				setResult(Activity.RESULT_OK, intent);
				finish();

				break;
			}
			}
		}
	};
}
