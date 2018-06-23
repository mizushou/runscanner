package com.example.shouhei.runscanner.util;

import java.util.Comparator;

public class SortElementWrapperByYComparator implements Comparator<ElementWrapper> {

  @Override
  public int compare(ElementWrapper o1, ElementWrapper o2) {
    return o1.getY() - o2.getY();
  }
}
