package com.graphsfm.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
public class TimerAnimationView extends CompoundButton {
	private static final String QUOTE =
        "Nobody uses Java anymore. It's this big heavyweight ball and chain.";

    private Animation anim;

    public TimerAnimationView(Context context) {
        super(context);
    }
    public TimerAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private void createAnim(Canvas canvas) {
        anim = new RotateAnimation(0, 360, canvas.getWidth() / 2, canvas
                .getHeight() / 2);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(10000L);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());

        startAnimation(anim);
    }
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
    	setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);	
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // creates the animation the first time
        if (anim == null) {
            createAnim(canvas);
        }

        Path circle = new Path();

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;
        int r = Math.min(centerX, centerY);

        circle.addCircle(centerX, centerY, r, Direction.CW);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        //canvas.drawCircle(circle, paint);
        canvas.drawCircle(centerX, centerY, r, paint);
        //canvas.drawTextOnPath(QUOTE, circle, 0, 30, paint);
    }

}
