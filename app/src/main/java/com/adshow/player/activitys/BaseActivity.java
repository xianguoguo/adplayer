package com.adshow.player.activitys;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.adshow.player.activitys.schedule.ScreenOffAdminReceiver;
import com.adshow.player.bean.mqtt.RunningStatus;
import com.adshow.player.service.ADPlayerBackendService;
import com.adshow.player.service.MQTTManager;
import com.google.gson.Gson;

import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity {

    protected void showLongToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_LONG).show();
    }

    protected void showShortToast(String pMsg) {
        Toast.makeText(this, pMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RunningStatus runningStatus = new RunningStatus();
        runningStatus.setBootTime(new Date());
        runningStatus.setShutdownTime(new Date());
        MQTTManager.getInstance().publish(MQTTManager.PUB_TOPIC_RUNNING, 2, new Gson().toJson(runningStatus).getBytes());
        Intent intent = new Intent(this, ADPlayerBackendService.class);
        startService(intent);
        intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this.getApplicationContext(), ScreenOffAdminReceiver.class));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "开启后就可以使用锁屏功能了...");
        startActivityForResult(intent, 0);


    }
}
