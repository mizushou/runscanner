package com.example.shouhei.runscanner.util;

public class DistanceHelper {

    static final float METERPERMILE = 1609.344f;

    public static float convertMileStrToMeter(String mileStr) {
        float mileFloat = Float.valueOf(mileStr);
        return mileFloat * METERPERMILE;
    }

    public static float convertMeterToMile(float meter) {
        return meter / METERPERMILE;
    }

    public static float convertMileToMeter(float mile) {
        return mile * METERPERMILE;
    }
}
