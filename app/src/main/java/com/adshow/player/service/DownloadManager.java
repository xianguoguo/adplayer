package com.adshow.player.service;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.adshow.player.util.AppUtils;
import com.adshow.player.util.FileUtils;
import com.adshow.player.util.SharedUtil;
import com.liulishuo.okdownload.DownloadContext;
import com.liulishuo.okdownload.DownloadContextListener;
import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;

public class DownloadManager {

    private static final String TAG = DownloadManager.class.getCanonicalName();

    private static final String DOWNLOAD_URL = "%s/ad/program/download/%s.zip";

    private static final File bunchDir = new File("/sdcard", "Advertising");

    private static DownloadManager mInstance;

    private static DownloadListener listener;

    private static DownloadContext downloadContext;

    private static DownloadCallbackBus mCallback;

    private DownloadManager() {
        mCallback = new DownloadCallbackBus();
        initDownloadListener();
    }

    public static DownloadManager getInstance() {
        if (null == mInstance) {
            mInstance = new DownloadManager();
        }
        return mInstance;
    }


    public static void stop() {
        try {
            if (mInstance != null) {
                downloadContext.stop();
                mInstance = null;
            }
        } catch (Exception e) {

        }
    }


    public static void download(String programId) {

        String server = SharedUtil.getString(ADPlayerBackendService.getInstance().getApplicationContext(), "server", null);
        String url = String.format(DOWNLOAD_URL, server, programId);

        final long startTime = SystemClock.uptimeMillis();
        Log.e(TAG, "startTime= " + startTime);
        final DownloadContext.Builder builder = new DownloadContext.QueueSet()
                .setParentPathFile(bunchDir)
                .setMinIntervalMillisCallbackProcess(300)
                .commit();

        Log.d(TAG, "before bind bunch task consume " + (SystemClock.uptimeMillis() - startTime) + "ms");
        String fileName = FileUtils.getFileName(url);
        builder.bind(new DownloadTask.Builder(url, bunchDir).setFilename(fileName));

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
                String md5 = AppUtils.getFileMD5(task.getFile());
                System.out.println("md5 is: " + md5);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.unzip(task.getFile().getAbsolutePath(), "/sdcard/Advertising/" + task.getFilename().split("[.]")[0] + "/");
                        //重新计算播放列表
                        SchedulerManager.getInstance().initScheduler();
                    }
                }).start();
            }
        };
    }

}
