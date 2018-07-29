package com.example.shouhei.runscanner.data;

import java.util.UUID;

public class Run {

    private UUID mId;
    private String mDistance;
    private String mCalorie;
    private int mDuration;
    private int mAvePace;
    private String mAveHeartRate;
    private long mDate;

    public Run() {
        mId = UUID.randomUUID();
        mDate = System.currentTimeMillis();
    }

    // For referring from existing uuid.
    public Run(UUID uuid) {
        mId = uuid;
        mDate = System.currentTimeMillis();
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

    public String getCalorie() {
        return mCalorie;
    }

    public void setCalorie(String calorie) {
        mCalorie = calorie;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getAvePace() {
        return mAvePace;
    }

    public void setAvePace(int avePace) {
        mAvePace = avePace;
    }

    public String getAveHeartRate() {
        return mAveHeartRate;
    }

    public void setAveHeartRate(String aveHeartRate) {
        mAveHeartRate = aveHeartRate;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
