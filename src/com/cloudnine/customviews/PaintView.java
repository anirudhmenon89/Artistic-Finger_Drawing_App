package com.cloudnine.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PaintView extends View {

	Paint paint;
	private Bitmap bmp;
	private Paint bmpPaint;
	private Canvas canvas;
	@SuppressWarnings("unused")
	private Context context;
	private float mX, mY;
	private Path path;
	private static final float TOUCH_TOLERANCE = 0.8f;
	private int colour;
	private Bitmap bgImage;
	protected Boolean pencil;
	private float strokeWidth = 1;


	public PaintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		setDrawingCacheEnabled(true); // to save images

		this.context = context;
		this.colour = Color.BLACK;

		this.path = new Path();
		this.bmpPaint = new Paint();
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setDither(true);
		this.paint.setColor(this.colour);
		this.paint.setStyle(Paint.Style.STROKE);
		this.paint.setStrokeJoin(Paint.Join.ROUND);
		this.paint.setStrokeCap(Paint.Cap.ROUND);
		this.paint.setStrokeWidth(strokeWidth);
	}

	public PaintView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		this.bgImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		this.bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		this.canvas = new Canvas(this.bmp);
	}

	private void touchStart(float x, float y) {
		this.path.reset();
		this.path.moveTo(x, y);
		this.mX = x;
		this.mY = y;
	}

	private void touchUp() {
		this.path.lineTo(mX, mY);
		// commit the path to our offscreen
		this.canvas.drawPath(this.path, paint);
		// kill this so we don't double draw
		this.path.reset();
	}

	private void touchMove(float x, float y) {
		float dx = Math.abs(x - this.mX);
		float dy = Math.abs(y - this.mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			// draws a quadratic curve
			this.path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();

		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			this.touchStart(x, y);
			this.touchMove(x + 0.8f, y + 0.8f);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			this.touchMove(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			this.touchUp();
			invalidate();
			break;
		}
		return true;
	}

	// Called on invalidate();
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(this.bgImage, 0, 0, this.bmpPaint);
		canvas.drawBitmap(this.bmp, 0, 0, this.bmpPaint);
		canvas.drawPath(this.path, this.paint);

	}

	/*
	 * Menu called methods
	 */
	public void togglePencil(Boolean b) {
		if (b) { // set pencil
			paint.setXfermode(null);
			this.pencil = true;
		} else { // set eraser
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			invalidate();
			this.pencil = false;
		}
		//DrawActivity.this.setTitle();
	}

	public void setColor(int c) {
		this.paint.setColor(c);
		this.colour = c;
	}
	
	public void setStroke(float size) {
		this.paint.setStrokeWidth(size);
		this.strokeWidth = size;
	}

	public int getColor() {
		return this.colour;
	}
	
	public float getStrokeWidth() {
		return strokeWidth;
	}

	public void clear() {
		this.path = new Path(); // empty path
		this.canvas.drawColor(Color.WHITE);
		if (this.bgImage != null) {
			this.canvas.drawBitmap(this.bgImage, 0, 0, null);
		}
		this.invalidate();
	}
}
