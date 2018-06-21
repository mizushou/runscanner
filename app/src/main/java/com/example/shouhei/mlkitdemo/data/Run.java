package com.example.shouhei.mlkitdemo.data;

import java.util.Date;
import java.util.UUID;

public class Run {

  private UUID mId;
  private String mDistance;
  private String mCalory;
  private String mDuration;
  private String mAvePace;
  private String mAveHeartRate;
  private Date mDate;

  public Run() {
    mId = UUID.randomUUID();
    mDate = new Date();
  }

  public String getPhotoFilename() {
    return "IMG_" + getId().toString() + ".jpg";
  }

  public UUID getId() {
    return mId;
  }

  public void setId(UUID id) {
    mId = id;
  }

  public String getDistance() {
    return mDistance;
  }

  public void setDistance(String distance) {
    mDistance = distance;
  }

  public String getCalory() {
    return mCalory;
  }

  public void setCalory(String calory) {
    mCalory = calory;
  }

  public String getDuration() {
    return mDuration;
  }

  public void setDuration(String duration) {
    mDuration = duration;
  }

  public String getAvePace() {
    return mAvePace;
  }

  public void setAvePace(String avePace) {
    mAvePace = avePace;
  }

  public String getAveHeartRate() {
    return mAveHeartRate;
  }

  public void setAveHeartRate(String aveHeartRate) {
    mAveHeartRate = aveHeartRate;
  }

  public Date getDate() {
    return mDate;
  }

  public void setDate(Date date) {
    mDate = date;
  }
}
