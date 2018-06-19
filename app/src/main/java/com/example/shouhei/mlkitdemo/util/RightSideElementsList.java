package com.example.shouhei.mlkitdemo.util;

import android.util.Log;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
  private static final int EXPECTEDDURATIONINDEX = 0;
  private static final int EXPECTEDAVGPACEINDEX = 1;
  private static final int EXPECTEDCALORIESINDEX = 0;
  private static final int EXPECTEDAVGHEARTRATE = 1;

  private int mCenterX;
  private List<ElementWrapper> mElementList;

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

    mElementList.add(e);
  }

  public void sortByY() {
    Collections.sort(mElementList, new SortElementWrapperByYComparator());
  }

  public String getMilesValue() {
    for (ElementWrapper e : mElementList) {
      if (isValidMilesValue(e.getValue())) {
        return e.getValue();
      }
    }
    return "";
  }

  private boolean isValidMilesValue(String elementValue) {
    String regex = "\\d+\\.\\d+";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(elementValue);
    return matcher.matches();
  }

  public String getDurationValue() {
    List<ElementWrapper> list = new ArrayList<>();
    for (ElementWrapper e : mElementList) {
      if (isValidDurationOrAvgPace(e.getValue())) {
        list.add(e);
      }
    }
    if (list.size() == 2) {
      String durationValue = list.get(EXPECTEDDURATIONINDEX).getValue();
      if (list.get(EXPECTEDDURATIONINDEX).getY() > list.get(EXPECTEDAVGPACEINDEX).getY()) {
        durationValue = list.get(EXPECTEDAVGPACEINDEX).getValue();
      }
      return durationValue;
    }
    return "";
  }

  public String getAvgPaceValue() {
    List<ElementWrapper> list = new ArrayList<>();
    for (ElementWrapper e : mElementList) {
      if (isValidDurationOrAvgPace(e.getValue())) {
        list.add(e);
      }
    }
    if (list.size() == 2) {
      String avgPaceValue = list.get(EXPECTEDAVGPACEINDEX).getValue();
      if (list.get(EXPECTEDDURATIONINDEX).getY() > list.get(EXPECTEDAVGPACEINDEX).getY()) {
        avgPaceValue = list.get(EXPECTEDDURATIONINDEX).getValue();
      }
      return avgPaceValue;
    }
    return "";
  }

  private boolean isValidDurationOrAvgPace(String elementValue) {
    String regex = "\\d*:*\\d*:\\d*";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(elementValue);
    return matcher.matches();
  }

  public String getCaloriesValue() {
    List<ElementWrapper> list = new ArrayList<>();
    for (ElementWrapper e : mElementList) {
      if (isValidCaloriesOrAvgHeartRate(e.getValue())) {
        list.add(e);
      }
    }
    String caloriesValue = "";
    if (list.size() == 2) {
      caloriesValue = list.get(EXPECTEDCALORIESINDEX).getValue();
      if (list.get(EXPECTEDCALORIESINDEX).getY() > list.get(EXPECTEDAVGHEARTRATE).getY()) {
        caloriesValue = list.get(EXPECTEDAVGHEARTRATE).getValue();
      }
      return caloriesValue;
    } else if (list.size() == 1) {
      // TODO consider later. It's rescue of caloriesValue and avgHeartRateValue.
      return caloriesValue;
    }
    return "";
  }

  public String getAvgHeartRate() {
    List<ElementWrapper> list = new ArrayList<>();
    for (ElementWrapper e : mElementList) {
      if (isValidCaloriesOrAvgHeartRate(e.getValue())) {
        list.add(e);
      }
    }
    String avgHeartRateValue = "";
    if (list.size() == 2) {
      avgHeartRateValue = list.get(EXPECTEDAVGHEARTRATE).getValue();
      if (list.get(EXPECTEDCALORIESINDEX).getY() > list.get(EXPECTEDAVGHEARTRATE).getY()) {
        avgHeartRateValue = list.get(EXPECTEDCALORIESINDEX).getValue();
      }
      return avgHeartRateValue;
    } else if (list.size() == 1) {
      // TODO consider later. It's rescue of caloriesValue and avgHeartRateValue.
      return avgHeartRateValue;
    }
    return "";
  }

  private boolean isValidCaloriesOrAvgHeartRate(String elementValue) {
    String regex = "[0-9]*";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(elementValue);
    return matcher.matches();
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
