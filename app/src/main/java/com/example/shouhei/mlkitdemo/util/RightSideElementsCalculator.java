package com.example.shouhei.mlkitdemo.util;

import android.util.Log;

import com.example.shouhei.mlkitdemo.exception.NoRightSideElementsException;

import java.util.Collections;
import java.util.List;

public class RightSideElementsCalculator {

  private static final String TAG = "RSElementsCalculator";
  //  private static final float PARAMETER = 0.1f;
  RightSideElementsList mElementsList;

  public RightSideElementsCalculator(RightSideElementsList elementsList) {
    mElementsList = elementsList;
  }

  public List<ElementWrapper> getResultElements() {

    List<ElementWrapper> elementList = mElementsList.getElementList();
    Collections.sort(elementList, new SortElementWrapperByYComparator());

    try {
      float coefficient = calcCoefficient();
    } catch (NoRightSideElementsException e) {
      Log.e(TAG, e.toString());
    }

    //    if (coefficient >= PARAMETER) {
    //      // include 'Totals'
    //      elementList.remove(0);
    //    }

    int i = 0;
    for (ElementWrapper e : elementList) {
      Log.d(
          TAG,
          "Result#" + i + " : " + e.getValue() + " | (x,y) = (" + e.getX() + "," + e.getY() + ")");
      i++;
    }

    return elementList;
  }

  private int calcMeanX() throws NoRightSideElementsException {
    List<ElementWrapper> elementList = mElementsList.getElementList();

    if (elementList.size() == 0) {
      throw new NoRightSideElementsException(
          "Number of right side elements is zero. Maybe the picture is a little to the left... ");
    }

    int sum = 0;
    for (ElementWrapper e : elementList) {
      sum += e.getX();
    }
    int meanX = sum / elementList.size();
    Log.d(TAG, "meanX : " + String.valueOf(meanX));
    return meanX;
  }

  private int calcVariance() throws NoRightSideElementsException {
    int meanX = calcMeanX();
    List<ElementWrapper> elementList = mElementsList.getElementList();
    int sum = 0;
    for (ElementWrapper e : elementList) {
      sum += Math.abs(e.getX() - meanX);
    }
    int variance = sum / elementList.size();
    Log.d(TAG, "variance : " + String.valueOf(variance));
    return variance;
  }

  private float calcCoefficient() throws NoRightSideElementsException {
    int order = Math.abs(calcMeanX() - mElementsList.getCenterX());
    Log.d(TAG, "order : " + String.valueOf(order));

    float coefficient = (float) calcVariance() / order;
    Log.d(TAG, "coefficient : " + String.valueOf(Math.round(coefficient * 100)) + "%");
    return coefficient;
  }
}
