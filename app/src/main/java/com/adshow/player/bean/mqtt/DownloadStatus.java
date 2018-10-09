package com.adshow.player.bean.mqtt;

import java.util.Date;

public class DownloadStatus extends MQTTMessage {

    private String programId;

    private Date beginTime;

    private Date endTime;

    /**
     * 完整性检查
     */
    private boolean checksum;
}
