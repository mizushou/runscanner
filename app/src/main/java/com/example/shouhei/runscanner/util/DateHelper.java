package com.example.shouhei.runscanner.util;

import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    public static String getDateTitleStringOnCard(long date) {
        return (String) DateFormat.format("MMM. dd yyyy", date);
    }

    public static String getDateSubTitleStringOnCard(long date) {
        return (String) DateFormat.format("EEE, HH:mma", date);
    }

    public static String getDateTitleStringOfTheYearOnStats() {
        long firstDayOfTheMonth = getFirstDayOfTheMonth();
        return (String) DateFormat.format("yyyy", firstDayOfTheMonth);
    }

    public static String getDateTitleStringOfTheMonthOnStats() {
        long firstDayOfTheMonth = getFirstDayOfTheMonth();
        return (String) DateFormat.format("yyyy. MMM", firstDayOfTheMonth);
    }

    public static String getDateTitleStringOfTheWeekOnStats() {
        long firstDayOfTheWeek = getFirstDayOfTheWeek();
        String startWeek = (String) DateFormat.format("yyyy. MMM.dd - ", firstDayOfTheWeek);

        long lastDayOfTheWeek = getLastDayOfTheWeek();
        String endWeek = (String) DateFormat.format("MMM.dd", lastDayOfTheWeek);

        return startWeek + endWeek;
    }

    public static long getFirstDayOfTheMonth() {
        Date currentDate = new Date(System.currentTimeMillis());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(currentDate);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int ampm = calendar.get(Calendar.AM_PM);

        calendar.add(Calendar.DAY_OF_MONTH, -dayOfMonth + 1);
        calendar.add(Calendar.HOUR, -hour);
        calendar.add(Calendar.MINUTE, -minute);
        calendar.add(Calendar.SECOND, -second);
        if (ampm == 1) {
            calendar.add(Calendar.AM_PM, -ampm);
        }

        return calendar.getTimeInMillis();
    }

    public static long getFirstDayOfTheNextMonth() {
        Date firstDayOfTheMonth = new Date(getFirstDayOfTheMonth());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(firstDayOfTheMonth);
        calendar.add(Calendar.MONTH, 1);

        return calendar.getTimeInMillis();
    }

    public static long getFirstDayOfTheWeek() {
        Date currentDate = new Date(System.currentTimeMillis());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(currentDate);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int ampm = calendar.get(Calendar.AM_PM);
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek + 1);
        calendar.add(Calendar.HOUR, -hour);
        calendar.add(Calendar.MINUTE, -minute);
        if (ampm == 1) {
            calendar.add(Calendar.AM_PM, -ampm);
        }

        return calendar.getTimeInMillis();
    }

    public static long getFirstDayOfTheNextWeek() {
        Date firstDayOfTheNextWeek = new Date(getFirstDayOfTheWeek());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(firstDayOfTheNextWeek);

        calendar.add(Calendar.DAY_OF_MONTH, 7);

        return calendar.getTimeInMillis();
    }

    public static long getLastDayOfTheWeek() {
        Date currentDate = new Date(System.currentTimeMillis());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(currentDate);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int ampm = calendar.get(Calendar.AM_PM);
        calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek + 7);
        calendar.add(Calendar.HOUR, -hour);
        calendar.add(Calendar.MINUTE, -minute);
        if (ampm == 1) {
            calendar.add(Calendar.AM_PM, -ampm);
        }

        return calendar.getTimeInMillis();
    }

    public static long getFirstDayOfTheYear() {
        Date firstDayOfTheYear = new Date(getFirstDayOfTheMonth());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(firstDayOfTheYear);

        int month = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.MONTH, -month);

        return calendar.getTimeInMillis();
    }

    public static long getFirstDayOfTheNextYear() {
        Date firstDayOfTheYear = new Date(getFirstDayOfTheYear());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(firstDayOfTheYear);

        int year = calendar.get(Calendar.YEAR);
        calendar.add(Calendar.YEAR, 1);

        return calendar.getTimeInMillis();
    }

    public static void testDate() {
        Date currentDate = new Date(System.currentTimeMillis());

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(currentDate);

        Log.d("DateHelper", "Year : " + String.valueOf(calendar.get(Calendar.YEAR)));
        Log.d("DateHelper", "Month :" + String.valueOf(calendar.get(Calendar.MONTH) + 1));
        Log.d("DateHelper", "Day : " + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        Log.d("DateHelper", "Hour :" + String.valueOf(calendar.get(Calendar.HOUR)));
        Log.d("DateHelper", "Minute : " + String.valueOf(calendar.get(Calendar.MINUTE)));
        Log.d("DateHelper", "AM PM " + String.valueOf(calendar.get(Calendar.AM_PM)));
        Log.d("DateHelper", "Day of week " + String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
    }

    public static void testDate2(long date) {
        Date currentDate = new Date(date);

        // TODO consider timezone later...
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.CANADA);
        calendar.setTime(currentDate);

        Log.d("DateHelper", "Year : " + String.valueOf(calendar.get(Calendar.YEAR)));
        Log.d("DateHelper", "Month :" + String.valueOf(calendar.get(Calendar.MONTH) + 1));
        Log.d("DateHelper", "Day : " + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        Log.d("DateHelper", "Hour :" + String.valueOf(calendar.get(Calendar.HOUR)));
        Log.d("DateHelper", "Minute : " + String.valueOf(calendar.get(Calendar.MINUTE)));
        Log.d("DateHelper", "AM PM " + String.valueOf(calendar.get(Calendar.AM_PM)));
        Log.d("DateHelper", "Day of week " + String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
    }
}
