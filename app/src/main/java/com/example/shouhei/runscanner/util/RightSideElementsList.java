package com.example.shouhei.runscanner.util;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RightSideElementsList {

  private static final String TAG = "RSElementsList";
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
    "miles",
    "FINISH"
  };

  private static final int EXPECTED_DISTANCE_INDEX = 0;
  private static final int EXPECTED_CALORIES_INDEX = 1;
  private static final int EXPECTED_DURATION_INDEX = 2;
  private static final int EXPECTED_AVGPACE_INDEX = 3;
  private static final int EXPECTED_AVGHEARTRATE_INDEX = 4;

  private int mTargetImageWidth;
  private int mTargetImageHeight;
  private List<ElementWrapper> mElementList;

  public RightSideElementsList(int targetImageWidth, int targetImageHeight) {
    mTargetImageWidth = targetImageWidth;
    mTargetImageHeight = targetImageHeight;
    mElementList = new ArrayList<>();
  }

  public void add(FirebaseVisionText.Element element) {

    ElementWrapper e = new ElementWrapper(element);

    int centerX = mTargetImageWidth / 2;
    if (centerX >= e.getX()) {
      return;
    }

    if (Arrays.asList(NGWORDS).contains(e.getValue())) {
      return;
    }

    mElementList.add(e);
  }

  public void sortByY() {
    Collections.sort(mElementList, new SortElementWrapperByYComparator());
  }

  public String getDistanceValue() {
    return getElementValue(EXPECTED_DISTANCE_INDEX);
  }

  public String getCaloriesValue() {
    return getElementValue(EXPECTED_CALORIES_INDEX);
  }

  public String getDurationValue() {
    return getElementValue(EXPECTED_DURATION_INDEX);
  }

  public String getAvgPaceValue() {
    return getElementValue(EXPECTED_AVGPACE_INDEX);
  }

  public String getAvgHeartRate() {
    return getElementValue(EXPECTED_AVGHEARTRATE_INDEX);
  }

  private String getElementValue(int index) {
    if (index < mElementList.size()) {
      return mElementList.get(index).getValue();
    }
    return "";
  }

  public int getTargetImageWidth() {
    return mTargetImageWidth;
  }

  public int getTargetImageHeight() {
    return mTargetImageHeight;
  }

  public List<ElementWrapper> getElementList() {
    return mElementList;
  }

  public void setElementList(List<ElementWrapper> elementList) {
    mElementList = elementList;
  }
}
