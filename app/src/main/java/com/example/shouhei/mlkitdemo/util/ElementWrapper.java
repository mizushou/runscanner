package com.example.shouhei.mlkitdemo.util;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

public class ElementWrapper {

  private FirebaseVisionText.Element mElement;
  private int mX;
  private int mY;
  private String mValue;

  public ElementWrapper(FirebaseVisionText.Element element) {
    mElement = element;
    mX = element.getCornerPoints()[0].x;
    mY = element.getCornerPoints()[0].y;
    mValue = element.getText();
  }

  public FirebaseVisionText.Element getElement() {
    return mElement;
  }

  public void setElement(FirebaseVisionText.Element element) {
    mElement = element;
  }

  public int getX() {
    return mX;
  }

  public void setX(int x) {
    mX = x;
  }

  public int getY() {
    return mY;
  }

  public void setY(int y) {
    mY = y;
  }

  public String getValue() {
    return mValue;
  }

  public void setValue(String value) {
    mValue = value;
  }
}
