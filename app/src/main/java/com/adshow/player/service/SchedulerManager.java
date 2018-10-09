package com.adshow.player.service;

import android.os.CountDownTimer;
import android.util.Log;

import com.adshow.player.bean.Schedule;
import com.adshow.player.dao.ScheduleDao;
import com.adshow.player.event.PlayEvent;
import com.adshow.player.util.DaoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchedulerManager {

    private static final String TAG = SchedulerManager.class.getCanonicalName();

    private static SchedulerManager mInstance;

    private CountDownTimer mStartCountDownTimer;

    private List<Schedule> list = new ArrayList<>();

    private int index;

    private SchedulerManager() {

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

        List<Schedule> list = DaoManager.getInstance().getDaoSession().getScheduleDao().queryBuilder().orderAsc()
                .where(ScheduleDao.Properties.BeginDate.ge(todayBegin), ScheduleDao.Properties.BeginDate.le(todayEnd))
                .list();

        index = 0;

        playNext();
    }


    private void playNext() {

        mStartCountDownTimer = new CountDownTimer(list.get(index).getDuration(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                index++;
                if (index > list.size() - 1) {
                    index = 0;
                }
                playNext();
            }
        };

        mStartCountDownTimer.start();

        //发送播放事件给Activity
        PlayEvent playEvent = new PlayEvent("/sdcard/Advertising/" + list.get(index).getAdvertisingId() + "/config.json");
        EventBus.getDefault().post(playEvent);
        Log.d(TAG, "计算出当前应该播放的广告");
    }
}
