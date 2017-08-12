package com.song.shapebglib.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * description: 圆圈截取的的效果
 * author: taosongsong
 * date: 2017/8/7 13:59
 * update: 圆圈截取的的效果
 * version: 1.0
 */

public class LoadingPathView extends View {
    private Paint paint;
    private Path circlePath;
    private Path dstPath;
    private PathMeasure pathMeasure;
    private float value;


    public LoadingPathView(Context context) {
        this(context, null);
    }

    public LoadingPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        pathMeasure = new PathMeasure();
        circlePath = new Path();
        dstPath = new Path();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        circlePath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 20, Path.Direction.CW);
        pathMeasure.setPath(circlePath, false);
        float stop = pathMeasure.getLength() * value;
        float start = (float) (stop - ((0.5 - Math.abs(value - 0.5)) * pathMeasure.getLength()));
        //4.0 bug
        dstPath.reset();
        dstPath.lineTo(0, 0);
        pathMeasure.getSegment(start, stop, dstPath, true);
        canvas.drawPath(dstPath, paint);
    }
}
