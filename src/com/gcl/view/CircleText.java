package com.gcl.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;

public class CircleText extends TextView {

	private Paint circlePaint;

	public CircleText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		circlePaint = new Paint();
		circlePaint.setStyle(Style.STROKE);
		circlePaint.setColor(0xff666666);
		circlePaint.setStrokeWidth(2);
		circlePaint.setAntiAlias(true);
	}

	public CircleText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleText(Context context) {
		this(context, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		int w = getWidth();
		int h = getHeight();
		int r = Math.min(w, h) - 1;

		canvas.drawCircle(w / 2, h / 2, r / 2, circlePaint);

		super.onDraw(canvas);
	}

}
