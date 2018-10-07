package com.adshow.player.bean.mqtt;

import java.util.Date;

public class RunningStatus extends DeviceReportStatus {

    private Date bootTime;

    private Date shutdownTime;

    public Date getBootTime() {
        return bootTime;
    }

    public void setBootTime(Date bootTime) {
        this.bootTime = bootTime;
    }

    public Date getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(Date shutdownTime) {
        this.shutdownTime = shutdownTime;
    }
}
