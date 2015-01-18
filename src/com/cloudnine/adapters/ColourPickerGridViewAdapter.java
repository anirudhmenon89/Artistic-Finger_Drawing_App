package com.cloudnine.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cloudnine.artistic.R;

public class ColourPickerGridViewAdapter extends DrawAdapter {
	
	private Context mContext;
	
	private LayoutInflater mInflater;
	
	private String[] msarrColourList;
	
	
	public ColourPickerGridViewAdapter(Context context, String[] colourList){
		mContext = context;
		msarrColourList = colourList;
		mCount = msarrColourList.length;
		
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		
		Holder holder;
		
		if (view == null) {
			view = mInflater.inflate(R.layout.adapter_gridviewcolourlist, null);
			holder = new Holder();
			
			holder.b = (Button) view.findViewById(R.id.btnColour);

			view.setTag(holder);
		}
		
		holder = (Holder) view.getTag();
		
		GradientDrawable sd = (GradientDrawable) holder.b.getBackground().mutate();
		sd.setColor(Color.parseColor(msarrColourList[position]));
		sd.invalidateSelf();
		//holder.b.setBackgroundColor(Color.parseColor(msarrColourList[position]));
		
		return view;
	}
	
	class Holder {
		Button b;
	}

}
