package org.example.models;

import java.util.Date;

public class SleepRecord {
    private Date sleepTime;
    private Date wakeupTime;

    public SleepRecord(Date sleepTime, Date wakeupTime) {
        this.sleepTime = sleepTime;
        this.wakeupTime = wakeupTime;
    }

    public Date getSleepTime() {
        return sleepTime;
    }

    public Date getWakeupTime() {
        return wakeupTime;
    }

    public int getHoursOfSleep() {
        long diffMillis = wakeupTime.getTime() - sleepTime.getTime();
        return (int) (diffMillis / (60 * 60 * 1000));
    }
}