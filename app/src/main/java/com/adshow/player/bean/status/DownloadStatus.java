package com.adshow.player.bean.status;

import java.util.Date;

public class DownloadStatus extends DeviceReportStatus {

    private String programId;

    private Date beginTime;

    private Date endTime;

    /**
     * 完整性检查
     */
    private boolean checksum;
}
