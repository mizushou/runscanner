package com.example.shouhei.mlkitdemo.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;

public class PictureUtils {

  private static final String TAG = "PictureUtils";

  public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);

    // size of image on disk
    float srcWidth = options.outWidth;
    float srcHeight = options.outHeight;
    Log.d(
        TAG, "Image size is X : " + Float.toString(srcWidth) + " Y : " + Float.toString(srcHeight));
    Log.d(
        TAG,
        "Screen size is X : " + Float.toString(destWidth) + " Y : " + Float.toString(destHeight));

    int inSampleSize = 1;
    if (srcHeight > destHeight || srcWidth > destWidth) {
      float heightScale = srcHeight / destHeight;
      float widthScale = srcWidth / destHeight;
      inSampleSize = Math.round(heightScale > widthScale ? heightScale : widthScale);
    }
    Log.d(TAG, "Image scale is " + Integer.toString(inSampleSize));

    options = new BitmapFactory.Options();
    options.inSampleSize = inSampleSize;

    return BitmapFactory.decodeFile(path, options);
  }

  public static Bitmap getScaledBitmap(String path, Activity activity) {
    Point size = new Point();
    activity.getWindowManager().getDefaultDisplay().getSize(size);

    return getScaledBitmap(path, size.x, size.y);
  }
}
