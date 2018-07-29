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

    static String delFirstZero(String s) {
        String regex = "^0[1-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return s.substring(1);
        }
        return s;
    }
}
