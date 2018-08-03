package com.example.shouhei.runscanner.util;

import android.util.Log;

import com.example.shouhei.runscanner.data.Run;

import java.util.Comparator;

public class SortRunByDateComparator implements Comparator<Run> {

    @Override
    public int compare(Run o1, Run o2) {

        String s1 = String.valueOf(o1.getDate());
        String s2 = String.valueOf(o2.getDate());

        return s2.compareTo(s1);
    }
}
