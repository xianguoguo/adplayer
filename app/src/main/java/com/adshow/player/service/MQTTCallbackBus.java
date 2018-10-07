package com.adshow.player.service;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MQTTCallbackBus implements MqttCallback {
    private static final String TAG = MQTTCallbackBus.class.getCanonicalName();

    @Override
    public void connectionLost(Throwable cause) {
        Log.e(TAG, cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        Log.d(TAG, "messageArrived: " + topic + "====" + message.toString());
        //EventBus.getDefault().post(message);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e(TAG, "deliveryComplete");
    }

}
