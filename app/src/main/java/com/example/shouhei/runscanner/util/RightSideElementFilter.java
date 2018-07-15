package com.example.shouhei.runscanner.util;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RightSideElementFilter {

    private static final String TAG = "RightSideElementFilter";
    // These are parameters.
    private static final float COEFFICIENT_Y_THRESHOLD = 0.05f;
    private static final float UPPER_LIMIT = 0.65f;
    private static final float LOWER_LIMIT = 0.34f;

    private RightSideElementsCalculator mCalculator;

    public RightSideElementFilter(RightSideElementsCalculator calculator) {
        mCalculator = calculator;
    }

    public void filter() {

        List<ElementWrapper> list = mCalculator.getRightSideElementsList().getElementList();
        List<ElementWrapper> filteredList = new ArrayList<>();

        // If the coefficient y exceeds the threshold, apply the valid range and exclude dust data.
        if (mCalculator.getCoefficientY() >= COEFFICIENT_Y_THRESHOLD) {
            Log.d(TAG, "Detect large variations!");
            Log.d(TAG, "    coefficient y threshold : " + COEFFICIENT_Y_THRESHOLD * 100 + "%");

            float roundedCoefficientYPercent =
                    new BigDecimal(String.valueOf(mCalculator.getCoefficientY() * 100))
                            .setScale(3, BigDecimal.ROUND_HALF_UP)
                            .floatValue();

            Log.d(TAG, "    This coefficient y : " + roundedCoefficientYPercent + "%");

            for (ElementWrapper element : list) {
                if (isValidRange(element.getY())) {
                    filteredList.add(element);
                }
            }
            mCalculator.getRightSideElementsList().setElementList(filteredList);
        }
    }

    private boolean isValidRange(int y) {

        int meanY = mCalculator.getMeanY();
        float upper = meanY * (1 + UPPER_LIMIT);
        float lower = meanY * (1 - LOWER_LIMIT);

        return y > lower && upper > y;
    }
}
