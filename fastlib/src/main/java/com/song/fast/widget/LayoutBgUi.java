package com.song.fast.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.song.fast.R;


/**
 * description: 圆角按压效果布局
 * author: ss
 * date: 2017/8/2 16:53
 * version: 1.0
 */

public class LayoutBgUi extends LinearLayout {
    private Context mContext;
    //边框宽度
    private int strokeWidth;
    //边框颜色
    private int strokeColor;
    //边框选中颜色
    private int selectStrokeColor;
    //圆角角度
    private float raoundRadius;
    //默认背景颜色
    private int defaultColor;
    //按下背景颜色
    private int pressedColor;
    //是否启用水波纹效果
    private boolean isRipple;
    //变暗或变亮的参数
    private float parameter;
    //各个方向圆角
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;


    public LayoutBgUi(Context context) {
        this(context, null);
    }

    public LayoutBgUi(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LayoutBgUi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttr(attrs);
        Drawable colorDrawable = getColorDrawable();
        if (colorDrawable != null) {
            this.setBackground(colorDrawable);
        }

    }

    private void initAttr(AttributeSet attrs) {
        //获取自定义的属性
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.LayoutBgUi);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.LayoutBgUi_layoutStrokeWidth, 0);
        strokeColor = typedArray.getColor(R.styleable.LayoutBgUi_layoutStrokeColor, 0);
        selectStrokeColor = typedArray.getColor(R.styleable.LayoutBgUi_layoutSelectStrokeColor, 0);
        pressedColor = typedArray.getColor(R.styleable.LayoutBgUi_layoutPressedColor, 0);
        defaultColor = typedArray.getColor(R.styleable.LayoutBgUi_layoutDefaultColor, 0);
        isRipple = typedArray.getBoolean(R.styleable.LayoutBgUi_layoutIsRipple, true);
        parameter = typedArray.getFloat(R.styleable.LayoutBgUi_layoutParameter, 0.2f);
        raoundRadius = typedArray.getDimensionPixelOffset(R.styleable.LayoutBgUi_layoutRoundRadius, 0);
        topLeftRadius = typedArray.getDimensionPixelOffset(R.styleable.LayoutBgUi_layoutTopLeftRadius, 0);
        topRightRadius = typedArray.getDimensionPixelOffset(R.styleable.LayoutBgUi_layoutTopRightRadius, 0);
        bottomLeftRadius = typedArray.getDimensionPixelOffset(R.styleable.LayoutBgUi_layoutBottomLeftRadius, 0);
        bottomRightRadius = typedArray.getDimensionPixelOffset(R.styleable.LayoutBgUi_layoutBottomRightRadius, 0);
        typedArray.recycle();
    }


    private Drawable getColorDrawable() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && isRipple) {
            if (pressedColor == 0) {
                pressedColor = getLightOrDarken(defaultColor, 0.2D);
            }
            ColorStateList pressedColorDw = ColorStateList.valueOf(pressedColor);
            return new RippleDrawable(pressedColorDw, getGradientDrawable(0, 0), getShape());
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getGradientDrawable(pressedColor, parameter));
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, getGradientDrawable(pressedColor, parameter * 2));
            stateListDrawable.addState(new int[]{}, getGradientDrawable(0, 0));
            return stateListDrawable;
        }
    }

    private Drawable getGradientDrawable(int color, double parameter) {
        GradientDrawable dw = new GradientDrawable();
        if (parameter == 0) {
            if (strokeWidth != 0) {
                dw.setStroke(strokeWidth, strokeColor == 0 ? Color.TRANSPARENT : strokeColor);
            }
            if (defaultColor != 0) {
                dw.setColor(defaultColor);
            }
        } else {
            if (strokeWidth != 0) {
                dw.setStroke(strokeWidth, selectStrokeColor == 0 ? strokeColor == 0 ? Color.TRANSPARENT : strokeColor : selectStrokeColor);
            }
            if (color == 0) {
                dw.setColor(getLightOrDarken(defaultColor, parameter));
            } else {
                dw.setColor(color);
            }
        }
        if (raoundRadius != 0) {
            dw.setCornerRadius(raoundRadius);
        } else if (topLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0 || bottomLeftRadius != 0) {
            dw.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        }
        return dw;
    }

    private Drawable getShape() {
        ShapeDrawable mask = new ShapeDrawable(new RectShape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                final float width = this.getWidth();
                final float height = this.getHeight();
                RectF rectF = new RectF(0, 0, width, height);
                if (raoundRadius != 0) {
                    canvas.drawRoundRect(rectF, raoundRadius, raoundRadius, paint);
                } else if (topLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0 || bottomLeftRadius != 0) {
                    Path path = new Path();
                    path.addRoundRect(rectF, new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius}, Path.Direction.CW);
                    canvas.drawPath(path, paint);
                } else {
                    canvas.drawRect(rectF, paint);
                }
            }
        });
        return mask;
    }

    //单色变暗
    private int darkenColor(int color, double parameter) {
        return (int) Math.max(color - color * parameter, 0);
    }

    //颜色变暗
    private int drakenColors(int color, double parameter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, parameter);
        green = darkenColor(green, parameter);
        blue = darkenColor(blue, parameter);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    //单色变亮
    private int lightColor(int color, double parameter) {
        return (int) Math.min(color + color * parameter, 255);
    }

    //颜色变亮
    private int lightColors(int color, double parameter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightColor(red, parameter);
        green = lightColor(green, parameter);
        blue = lightColor(blue, parameter);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    //判断颜色 变亮或变暗
    private boolean isLightOrDarken(int color, double parameter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return red + (red * parameter) < 255 && green + (green * parameter) < 255 && blue + (blue * parameter) < 255;
    }

    //获取变亮或变暗颜色
    private int getLightOrDarken(int color, double parameter) {
        parameter = parameter < 0 ? 0 : parameter > 1 ? 1 : parameter;
        if (isLightOrDarken(color, parameter)) {
            return lightColors(color, parameter);
        } else {
            return drakenColors(color, parameter);
        }
    }


}
