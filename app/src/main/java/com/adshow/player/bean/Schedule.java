package com.adshow.player.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity
public class Schedule
{
    @Id
    private String id;
    private Date scheduleTime;
    @NotNull
    private String advertisingId;

    @Generated(hash = 2078719517)
    public Schedule(String id, Date scheduleTime, @NotNull String advertisingId) {
        this.id = id;
        this.scheduleTime = scheduleTime;
        this.advertisingId = advertisingId;
    }

    @Generated(hash = 729319394)
    public Schedule() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id='" + id + '\'' +
                ", scheduleTime=" + scheduleTime +
                ", advertisingId='" + advertisingId + '\'' +
                '}';
    }
}
