package com.adshow.player.activitys.fullscreen;

import android.os.Bundle;
import android.util.Log;

import com.adshow.player.activitys.BaseActivity;
import com.adshow.player.event.MyEvent;
import com.adshow.player.event.PlayStartEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PlayingActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        MyEvent event = new MyEvent("playerReady");
        EventBus.getDefault().post(event);

    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onReceivePlayEvent(PlayStartEvent event) {

        Log.d("loadAdContent", "播放广告:" + event.advertisingPath);
    }


}
