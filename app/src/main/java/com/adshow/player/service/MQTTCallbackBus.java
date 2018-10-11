package com.adshow.player.service;

import android.util.Log;

import com.adshow.player.bean.Advertising;
import com.adshow.player.bean.Schedule;
import com.adshow.player.bean.mqtt.CMDDeploy;
import com.adshow.player.bean.mqtt.CMDScreen;
import com.adshow.player.bean.mqtt.MQTTMessage;
import com.adshow.player.util.DaoManager;
import com.google.gson.Gson;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;
import java.util.UUID;


public class MQTTCallbackBus implements MqttCallback {

    private static final String TAG = MQTTCallbackBus.class.getCanonicalName();

    @Override
    public void connectionLost(Throwable cause) {
        Log.e(TAG, cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Gson gson = new Gson();
        Log.d(TAG, "messageArrived: " + topic + "====" + message.toString());
        if (message.toString().indexOf(MQTTMessage.SERVER_CMD_DEPLOY) != -1) {
            CMDDeploy deploy = gson.fromJson(message.toString(), CMDDeploy.class);

            Schedule schedule = new Schedule();
            schedule.setId(UUID.randomUUID().toString());
            schedule.setAdvertisingId(deploy.getProgramId());
            schedule.setBeginDate(deploy.getBeginDate());
            schedule.setEndDate(deploy.getEndDate());
            schedule.setOrder(deploy.getOrder());
            schedule.setDuration(deploy.getDuration());
            DaoManager.getInstance().getDaoSession().getScheduleDao().insertOrReplace(schedule);

            Advertising advertising = new Advertising();
            advertising.setId(deploy.getProgramId());
            advertising.setName(deploy.getProgramId());
            advertising.setDescription("描述");
            DaoManager.getInstance().getDaoSession().getAdvertisingDao().insertOrReplace(advertising);

            //下载
            DownloadManager.getInstance().download(deploy.getProgramId());
        }

        if (message.toString().indexOf(MQTTMessage.SERVER_CMD_SCREEN) != -1) {
            CMDScreen screen = gson.fromJson(message.toString(), CMDScreen.class);
            boolean online = screen.isOnline();
            //TODO 亮屏或熄屏
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e(TAG, "deliveryComplete");
    }

}
