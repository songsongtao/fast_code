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
 * description: 动态画出成功的路径
 * author: taosongsong
 * date: 2017/8/7 13:58
 * update: 动态画出成功的路径
 * version: 1.0
*/

public class SuccessView extends View {
    private PathMeasure pathMeasure;
    private Paint paint;
    //圆
    private Path circlePath;
    //xx
    private Path linePath;
    //存放截取的线段
    private Path dst1;
    private Path dst2;
    //动态的数值
    private float value1;
    private float value2;
    //半径


    public SuccessView(Context context) {
        this(context, null);
    }

    public SuccessView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        pathMeasure = new PathMeasure();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10f);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        circlePath = new Path();
        linePath = new Path();
        dst1 = new Path();
        dst2 = new Path();


        final ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0, 1);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value2 = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator2.setDuration(500);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                value1 = (float) valueAnimator.getAnimatedValue();
                invalidate();
                if (value1 == 1) {
                    valueAnimator2.start();
                }
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //减去的是边框本身的宽度
        circlePath.addCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 20, Path.Direction.CW);
        pathMeasure.setPath(circlePath, false);
        pathMeasure.getSegment(0, value1 * pathMeasure.getLength(), dst1, true);
        canvas.drawPath(dst1, paint);

        if (value1 == 1) {
            linePath.moveTo(getWidth() / 3, getWidth() / 2);
            linePath.lineTo(getWidth() / 2, getWidth() / 5 * 3);
            linePath.lineTo(getWidth() / 3 * 2, getWidth() / 5 * 2);
            pathMeasure.nextContour();
            pathMeasure.setPath(linePath, false);
            pathMeasure.getSegment(0, value2 * pathMeasure.getLength(), dst2, true);
            canvas.drawPath(dst2, paint);
        }


    }
}
