package com.cloudnine.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class DrawAdapter extends BaseAdapter {
	
	protected int mCount;

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public abstract View getView(int position, View view, ViewGroup viewGroup);

}
