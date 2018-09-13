package com.adshow.player.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.adshow.player.R;
import com.adshow.player.activitys.downloadProcess.DownloadProcessActivity;
import com.adshow.player.activitys.fullscreen.FullScreenPlayActivity;
import com.adshow.player.activitys.playProcess.PlayTimelineActivity;
import com.adshow.player.views.FocusPercentRelativeLayout;

public class LocalServiceFragment extends Fragment implements View.OnClickListener {

    Button playList;
    Button downloadList;
    Button play;
    Button statistics;
    Button notification;
    private FocusPercentRelativeLayout layout;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_local_service, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        playList = (Button) view.findViewById(R.id.playList);
        downloadList = (Button) view.findViewById(R.id.downloadList);
        play = (Button) view.findViewById(R.id.play);
        statistics = (Button) view.findViewById(R.id.statistics);
        notification = (Button) view.findViewById(R.id.notification);
        layout = (FocusPercentRelativeLayout) view.findViewById(R.id.playingFocusLayout);

        playList.setOnClickListener(this);
        downloadList.setOnClickListener(this);
        play.setOnClickListener(this);
        statistics.setOnClickListener(this);
        notification.setOnClickListener(this);

        new Handler().postDelayed(new Runnable() {

            public void run() {
                play.setFocusable(true);
                play.setFocusableInTouchMode(true);
                play.requestFocus();
                play.requestFocusFromTouch();
                layout.initFocusView(play);
            }

        }, 500);

        new Handler().postDelayed(new Runnable() {

            public void run() {
//                Intent intent = new Intent("com.android.settings.action.REQUEST_POWER_ON");
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//                AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                long time = System.currentTimeMillis() + 1000 * 1;
//                am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, pendingIntent);

//                Intent newIntent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN"/*Intent.ACTION_REQUEST_SHUTDOWN*/);
//                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(newIntent);
            }

        }, 5000);

    }

//    public static String getConnectedType(Context context) {
//        String macAddress = null;
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
//            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
//                int type = mNetworkInfo.getType();
//
//                if (type == ConnectivityManager.TYPE_ETHERNET) {
//                    EthernetManager mEthManager = (EthernetManager) context.getSystemService(Context.ETHERNET_SERVICE);
//                    macAddress = mEthManager.getDevInfo().getHwaddr().toUpperCase();
//                } else if (type == ConnectivityManager.TYPE_WIFI) {
//                    WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                    macAddress = mWifiManager.getConnectionInfo().getMacAddress().toUpperCase();
//                }
//            }
//        }
//        return macAddress;
//    }


    @Override
    public void onClick(View v) {
        Intent jumpIntent;
        switch (v.getId()) {
            case R.id.playList:
                jumpIntent = new Intent(context, PlayTimelineActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.downloadList:
                jumpIntent = new Intent(context, DownloadProcessActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.play:
                jumpIntent = new Intent(context, FullScreenPlayActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.statistics:
                break;
            case R.id.notification:
                break;
        }
        Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
    }
}