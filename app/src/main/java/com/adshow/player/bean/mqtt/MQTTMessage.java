package com.adshow.player.bean.mqtt;

public class MQTTMessage {

    public static final String SERVER_CMD_DEPLOY = "server#cmd#deploy";

    public static final String SERVER_CMD_SCREEN = "server#cmd#screen";

    private String type;
    /**
     * 设备SN
     */
    protected String sn;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}
