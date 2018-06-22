package com.example.shouhei.mlkitdemo.runs;

import android.content.Context;

import com.example.shouhei.mlkitdemo.data.Run;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Runs {

  private static Runs sRuns;
  private List<Run> mRunList;
  private Context mContext;

  // singleton
  public static Runs get(Context context) {
    if (sRuns == null) {
      sRuns = new Runs(context);
    }
    return sRuns;
  }

  private Runs(Context context) {
    mContext = context.getApplicationContext();
    mRunList = new ArrayList<>();

    // TODO remove later
    //    for (int i = 0; i < 100; i++) {
    //      Run run = new Run();
    //      run.setDistance("1.55");
    //      run.setCalory("200");
    //      run.setDuration("30:00");
    //      run.setAvePace("13:00");
    //      run.setAveHeartRate("80");
    //      addRun(run);
    //    }
  }

  public void addRun(Run run) {
    mRunList.add(run);
  }

  public List<Run> getRunList() {

    return mRunList;
  }

  public Run getRun(UUID uuid) {
    for (Run run : mRunList) {
      if (run.getId().equals(uuid)) {
        return run;
      }
    }
    return null;
  }

  public File getPhotoFile(Run run) {
    File fileDir = mContext.getFilesDir();
    return new File(fileDir, run.getPhotoFilename());
  }
}
