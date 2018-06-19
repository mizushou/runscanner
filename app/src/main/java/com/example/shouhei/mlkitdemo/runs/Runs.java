package com.example.shouhei.mlkitdemo.model;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RunList {

  private static List<Run> mRuns;
  private static RunList sRunList;
  private Context mContext;

  // singleton
  public static RunList get(Context context) {
    if (sRunList == null) {
      sRunList = new RunList(context);
    }
    return sRunList;
  }

  public void addRun(Run r) {
    mRuns.add(r);
  }

  private RunList(Context context) {
    mContext = context.getApplicationContext();
    mRuns = new ArrayList<>();
  }

  public File getPhotoFile(Run run) {
    File fileDir = mContext.getFilesDir();
    return new File(fileDir, run.getPhotoFilename());
  }
}
