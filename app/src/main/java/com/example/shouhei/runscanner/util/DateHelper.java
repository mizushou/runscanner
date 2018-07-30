package com.example.shouhei.runscanner.util;

import android.text.format.DateFormat;

public class DateHelper {

    public static String getDateTitleStringOnCard(long date) {
        return (String) DateFormat.format("MMM. dd yyyy", date);
    }

    public static String getDateSubTitleStringOnCard(long date) {
        return (String) DateFormat.format("EEE, HH:mma", date);
    }
}
