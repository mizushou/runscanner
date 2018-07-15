package com.example.shouhei.runscanner.util;

import android.util.Log;

import com.example.shouhei.runscanner.exception.NoRightSideElementsException;

import java.util.List;

public class RightSideElementsCalculator {

    private static final String TAG = "RSElementsCalculator";

    private RightSideElementsList mRightSideElementsList;
    private int mMeanX;
    private int mMeanY;
    private int mVarianceX;
    private int mVarianceY;
    private int mOrderX;
    private int mOrderY;
    private float mCoefficientX;
    private float mCoefficientY;

    public RightSideElementsCalculator(RightSideElementsList elementsList) {
        mRightSideElementsList = elementsList;
        try {
            mMeanX = calcMeanX();
            mMeanY = calcMeanY();
            mVarianceX = calcVarianceX();
            mVarianceY = calcVarianceY();
            mOrderX = calcOrderX();
            mOrderY = calcOrderY();
            mCoefficientX = calcCoefficientX();
            mCoefficientY = calcCoefficientY();
        } catch (NoRightSideElementsException e) {
            Log.e(TAG, e.toString());
        }
    }

    private int calcMeanX() throws NoRightSideElementsException {
        List<ElementWrapper> elementList = mRightSideElementsList.getElementList();

        if (elementList.size() == 0) {
            throw new NoRightSideElementsException(
                    "Number of right side elements is zero. Maybe the picture is a little to the left... ");
        }

        int sum = 0;
        for (ElementWrapper e : elementList) {
            sum += e.getX();
        }
        return sum / elementList.size();
    }

    private int calcMeanY() throws NoRightSideElementsException {
        List<ElementWrapper> elementList = mRightSideElementsList.getElementList();

        if (elementList.size() == 0) {
            throw new NoRightSideElementsException(
                    "Number of right side elements is zero. Maybe the picture is a little to the left... ");
        }

        int sum = 0;
        for (ElementWrapper e : elementList) {
            sum += e.getY();
        }
        return sum / elementList.size();
    }

    private int calcVarianceX() {
        List<ElementWrapper> elementList = mRightSideElementsList.getElementList();
        int sum = 0;
        for (ElementWrapper e : elementList) {
            //      sum += Math.abs(e.getX() - getMeanX());
            sum += Math.pow(e.getX() - getMeanX(), 2);
        }
        return sum / elementList.size();
    }

    private int calcVarianceY() {
        List<ElementWrapper> elementList = mRightSideElementsList.getElementList();
        int sum = 0;
        for (ElementWrapper e : elementList) {
            //      sum += Math.abs(e.getY() - getMeanY());
            sum += Math.pow(e.getY() - getMeanY(), 2);
        }
        return sum / elementList.size();
    }

    private int calcOrderX() throws NoRightSideElementsException {
        return Math.abs(calcMeanX() - mRightSideElementsList.getTargetImageWidth());
    }

    private int calcOrderY() throws NoRightSideElementsException {
        return Math.abs(calcMeanY() - mRightSideElementsList.getTargetImageHeight());
    }

    private float calcCoefficientX() {

        return (float) (getVarianceX() / Math.pow(getOrderX(), 2));
    }

    private float calcCoefficientY() {

        return (float) (getVarianceY() / Math.pow(getOrderY(), 2));
    }

    public int getMeanX() {
        return mMeanX;
    }

    public int getOrderX() {
        return mOrderX;
    }

    public int getVarianceX() {
        return mVarianceX;
    }

    public float getCoefficientX() {
        return mCoefficientX;
    }

    public int getMeanY() {
        return mMeanY;
    }

    public int getVarianceY() {
        return mVarianceY;
    }

    public int getOrderY() {
        return mOrderY;
    }

    public float getCoefficientY() {
        return mCoefficientY;
    }

    public RightSideElementsList getRightSideElementsList() {
        return mRightSideElementsList;
    }
}
