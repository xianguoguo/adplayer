package com.adshow.player.bean.mqtt;

import java.util.Date;

public class CMDDeploy extends MQTTMessage {

    private String programId;

    private Date beginDate;

    private Date endDate;

    private Long duration;

    private Long order;

    public CMDDeploy() {
        super.setType(MQTTMessage.SERVER_CMD_DEPLOY);
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }
}
