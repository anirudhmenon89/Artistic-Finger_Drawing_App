package com.cloudnine.customviews;

import android.animation.TimeInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

public class ArtisticBounce extends BounceInterpolator implements Interpolator {
	
	@Override
	public float getInterpolation(float t) {
		float x = 2.0f * t - 1.0f;
        return 0.5f * ( x + 1.0f);
	}
	
	

}
