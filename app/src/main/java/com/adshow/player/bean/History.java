package com.adshow.player.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity
public class History {
    @Id
    private String id;
    private Date playBeginTime;
    private Date playEndTime;
    @NotNull
    private String advertisingId;

    @Generated(hash = 129390557)
    public History(String id, Date playBeginTime, Date playEndTime,
            @NotNull String advertisingId) {
        this.id = id;
        this.playBeginTime = playBeginTime;
        this.playEndTime = playEndTime;
        this.advertisingId = advertisingId;
    }

    @Generated(hash = 869423138)
    public History() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPlayBeginTime() {
        return playBeginTime;
    }

    public void setPlayBeginTime(Date playBeginTime) {
        this.playBeginTime = playBeginTime;
    }

    public Date getPlayEndTime() {
        return playEndTime;
    }

    public void setPlayEndTime(Date playEndTime) {
        this.playEndTime = playEndTime;
    }

    public String getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", playBeginTime=" + playBeginTime +
                ", playEndTime=" + playEndTime +
                ", advertisingId='" + advertisingId + '\'' +
                '}';
    }
}
