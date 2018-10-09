package com.adshow.player.service;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.adshow.player.activitys.schedule.ScreenOffAdminReceiver;
import com.adshow.player.bean.DeviceInfo;
import com.adshow.player.bean.UserToken;
import com.adshow.player.event.MyEvent;
import com.adshow.player.util.DeviceUtil;
import com.adshow.player.util.SharedUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ADPlayerBackendService extends Service {
    private static final String TAG = "ADPlayerBackendService";
    private static final String SERVER_DEFAULT = "http://192.168.1.4:8089/";

    private int totalCount;

    private int currentCount;

    private UserToken userToken;

    private static ADPlayerBackendService instance;

    public static ADPlayerBackendService getInstance() {
        return instance;
    }


    public OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request.Builder builder = chain.request()
                                .newBuilder();
                        if (DeviceUtil.getUserToken() != null && !TextUtils.isEmpty(DeviceUtil.getUserToken().getAccessToken())) {
                            builder.addHeader("accessToken", DeviceUtil.getUserToken().getAccessToken());
                        }
                        builder.addHeader("Accept-Encoding", "gzip, deflate")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*");
                        return chain.proceed(builder.build());
                    }
                })
                .build();

        return httpClient;
    }

    public RestWeatherApi getRestWeatherApi() {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://t.weather.sojson.com/")
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(genericClient())
                .build();

        return retrofit.create(RestWeatherApi.class);
    }


    public RestApi getRestApi() {
        Gson gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.4:8089/")
                //可以接收自定义的Gson，当然也可以不传
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(genericClient())
                .build();

        return retrofit.create(RestApi.class);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    /**
     * POSTING线程模型：在哪个线程发布事件，就在哪个线程执行onPostingEvent方法
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void onPostingEvent(MyEvent event) {
        Log.d(TAG, "onPostingEvent:" + Thread.currentThread().getName());
        if ("playerReady".equals(event.msg)) {
            SchedulerManager.getInstance().initScheduler();
        }
    }


    private DevicePolicyManager policyManager;
    private ComponentName adminReceiver;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    private PowerManager.WakeLock mWakeLock;


    public WindowManager getWindowManager() {
        return mWindowManager;
    }

    public void setWindowManager(WindowManager windowManager) {
        this.mWindowManager = windowManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        if (SharedUtil.getString(getApplicationContext(), "server", null) == null) {
            SharedUtil.putString(getApplicationContext(), "server", SERVER_DEFAULT);
        }

        mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        DeviceInfo deviceInfo = DeviceUtil.getDeviceInfo();
        System.out.println(new Gson().toJson(deviceInfo));


        EventBus.getDefault().register(this);

        adminReceiver = new ComponentName(this.getApplicationContext(), ScreenOffAdminReceiver.class);
        boolean admin = policyManager.isAdminActive(adminReceiver);
        if (admin) {
            policyManager.lockNow();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    turnOnScreen();
                }
            }, 5000);
        } else {
            Toast.makeText(getApplicationContext(), "没有设备管理权限", Toast.LENGTH_LONG);
        }

        initMQTTManager();
        initDownloadManager();
    }


    public static final String TOPIC_PROGRAM_DEPLOY = "/s/dev/%s/deploy";

    private void initMQTTManager() {
        String uniqueId = DeviceUtil.getUniqueDeviceId(this.getApplicationContext());
        MQTTManager.getInstance().connect(MQTTManager.URL, MQTTManager.userName, MQTTManager.password, MQTTManager.clientId);
        MQTTManager.getInstance().subscribe(String.format(TOPIC_PROGRAM_DEPLOY, uniqueId), 2);
    }


    private void initDownloadManager() {
        String uniqueId = DeviceUtil.getUniqueDeviceId(this.getApplicationContext());
        MQTTManager.getInstance().connect(MQTTManager.URL, MQTTManager.userName, MQTTManager.password, MQTTManager.clientId);
        MQTTManager.getInstance().subscribe(String.format(TOPIC_PROGRAM_DEPLOY, uniqueId), 2);
    }


    public void turnOnScreen() {
        // turn on screen
        Log.v("ProximityActivity", "ON!");
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");
        mWakeLock.acquire();
        mWakeLock.release();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        MQTTManager.stop();
        DownloadManager.stop();
        EventBus.getDefault().unregister(this);
    }


}

