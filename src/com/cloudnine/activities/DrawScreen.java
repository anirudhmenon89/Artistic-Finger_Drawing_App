package com.cloudnine.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.cloudnine.artistic.R;
import com.cloudnine.customviews.PaintView;
import com.cloudnine.dialogs.AlertDialog;
import com.cloudnine.dialogs.ColourPicker;
import com.cloudnine.dialogs.StrokeSeekBar;
import com.cloudnine.utils.ArtisticConstants;
import com.cloudnine.utils.ArtisticUtils;

public class DrawScreen extends ArtisticActivity {

	private final int REQUEST_CODE_COLOUR = 1;
	private final int REQUEST_CODE_STROKE = 2;
	private final int REQUEST_CODE_CLEAR_CANVAS = 3;
	private final int SHOW_SUCCESS_TOAST = 1;
	private final int SHOW_FAILURE_TOAST = 0;
	private int miWidth;
	private int miHeight;

	private boolean mbIsPencil = true;
	private boolean mbShowMenu;
	private boolean mbShowDrawMenu;
	private boolean mbShowSuccesToast;

	private TextView mtvActionBarAppName;
	private TextView mtvMenuClear;
	private TextView mtvMenuSave;
	private TextView mtvMenuShare;
	private TextView mtvMenuAbout;
	private TextView mtvPencil;
	private TextView mtvEraser;
	private TextView mtvColour;

	private ImageView mivPencilEraser;
	private ImageView mivShowMenu;
	private ImageView mivDrawMenu;

	private ImageButton mibMenuClear;
	private ImageButton mibMenuSave;
	private ImageButton mibMenuShare;
	private ImageButton mibMenuAbout;
	private ImageButton mibAMenuPencil;
	private ImageButton mibAMenuEraser;
	private ImageButton mibAMenuColour;

	private LinearLayout mllActionbar;
	private LinearLayout mllActionBarMenu;
	private RelativeLayout mrlMainLayout;
	private View mvTempPaint;

	private ScrollView msvDrawerScroll;

	private ProgressBar mProgressBar;
	
	private PaintView mPaintView;

	private Animation manimDrawMenuShow;
	private Animation manimDrawMenuHide;
	private Animation manimShowDrawIcon;
	private Animation manimHideDrawIcon;
	
	private Uri mUri;

	private Intent miDrawerActivity;

	private WeakReference<Bitmap> mwrbmpDrawImage;

