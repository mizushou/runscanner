package com.example.shouhei.runscanner.util;

import android.util.Log;

import com.example.shouhei.runscanner.exception.NoRightSideElementsException;

import java.util.List;

public class RightSideElementsCalculator {

  private static final String TAG = "RSElementsCalculator";
  //  private static final float PARAMETER = 0.1f;
  private RightSideElementsList mElementsList;
  private int mMeanX;
  private int mOrder;
  private int mVariance;
  private float mCoefficient;

  public RightSideElementsCalculator(RightSideElementsList elementsList) {
    mElementsList = elementsList;
    try {
      mMeanX = calcMeanX();
      mOrder = calcOrder();
      mVariance = calcVariance();
      mCoefficient = calcCoefficient();
    } catch (NoRightSideElementsException e) {
      Log.e(TAG, e.toString());
    }
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
    return meanX;
  }

  private int calcVariance() throws NoRightSideElementsException {
    int meanX = calcMeanX();
    List<ElementWrapper> elementList = mElementsList.getElementList();
    int sum = 0;
    for (ElementWrapper e : elementList) {
      sum += Math.abs(e.getX() - meanX);
    }
    return sum / elementList.size();
  }

  private int calcOrder() throws NoRightSideElementsException {
    return Math.abs(calcMeanX() - mElementsList.getCenterX());
  }

  private float calcCoefficient() throws NoRightSideElementsException {
    int order = calcOrder();
    float coefficient = (float) calcVariance() / order;
    return coefficient;
  }

  public int getMeanX() {
    return mMeanX;
  }

  public int getOrder() {
    return mOrder;
  }

  public int getVariance() {
    return mVariance;
  }

  public float getCoefficient() {
    return mCoefficient;
  }
}
