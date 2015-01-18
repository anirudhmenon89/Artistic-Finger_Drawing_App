package com.cloudnine.dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.cloudnine.adapters.ColourPickerGridViewAdapter;
import com.cloudnine.artistic.R;

public class ColourPicker extends Activity {
	
	private final String TAG = "COLOUR PICKER";
	
	private GridView mgvColourPicker;
	
	private String[] msarrColourList = {
			"#000000", "#FF0000", "#00FFFF", "#0000FF", "#ADD8E6",
			"#800080", "#FFFF00", "#00FF00", "#FF00FF"
		};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_colourpicker);
		
		initViews();
		
		mgvColourPicker.setAdapter(new ColourPickerGridViewAdapter(ColourPicker.this, msarrColourList));
		
		mgvColourPicker.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent();
				i.putExtra("colourString", msarrColourList[position]);
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
	}
	
	
	private void initViews() {
		mgvColourPicker = (GridView) findViewById(R.id.gvColourPicker);
	}
}
