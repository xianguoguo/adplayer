package com.adshow.player.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity
public class Schedule {
    @Id
    private String id;
    private Long duration;
    private Date beginDate;
    private Date endDate;
    private Long order;
    @NotNull
    private String advertisingId;

    @Generated(hash = 172246409)
    public Schedule(String id, Long duration, Date beginDate, Date endDate,
            Long order, @NotNull String advertisingId) {
        this.id = id;
        this.duration = duration;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.order = order;
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

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", advertisingId='" + advertisingId + '\'' +
                '}';
    }

    public Long getDuration() {
        return this.duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getOrder() {
        return this.order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
