package com.song.fast.compressor;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.IOException;

/**
 * description: Compressor
 * author: ss
 * date: 2017/12/28 10:31
 * update:
 * version: 1.0
 */
public class Compressor {

    private long maxSize = 1024; //1024kb
    private int maxWidth = 800;
    private int maxHeight = 800;
    private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
    private int quality = 80;
    private String destinationDirectoryPath;


    public Compressor(Context context) {
        destinationDirectoryPath = context.getCacheDir().getPath() + File.separator + "images";
    }

    public Compressor setMaxSize(long size) {
        this.maxSize = size;
        return this;
    }


    public Compressor setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public Compressor setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public Compressor setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
        return this;
    }

    public Compressor setQuality(int quality) {
        this.quality = quality;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String destinationDirectoryPath) {
        this.destinationDirectoryPath = destinationDirectoryPath;
        return this;
    }


    //for img save
    public File compressToFile(File imageFile, String compressedFileName) throws IOException {
        return ImageUtil.compressImage(imageFile, maxSize, maxWidth, maxHeight, compressFormat, quality,
                destinationDirectoryPath + File.separator + compressedFileName);
    }

    //for img display
    public Bitmap compressToBitmap(File imageFile) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(imageFile, maxWidth, maxHeight);
    }


}