	class AsyncSaveImage extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			mwrbmpDrawImage = new WeakReference<Bitmap>(getBitmapFromView(mPaintView));
			savePhoto(mwrbmpDrawImage.get());
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if(!mbShowSuccesToast) {
				mbShowSuccesToast = true;
				showToast(SHOW_FAILURE_TOAST);
			} else if(mbShowSuccesToast) {
				mbShowSuccesToast = true;
				showToast(SHOW_SUCCESS_TOAST);
			}
			mProgressBar.setVisibility(View.GONE);
			hideMenu(mrlMainLayout);
		}
		
		

	}
	
	class AsyncShareImage extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			mwrbmpDrawImage = new WeakReference<Bitmap>(getBitmapFromView(mPaintView));
			savePhoto(mwrbmpDrawImage.get());
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			mProgressBar.setVisibility(View.GONE);
			shareImage();
			hideMenu(mrlMainLayout);
		}

	}

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drawscreen);

		initViews();
		
		setBackground(ArtisticConstants.WALLPAPER_NUMBER);

		setActionBarColour();
		setFont();

		//TODO get proper height and width for older versions
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		miWidth = size.x;
		miHeight = size.y;

		/* Click listener */
		mivShowMenu.setOnClickListener(clickListener);
		mivPencilEraser.setOnClickListener(clickListener);
		mPaintView.setOnClickListener(clickListener);
		mvTempPaint.setOnClickListener(clickListener);
		mibMenuAbout.setOnClickListener(clickListener);
		mibMenuClear.setOnClickListener(clickListener);
		mibMenuShare.setOnClickListener(clickListener);
		mibMenuSave.setOnClickListener(clickListener);
		mibAMenuColour.setOnClickListener(clickListener);
		mibAMenuEraser.setOnClickListener(clickListener);
		mibAMenuPencil.setOnClickListener(clickListener);
		mivDrawMenu.setOnClickListener(clickListener);
	}

	@Override
	protected void onStart() {
		super.onStart();
		BugSenseHandler.startSession(DrawScreen.this);
	}


	@Override
	protected void onResume() {
		super.onResume();
	}


	@Override
	protected void onStop() {
		super.onStop();
		BugSenseHandler.closeSession(DrawScreen.this);
	}


	@Override
	public void onBackPressed() {
		if(mbShowMenu == true)
			hideMenu(mrlMainLayout);
		else
			super.onBackPressed();
	}


	private void initViews() {
		mbShowMenu = false;
		mbShowDrawMenu = false;
		mbShowSuccesToast = true;

		mtvActionBarAppName = (TextView) findViewById(R.id.tvActionbarAppName);
		mtvMenuAbout = (TextView) findViewById(R.id.tvmenuAbout);
		mtvMenuClear = (TextView) findViewById(R.id.tvmenuClear);
		mtvMenuShare = (TextView) findViewById(R.id.tvmenuShare);
		mtvMenuSave = (TextView) findViewById(R.id.tvmenuSave);
		mtvColour = (TextView) findViewById(R.id.tvcolourText);
		mtvEraser = (TextView) findViewById(R.id.tveraserText);
		mtvPencil = (TextView) findViewById(R.id.tvpencilText);

		mibMenuAbout = (ImageButton) findViewById(R.id.ibmenuAbout);
		mibMenuClear = (ImageButton) findViewById(R.id.ibmenuClear);
		mibMenuShare = (ImageButton) findViewById(R.id.ibmenuShare);
		mibMenuSave = (ImageButton) findViewById(R.id.ibmenuSave);
		mibAMenuPencil = (ImageButton) findViewById(R.id.ibpencilIcon);
		mibAMenuEraser = (ImageButton) findViewById(R.id.iberaserIcon);
		mibAMenuColour = (ImageButton) findViewById(R.id.ibcolourIcon);

		mllActionbar = (LinearLayout) findViewById(R.id.llActionBar);
		mllActionBarMenu = (LinearLayout) findViewById(R.id.llArtisticMenu);

		mrlMainLayout = (RelativeLayout) findViewById(R.id.mainLayout);

		//mlvDrawerList = (ListView) findViewById(R.id.lvDrawer);
		msvDrawerScroll = (ScrollView) findViewById(R.id.svDrawer);

		mPaintView = (PaintView) findViewById(R.id.paintView);
		
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

		mivPencilEraser = (ImageView) findViewById(R.id.ivPencilEraser);
		mivShowMenu = (ImageView) findViewById(R.id.ivShowMenu);
		mvTempPaint = findViewById(R.id.vTempPaint);
		mivDrawMenu = (ImageView) findViewById(R.id.ivDrawMenu);

		manimDrawMenuShow = AnimationUtils.loadAnimation(DrawScreen.this, R.anim.drawmenuanimationshow);
		manimDrawMenuHide = AnimationUtils.loadAnimation(DrawScreen.this, R.anim.drawmenuanimationhide);
		manimShowDrawIcon = AnimationUtils.loadAnimation(DrawScreen.this, R.anim.drawmenushowicon);
		manimHideDrawIcon = AnimationUtils.loadAnimation(DrawScreen.this, R.anim.drawmenuhideicon);
	}


	private void setActionBarColour() {
		String colour = barColours[ArtisticConstants.WALLPAPER_NUMBER];
		mllActionbar.setBackgroundColor(Color.parseColor(colour));
		mllActionBarMenu.setBackgroundColor(Color.parseColor(colour));
		
		mibAMenuColour.setBackgroundResource(R.drawable.layoutbackground);
		mibAMenuPencil.setBackgroundResource(R.drawable.layoutbackground);
		mibAMenuEraser.setBackgroundResource(R.drawable.layoutbackground);
		mivShowMenu.setBackgroundResource(R.drawable.layoutbackground);
	}


	private void setBackground(int number) {
		int background = blurWallpaper[ArtisticConstants.WALLPAPER_NUMBER];
		msvDrawerScroll.setBackgroundResource(background);
	}


	private void setFont(){ 
		mtvMenuAbout.setTypeface(getFontRobotoLight());
		mtvMenuClear.setTypeface(getFontRobotoLight());
		mtvMenuSave.setTypeface(getFontRobotoLight());
		mtvActionBarAppName.setTypeface(getFontRobotoLight());
		mtvColour.setTypeface(getFontRobotoLight());
		mtvPencil.setTypeface(getFontRobotoLight());
		mtvEraser.setTypeface(getFontRobotoLight());
	}
	
	
	/* Get bitmap from draw view */
	public static Bitmap getBitmapFromView(View view) {

		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable =view.getBackground();
		if (bgDrawable!=null) 
			bgDrawable.draw(canvas);
		else 
			canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		return returnedBitmap;
	}

	
	private void showToast(int type) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = null;
		if(type == SHOW_SUCCESS_TOAST)
			layout = inflater.inflate(R.layout.toast_success, null);
		else
			layout = inflater.inflate(R.layout.toast_failure, null);
		
		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	/* Save image */
	private String savePhoto(Bitmap bmp)
	{
		File path = new File(Environment.getExternalStorageDirectory(),"artistic");
		path.mkdir();
		FileOutputStream out = null;
		Calendar c = Calendar.getInstance();
		String date = fromInt(c.get(Calendar.MONTH))
				+ fromInt(c.get(Calendar.DAY_OF_MONTH))
				+ fromInt(c.get(Calendar.YEAR))
				+ fromInt(c.get(Calendar.HOUR_OF_DAY))
				+ fromInt(c.get(Calendar.MINUTE))
				+ fromInt(c.get(Calendar.SECOND));
		File imageFileName = new File(path, date.toString() + ".jpg"); //imageFileFolder
		mUri = Uri.fromFile(imageFileName);
		try
		{
			out = new FileOutputStream(imageFileName);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
			scanPhoto(imageFileName.toString());
			out = null;
		} catch (Exception e)
		{
			Log.e("DrawScreen","Exception in saving");
			e.printStackTrace();
			mbShowSuccesToast = false;
		}
		
		return imageFileName.toString();
	}
	private String fromInt(int val)
	{
		return String.valueOf(val);
	}

	/* invoke the system's media scanner to add your photo to the Media Provider's database,
	 * making it available in the Android Gallery application and to other apps. */
	private void scanPhoto(String imageFileName)
	{
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(imageFileName);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
	}

	
	private void shareImage() {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, mUri);
		shareIntent.setType("image/jpeg");
		startActivity(Intent.createChooser(shareIntent, "Share with"));
	}
	

	private void showMenu(RelativeLayout view) {
		mbShowMenu = true;
		int time = 200;

		mvTempPaint.setVisibility(View.VISIBLE);
		view.animate().scaleX(0.5f).setDuration(time);
		view.animate().scaleY(0.5f).setDuration(time);
		view.animate().translationX(((float)50/100)*miWidth).setDuration(time);
		//view.animate().setInterpolator(new ArtisticBounce());
	}


	private void hideMenu(RelativeLayout view) {
		mbShowMenu = false;
		int time = 200;

		mvTempPaint.setVisibility(View.GONE);
		view.animate().scaleX(1.0f).setDuration(time);
		view.animate().scaleY(1.0f).setDuration(time);
		view.animate().translationX(0).setDuration(time);
		view.animate().setInterpolator(null);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == Activity.RESULT_OK) { 
			/** Choose Colour
			 * returns - String - colour */
			if(requestCode == REQUEST_CODE_COLOUR) {
				if(data != null) {
					String colour = data.getStringExtra("colourString");
					mPaintView.setColor(Color.parseColor(colour));
				} else {
					ArtisticUtils.showShortToast("data intent is null", DrawScreen.this);
				}
			}

			/** Change stroke width
			 * return - float - stroke value */
			else if(requestCode == REQUEST_CODE_STROKE)  {
				if(data != null) {
					Logi("DrawScreen","Stroke: "+data.getFloatExtra("strokeWidth", 1.0f));
					mPaintView.setStroke(data.getFloatExtra("strokeWidth", 1.0f));
				}
			}

			/** Alert message 
			 * returns - int - 1 if OK
			 * 			 int - 0 if Cancel 
			 **/
			else if(requestCode == REQUEST_CODE_CLEAR_CANVAS) {
				if(data != null){
					int value = data.getIntExtra("alertaction", 0);
					if(value == 1) {
						mPaintView.clear();
						ArtisticUtils.showShortToast("Canvas cleared", DrawScreen.this);
					}
				}
			}
		}
	}


	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			case R.id.paintView: {
				hideMenu(mrlMainLayout);
				ArtisticUtils.showShortToast("Relative click", DrawScreen.this);
				break;
			}

			case R.id.ivShowMenu: {
				if(mbShowMenu == false)
					showMenu(mrlMainLayout);
				else
					hideMenu(mrlMainLayout);
				break;
			}

			case R.id.vTempPaint: {
				hideMenu(mrlMainLayout);
				break;
			}

			case R.id.ibmenuAbout: {
				Intent intent = new Intent(DrawScreen.this, AboutUs.class);
				startActivity(intent);
				overridePendingTransition(R.anim.nextactivity, R.anim.currentactivity);
				break;
			}

			case R.id.ibmenuClear: {
				hideMenu(mrlMainLayout);
				miDrawerActivity = new Intent(DrawScreen.this, AlertDialog.class);
				miDrawerActivity.putExtra("alertmessage", "Are you sure? This will remove all unsaved content");
				miDrawerActivity.putExtra("alerttitle", "Clear Canvas");
				startActivityForResult(miDrawerActivity, REQUEST_CODE_CLEAR_CANVAS);
				break;
			}

			case R.id.ibmenuShare: {
				new AsyncShareImage().execute(null, null, null);
				break;
			}

			case R.id.ibmenuSave: {
				new AsyncSaveImage().execute(null, null, null);
				break;
			}


			case R.id.iberaserIcon: { //Use eraser
				mPaintView.togglePencil(false);
				mbIsPencil = false;
				mivPencilEraser.setImageResource(R.drawable.iseraser);
				break;
			}


			case R.id.ibpencilIcon: { //Use pencil
				mPaintView.togglePencil(true);
				mbIsPencil = true;
				mivPencilEraser.setImageResource(R.drawable.ispencil);
				break;
			}

			case R.id.ibcolourIcon: { //Colour pick
				miDrawerActivity = new Intent(DrawScreen.this, ColourPicker.class);
				startActivityForResult(miDrawerActivity, REQUEST_CODE_COLOUR);
				break;
			}

			case R.id.ivDrawMenu: {
				if(!mbShowDrawMenu) {
					mllActionBarMenu.setVisibility(View.VISIBLE);
					mllActionBarMenu.setAnimation(manimDrawMenuShow);
					mllActionBarMenu.startAnimation(manimDrawMenuShow);

					mivDrawMenu.setAnimation(manimShowDrawIcon);
					mivDrawMenu.startAnimation(manimShowDrawIcon);

					mvTempPaint.setVisibility(View.VISIBLE);
					mbShowDrawMenu = true;
				} else if(mbShowDrawMenu) {
					mllActionBarMenu.setAnimation(manimDrawMenuHide);
					mllActionBarMenu.startAnimation(manimDrawMenuHide);
					mllActionBarMenu.setVisibility(View.GONE);
					mbShowDrawMenu = false;
					mivDrawMenu.setAnimation(manimHideDrawIcon);
					mivDrawMenu.startAnimation(manimHideDrawIcon);

					mvTempPaint.setVisibility(View.GONE);
					Log.i("DrawScreen.java","Inisible");
				}
				break;
			}
			
			case R.id.ivPencilEraser: { //Set stroke for pencil/eraser
				miDrawerActivity = new Intent(DrawScreen.this, StrokeSeekBar.class);
				miDrawerActivity.putExtra("strokeWidth", mPaintView.getStrokeWidth());
				if(mbIsPencil) {
					miDrawerActivity.putExtra("isPencil", true);
				} else {
					miDrawerActivity.putExtra("isPencil", false);
				}
				startActivityForResult(miDrawerActivity, REQUEST_CODE_STROKE);
				break;
			}

			}
		}
	};
}
