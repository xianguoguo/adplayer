package com.adshow.player.bean.mqtt;

import java.util.Date;

public class CMDScreen extends MQTTMessage {

    private boolean online;

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
