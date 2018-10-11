package com.adshow.player.event;

public class PlayStartEvent {

    public PlayStartEvent(String advertisingPath) {
        this.advertisingPath = advertisingPath;
    }

    public String advertisingPath;

}
