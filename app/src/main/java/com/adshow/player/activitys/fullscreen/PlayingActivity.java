package com.adshow.player.activitys.fullscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.adshow.player.event.MyEvent;
import com.adshow.player.event.PlayEvent;
import com.adshow.player.util.FileUtils;
import com.avocarrot.json2view.DynamicView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayingActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        MyEvent event = new MyEvent("playerReady");
        EventBus.getDefault().post(event);

    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1)
    public void onReceivePlayEvent(PlayEvent event) {
        loadAdContent(event.advertisingPath);
        Log.d("loadAdContent", "播放广告:" + event.advertisingPath);
    }


    private void loadAdContent(String configuration){
        JSONObject jsonObject;
        View sampleView;

        try {
            jsonObject = FileUtils.readAdvertisingConfig(configuration);

        } catch (JSONException je) {
            je.printStackTrace();
            jsonObject = null;
        }

        if (jsonObject != null) {

            /* create dynamic view and return the view with the holder class attached as tag */
            sampleView = DynamicView.createView(this, jsonObject);

            /* add Layout Parameters in just created view and set as the contentView of the activity */
            sampleView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
            setContentView(sampleView);

        } else {
            Log.e("Json2View", "Could not load valid json file");
        }
    }




}
