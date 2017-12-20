package com.song.rxsamples.helper;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * description: 两端文字环绕图片的效果
 * author: ss
 * date: 2017/12/4 13:09
 * update: 两端文字环绕图片的效果
 * version: 1.0
 */
public class TextRoundSpan implements LeadingMarginSpan.LeadingMarginSpan2 {
    //左间距
    private int margin;
    //行数
    private int lines;

    public TextRoundSpan(int margin, int lines) {
        this.margin = margin;
        this.lines = lines;
    }

    //有间距的行数
    @Override
    public int getLeadingMarginLineCount() {
        return lines;
    }

    //是否使用margin
    //当前行数小于等于getLeadingMarginLineCount()，getLeadingMargin(boolean first)中first的值为true。
    @Override
    public int getLeadingMargin(boolean b) {
        if (b) {
            return margin;
        } else {
            return 0;
        }
    }

    //间距效果
    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint, int i, int i1, int i2, int i3, int i4, CharSequence charSequence, int i5, int i6, boolean b, Layout layout) {

    }
}
