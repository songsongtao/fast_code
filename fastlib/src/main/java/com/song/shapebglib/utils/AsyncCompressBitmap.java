package com.song.shapebglib.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class AsyncCompressBitmap {

    //文件列表
    private List<String> fileList = new ArrayList<>();
    //进度条
    private ProgressDialog progressDialog;

    public AsyncCompressBitmap(Context context, List<String> list, String saveRoot
            , int width, int height, int maxSize, CompressCallBack callBack) {
        CompressTask task = new CompressTask(context, list, saveRoot, callBack, width, height, maxSize);
        task.execute();
    }

    class CompressTask extends AsyncTask<Void, Integer, Integer> {
        private Context context;
        private List<String> list;
        private CompressCallBack callBack;
        private String saveRoot;
        private int width;
        private int height;
        private int maxSize;

        public CompressTask(Context context, List<String> list, String saveRoot,
                            CompressCallBack callBack, int width, int height, int maxSize) {
            this.context = context;
            this.list = list;
            this.callBack = callBack;
            this.saveRoot = saveRoot;
            this.width = width;
            this.height = height;
            this.maxSize = maxSize;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, null, "压缩中...");
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String picName = formatter.format(new Date());

            for (int i = 0; i < list.size(); i++) {
                String fileName = saveRoot + File.separator + picName+i+".jpg";
                BitmapUtil.compressBitmapAndSave(list.get(i), fileName, width, height, maxSize);
                fileList.add(fileName);
                callBack.success(fileList);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressDialog.dismiss();
        }
    }


    public interface CompressCallBack {
        void success(List<String> list);
    }
}
