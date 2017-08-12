package com.song.shapebglib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 * description: 跳转工具类
 * author: taosongsong
 * date: 2017/8/9 17:34
 * update: 跳转工具类
 * version: 1.0
 */

public class IntentUtils {

    //跳转到拨号界面
    public static boolean callDial(Context context, String phone) {
        Uri number = Uri.parse("tel:" + phone);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (check(context, callIntent)) {
            context.startActivity(callIntent);
            return true;
        } else {
            return false;
        }

    }


    //跳转到网页
    public static boolean startWeb(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

        if (check(context, webIntent)) {
            context.startActivity(webIntent);
            return true;
        } else {
            return false;
        }
    }


    //打开分享
    public static boolean startShare(Context context, String title) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "这是一段分享的文字");

        if (check(context, shareIntent)) {
            Intent chooser = Intent.createChooser(shareIntent, title);
            context.startActivity(chooser);
            return true;
        } else {
            return false;
        }
    }


    //检查是否有对应的intent
    public static boolean check(Context context, Intent intent) {
       PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return activities.size() > 0;
    }


}
