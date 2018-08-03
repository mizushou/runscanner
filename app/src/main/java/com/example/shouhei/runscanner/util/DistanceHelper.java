package com.example.shouhei.runscanner.util;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DistanceHelper {

    static final BigDecimal METERPERMILE = BigDecimal.valueOf(1609.344);

    public static double convertMileStrToMeter(String mileStr) {

        BigDecimal mileBd = new BigDecimal(mileStr);

        return mileBd.multiply(METERPERMILE).doubleValue();
    }

    public static double convertMeterToMile(double meter) {

        BigDecimal meterBd = BigDecimal.valueOf(meter);
        return meterBd.divide(METERPERMILE, 2, RoundingMode.HALF_UP).doubleValue();
    }

    public static String formatMile(double mile) {
        DecimalFormat format = new DecimalFormat("#0.00");
        return format.format(mile);
    }

    //    public static double convertMileToMeter(float mile) {
    //        return mile * METERPERMILE;
    //    }
}
