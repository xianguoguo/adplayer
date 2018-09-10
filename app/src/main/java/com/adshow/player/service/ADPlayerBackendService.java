package com.adshow.player.service;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.adshow.player.bean.Advertising;
import com.adshow.player.bean.History;
import com.adshow.player.bean.Schedule;
import com.adshow.player.event.MyEvent;
import com.adshow.player.event.PlayEvent;
import com.adshow.player.util.DaoManager;
import com.adshow.player.util.FileUtils;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ADPlayerBackendService extends Service {
    private static final String TAG = "ADPlayerBackendService";

    private File bunchDir;
    private int totalCount;
    private int currentCount;
    private DownloadListener listener;
    private DownloadContext downloadContext;

    private static ADPlayerBackendService instance;

    public static ADPlayerBackendService getInstance() {
        return instance;
    }

    private void testDatabase() {

        DaoManager.getInstance().getDaoSession().getHistoryDao().deleteAll();
        DaoManager.getInstance().getDaoSession().getScheduleDao().deleteAll();
        DaoManager.getInstance().getDaoSession().getAdvertisingDao().deleteAll();

        String advertisingId = UUID.randomUUID().toString();
        Advertising advertising = new Advertising();
        advertising.setId(advertisingId);
        advertising.setName("452");
        advertising.setDescription("描述");
        advertising.setUrl("http://localhost:8089/ad/player/program/452.zip");
        DaoManager.getInstance().getDaoSession().getAdvertisingDao().insert(advertising);

        History history = new History();
        history.setId(UUID.randomUUID().toString());
        history.setAdvertisingId(advertisingId);
        history.setPlayBeginTime(new Date());
        history.setPlayEndTime(new Date());
        DaoManager.getInstance().getDaoSession().getHistoryDao().insert(history);
        history = new History();
        history.setId(UUID.randomUUID().toString());
        history.setAdvertisingId(advertisingId);
        history.setPlayBeginTime(new Date());
        history.setPlayEndTime(new Date());
        DaoManager.getInstance().getDaoSession().getHistoryDao().insert(history);

        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID().toString());
        schedule.setAdvertisingId(advertisingId);
        schedule.setScheduleTime(new Date());
        DaoManager.getInstance().getDaoSession().getScheduleDao().insert(schedule);
        schedule = new Schedule();
        schedule.setId(UUID.randomUUID().toString());
        schedule.setAdvertisingId(advertisingId);
        schedule.setScheduleTime(new Date());
        DaoManager.getInstance().getDaoSession().getScheduleDao().insert(schedule);

        List<Advertising> list = DaoManager.getInstance().getDaoSession().getAdvertisingDao().loadAll();
        for (Advertising item : list) {
            item.getHistories();
            item.getSchedules();
            Log.d(TAG, "Advertising List: " + item);
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    private int adIndex = 0;

    /**
     * POSTING线程模型：在哪个线程发布事件，就在哪个线程执行onPostingEvent方法
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void onPostingEvent(MyEvent event) {
        Log.d(TAG, "onPostingEvent:" + Thread.currentThread().getName());
        if ("playerReady".equals(event.msg)) {
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    adIndex++;
                    PlayEvent playEvent = new PlayEvent("/sdcard/Advertising/json/demo" + (adIndex % 2 + 1) + ".json");
                    EventBus.getDefault().post(playEvent);
                    Log.d(TAG, "计算出当前应该播放的广告");
                }
            }, 1000, 5000);

//            PlayEvent playEvent = new PlayEvent("/sdcard/Advertising/json/demo" + (adIndex % 2 + 1) + ".json");
//            EventBus.getDefault().post(playEvent);
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            playEvent = new PlayEvent("/sdcard/Advertising/json/demo" + (adIndex % 2 + 2) + ".json");
//            EventBus.getDefault().post(playEvent);

        }
    }


    private Handler handler = new Handler();

    private Runnable task = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this, 5 * 1000);//设置延迟时间，此处是5秒
            //需要执行的代码
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        testDatabase();
        EventBus.getDefault().register(this);
        bunchDir = new File("/sdcard", "Advertising");
        initDownloadListener();
        download(new String[]{
                "http://192.168.1.4:8089/ad/player/program/452.zip",
                "http://192.168.1.4:8089/ad/player/program/361.zip"
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloadContext.stop();
        EventBus.getDefault().unregister(this);
    }


    private void download(String[] urls) {
        final long startTime = SystemClock.uptimeMillis();
        Log.e(TAG, "startTime= " + startTime);
        final DownloadContext.Builder builder = new DownloadContext.QueueSet()
                .setParentPathFile(bunchDir)
                .setMinIntervalMillisCallbackProcess(300)
                .commit();

        Log.d(TAG, "before bind bunch task consume " + (SystemClock.uptimeMillis() - startTime) + "ms");
        for (String url : urls) {
            String fileName = FileUtils.getFileName(url);
            builder.bind(new DownloadTask.Builder(url, bunchDir).setFilename(fileName));
        }

        totalCount = urls.length;
        currentCount = 0;

        Log.d(TAG, "before build bunch task consume " + (SystemClock.uptimeMillis() - startTime) + "ms");
        downloadContext = builder.setListener(new DownloadContextListener() {
            @Override
            public void taskEnd(@NonNull DownloadContext context,
                                @NonNull DownloadTask task,
                                @NonNull EndCause cause,
                                @Nullable Exception realCause,
                                int remainCount) {
            }

            @Override
            public void queueEnd(@NonNull DownloadContext context) {
                Log.e(TAG, "queueEnd");
            }
        }).build();

        Log.d(TAG, "before bunch task consume " + (SystemClock.uptimeMillis() - startTime) + "ms");
        downloadContext.start(listener, true);
        Log.d(TAG, "start bunch task consume " + (SystemClock.uptimeMillis() - startTime) + "ms");
    }

    private void initDownloadListener() {
        listener = new DownloadListener1() {

            @Override
            public void taskStart(@NonNull DownloadTask task,
                                  @NonNull Listener1Assist.Listener1Model model) {
                Log.e(TAG, "taskStart");
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
                Log.e(TAG, "retry");
            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset,
                                  long totalLength) {
                Log.e(TAG, "connected");
                updateBunchInfoAndProgress();
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                Log.e(TAG, "currentOffset= " + currentOffset);
                Log.e(TAG, "totalLength= " + totalLength);
            }

            @Override
            public void taskEnd(@NonNull final DownloadTask task, @NonNull EndCause cause,
                                @android.support.annotation.Nullable Exception realCause,
                                @NonNull Listener1Assist.Listener1Model model) {
                Log.e(TAG, "taskEnd= " + task + " end " + cause);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.unzip(task.getFile().getAbsolutePath(), "/sdcard/Advertising/" + task.getFilename().split("[.]")[0] + "/");
                    }
                }).start();

                currentCount += 1;
                updateBunchInfoAndProgress();
            }
        };
    }

    private void updateBunchInfoAndProgress() {
        Log.e(TAG, "Total Progress: " + currentCount + "/" + totalCount);
    }


}
