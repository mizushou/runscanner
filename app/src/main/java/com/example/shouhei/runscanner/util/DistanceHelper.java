package com.example.shouhei.runscanner.util;

public class DistanceHelper {

    static final float METERPERMILE = 1609.344f;

    public static float convertMileStrToMeter(String mileStr) {
        try {
            float mileFloat = Float.valueOf(mileStr);
            return mileFloat * METERPERMILE;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // TODO consider later...
            return 0f;
        }
    }

    public static float convertMeterToMile(float meter) {
        return meter / METERPERMILE;
    }

    public static float convertMileToMeter(float mile) {
        return mile * METERPERMILE;
    }
}
