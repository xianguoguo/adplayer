package com.adshow.player.activitys.fullscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.adshow.player.activitys.BaseActivity;
import com.adshow.player.event.MyEvent;
import com.adshow.player.event.PlayEvent;
import com.adshow.player.util.FileUtils;
import com.avocarrot.json2view.DynamicView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayingActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        MyEvent event = new MyEvent("playerReady");
        EventBus.getDefault().post(event);

    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onReceivePlayEvent(PlayEvent event) {

        Log.d("loadAdContent", "播放广告:" + event.advertisingPath);
    }


}
