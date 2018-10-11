package com.adshow.player.service;

import android.util.Log;

import com.adshow.player.bean.Schedule;
import com.adshow.player.dao.ScheduleDao;
import com.adshow.player.event.PlayEndEvent;
import com.adshow.player.event.PlayStartEvent;
import com.adshow.player.util.DaoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerManager {

    private static final String TAG = SchedulerManager.class.getCanonicalName();

    private static SchedulerManager mInstance;

    private ScheduledExecutorService executor;

    private List<Schedule> list = new ArrayList<>();

    private int index;

    private SchedulerManager() {
        executor = Executors.newScheduledThreadPool(1);
    }


    public static SchedulerManager getInstance() {
        if (null == mInstance) {
            mInstance = new SchedulerManager();
        }
        return mInstance;
    }


    public static void stop() {

    }


    public void initScheduler() {

        Date todayBegin = new Date();
        todayBegin.setHours(0);
        todayBegin.setMinutes(0);
        todayBegin.setSeconds(0);

        Date todayEnd = new Date();
        todayEnd.setHours(23);
        todayEnd.setMinutes(59);
        todayEnd.setSeconds(59);

        list = DaoManager.getInstance().getDaoSession().getScheduleDao().queryBuilder().orderAsc(ScheduleDao.Properties.Order)
                .where(ScheduleDao.Properties.BeginDate.le(todayEnd), ScheduleDao.Properties.EndDate.ge(todayBegin)).list();

        index = 0;

        playNext();
    }


    public void playNext() {
        if (list.size() == 0) {
            Log.d(TAG, "没有广告");
            return;
        }
        //发送播放事件给Activity
//        PlayStartEvent playEvent = new PlayStartEvent("/sdcard/Advertising/" + 465 + "/config.json");
        PlayStartEvent playEvent = new PlayStartEvent("/sdcard/Advertising/" + list.get(index).getAdvertisingId() + "/config.json");
        EventBus.getDefault().post(playEvent);
        executor.schedule(new Runnable() {
            public void run() {
                Log.d(TAG, "时间到了, 下一个节目");
                EventBus.getDefault().post(new PlayEndEvent());
            }
        }, (list.get(index).getDuration() == null ? 30 : list.get(index).getDuration()) * 1000, TimeUnit.MILLISECONDS);

        Log.d(TAG, "计算出当前应该播放的广告");
    }
}
