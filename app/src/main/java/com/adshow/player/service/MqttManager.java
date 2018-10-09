package com.adshow.player.service;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class MQTTManager {

    private static final String TAG = MQTTManager.class.getCanonicalName();

    public static final String URL = "tcp://192.168.1.4:1883";

    public static final String userName = "device";

    public static final String password = "wab123";

    public static final String clientId = "sn001";

    public static final String TOPIC = "/d/status/play";

    public static final String PUB_TOPIC_RUNNING = "/d/status/running";

    private static MQTTManager mInstance = null;

    private MqttCallback mCallback;

    private MqttClient client;

    private MqttConnectOptions conOpt;

    private boolean clean = true;

    private MQTTManager() {
        mCallback = new MQTTCallbackBus();
    }

    public static MQTTManager getInstance() {
        if (null == mInstance) {
            mInstance = new MQTTManager();
        }
        return mInstance;
    }


    public static void stop() {
        try {
            if (mInstance != null) {
                mInstance.disConnect();
                mInstance = null;
            }
        } catch (Exception e) {

        }
    }


    public boolean connect(String brokerUrl, String userName, String password, String clientId) {
        boolean flag = false;
        String tmpDir = System.getProperty("java.io.tmpdir");
        MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);

        try {
            conOpt = new MqttConnectOptions();
            conOpt.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
            conOpt.setCleanSession(clean);
            if (password != null) {
                conOpt.setPassword(password.toCharArray());
            }
            if (userName != null) {
                conOpt.setUserName(userName);
            }

            client = new MqttClient(brokerUrl, clientId, dataStore);
            client.setCallback(mCallback);
            flag = doConnect();
        } catch (MqttException e) {
            Log.e(TAG, e.getMessage());
        }

        return flag;
    }


    public boolean doConnect() {
        boolean flag = false;
        if (client != null) {
            try {
                client.connect(conOpt);
                Log.d(TAG, "Connected to " + client.getServerURI() + " with client ID " + client.getClientId());
                flag = true;
            } catch (Exception e) {
            }
        }
        return flag;
    }


    public boolean publish(String topicName, int qos, byte[] payload) {
        boolean flag = false;
        if (client != null && client.isConnected()) {
            Log.d(TAG, "Publishing to topic \"" + topicName + "\" qos " + qos);
            MqttMessage message = new MqttMessage(payload);
            message.setQos(qos);
            try {
                client.publish(topicName, message);
                flag = true;
            } catch (MqttException e) {

            }
        }
        return flag;
    }


    public boolean subscribe(String topicName, int qos) {
        boolean flag = false;
        if (client != null && client.isConnected()) {
            Log.d(TAG, "Subscribing to topic \"" + topicName + "\" qos " + qos);
            try {
                client.subscribe(topicName, qos);
                flag = true;
            } catch (MqttException e) {

            }
        }
        return flag;
    }


    public void disConnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
        }
    }


    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}
