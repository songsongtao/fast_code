package com.song.fast.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * description: TextWatcher实现类，简化监听
 * author: ss
 * date: 2017/11/24 10:46
 * update:
 * version: 1.0
*/
public class SimpleTextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

}
