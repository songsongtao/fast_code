package com.song.fast.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.song.fast.R;

/**
 * description: This is SquareLayout 矩形比例布局，根据宽高比设置大小
 * author: ss
 * date: 2017/11/27 8:47
 * update:
 * version: 1.0
 */
public class SquareLayout extends RelativeLayout {
    //宽高比
    float ratio = 1f;

    public SquareLayout(Context context) {
        this(context, null);
    }

    public SquareLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareLayout);
        ratio = typedArray.getFloat(R.styleable.SquareLayout_ratio, 1f);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
//        int childHeightSize = getMeasuredHeight();

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY);

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(
                (int) (childWidthSize * ratio), MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
