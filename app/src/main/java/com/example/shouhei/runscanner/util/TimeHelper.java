package com.example.shouhei.runscanner.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeHelper {

    public static int convertSexagesimalStrToSecond(String sexagesimalStr) {

        String[] arr = sexagesimalStr.split(":");
        int n = arr.length;
        int hh;
        int mm;
        int ss;
        switch (n) {
            case 2:
                // mm:ss
                mm = Integer.valueOf(delFirstZero(arr[0]));
                ss = Integer.valueOf(delFirstZero(arr[1]));
                return mm * 60 + ss;
            case 3:
                // hh:mm:ss
                hh = Integer.valueOf(delFirstZero(arr[0]));
                mm = Integer.valueOf(delFirstZero(arr[1]));
                ss = Integer.valueOf(delFirstZero(arr[2]));
                return hh * 3600 + mm * 60 + ss;
        }
        return 0;
    }

    private static String delFirstZero(String s) {
        String regex = "^0[1-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return s.substring(1);
        }
        return s;
    }

    public static String convertSecondToSexagesimal(int targetSecond) {
        StringBuilder hhmmss = new StringBuilder();
        int hour = targetSecond / 3600;
        int minute = targetSecond / 60;
        int second = targetSecond % 60;
        if (hour >= 1) {
            // hh:mm:ss
            // Fill zero except the highest digit
            hhmmss.append(hour)
                    .append(":")
                    .append(addZero(String.valueOf(minute)))
                    .append(":")
                    .append(addZero(String.valueOf(second)));
        } else if (minute >= 1) {
            // mm:ss
            // Fill zero except the highest digit
            hhmmss.append(minute).append(":").append(addZero(String.valueOf(second)));
        } else {
            // ss
            hhmmss.append("00:").append(addZero(String.valueOf(second)));
        }
        return hhmmss.toString();
    }

    private static String addZero(String s) {
        String regex = "^[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return "0" + s;
        }
        return s;
    }
}
