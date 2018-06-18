package com.example.shouhei.mlkitdemo.util;

import android.util.Log;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RightSideElementsList {

  private static final String TAG = "RSElementsList";
  private static final float ERRORRATE = 0.06f;
  private static final String[] NGWORDS = {
    "Workout",
    "Complete!",
    "Today's",
    "Totals",
    "Distance",
    "Calories",
    "Duration",
    "Avg",
    "Pace",
    "Heart",
    "Rate",
    "miles"
  };
  int mCenterX;
  List<ElementWrapper> mElementList;

  public RightSideElementsList(int targetImageWidth) {
    // TODO consider type
    mCenterX = targetImageWidth / 2;
    mElementList = new ArrayList<>();
    Log.d(TAG, "Error rate : " + String.valueOf(ERRORRATE));
  }

  public void add(FirebaseVisionText.Element element) {

    ElementWrapper e = new ElementWrapper(element);

    if (mCenterX * (1.0f + ERRORRATE) >= e.getX()) {
      return;
    }

    if (Arrays.asList(NGWORDS).contains(e.getValue())) {
      return;
    }

    //    if (e.getValue().equals("Complete!")) {
    //      return;
    //    }
    //
    //    if (e.getValue().equals("Totals")) {
    //      return;
    //    }
    //
    //    if (e.getValue().equals("miles")) {
    //      return;
    //    }
    //
    //    if (e.getValue().equals("Rate")) {
    //      return;
    //    }

    mElementList.add(e);
  }

  public int getCenterX() {
    return mCenterX;
  }

  public void setCenterX(int centerX) {
    mCenterX = centerX;
  }

  public List<ElementWrapper> getElementList() {
    return mElementList;
  }

  public void setElementList(List<ElementWrapper> elementList) {
    mElementList = elementList;
  }
}
