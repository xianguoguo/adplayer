package com.adshow.player.widgets;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeTextViewWrapper extends AppCompatTextView {

    private static final int TIME_TICK = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TIME_TICK:
                    long time = System.currentTimeMillis();
                    Date data = new Date(time);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm:ss", Locale.CHINESE);
                    setText(simpleDateFormat.format(data));
                    break;
                default:
                    break;
            }
        }
    };


    public DateTimeTextViewWrapper(Context context) {
        this(context, null);
    }

    public DateTimeTextViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimeTextViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setId(View.generateViewId());
        new TimeThread("DateTime-Refresh-Thread", this).start();
    }


    public class TimeThread extends Thread {

        private DateTimeTextViewWrapper textView;

        public TimeThread(String name, DateTimeTextViewWrapper textView) {
            super(name);
            this.textView = textView;
        }

        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = TIME_TICK;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }
}
