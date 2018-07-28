package com.example.shouhei.runscanner.data;

import java.util.UUID;

public class Run {

    private UUID mId;
    private String mDistance;
    private String mCalorie;
    private String mDuration;
    private String mAvePace;
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

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
