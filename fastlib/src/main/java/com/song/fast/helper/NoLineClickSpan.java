package com.song.rxsamples.helper;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * description: 去除下划线ClickSpan
 * author: ss
 * date: 2017/12/4 14:40
 * update: 去除下划线
 * version: 1.0
*/

public abstract class NoLineClickSpan extends ClickableSpan {

    @Override
    public void updateDrawState(TextPaint ds) {
//        super.updateDrawState(ds);
        //textColor
        ds.setColor(ds.getColor());
        //remove underLine ds.linkColor线的颜色
        ds.setUnderlineText(false);
    }
}
