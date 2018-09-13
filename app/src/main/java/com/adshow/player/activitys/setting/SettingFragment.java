package com.adshow.player.activitys.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.adshow.player.R;
import com.adshow.player.activitys.LoginActivity;
import com.adshow.player.activitys.app.AppAutoRun;
import com.adshow.player.activitys.downloadProcess.DownloadProcessActivity;
import com.adshow.player.activitys.garbageclear.GarbageClear;
import com.adshow.player.activitys.playProcess.PlayTimelineActivity;
import com.adshow.player.activitys.speedtest.SpeedTestActivity;
import com.adshow.player.views.FocusPercentRelativeLayout;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private Button weatherSetting;
    private Button connectTesting;
    private Button garbageClear;
    private Button autoRunSetting;
    private Button systemLogin;
    private Button aboutUs;
    private FocusPercentRelativeLayout layout;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_setting, container, false);
        initView(view);
        setListener();
        return view;
    }

    private void initView(View view) {
        weatherSetting = (Button) view.findViewById(R.id.weatherSetting);
        connectTesting = (Button) view.findViewById(R.id.connectTesting);
        garbageClear = (Button) view.findViewById(R.id.garbageClear);
        autoRunSetting = (Button) view.findViewById(R.id.autoRunSetting);
        systemLogin = (Button) view.findViewById(R.id.systemLogin);
        aboutUs = (Button) view.findViewById(R.id.aboutUs);
        layout = (FocusPercentRelativeLayout) view.findViewById(R.id.settingFocusLayout);
        new Handler().postDelayed(new Runnable() {

            public void run() {
                weatherSetting.setFocusable(true);
                weatherSetting.setFocusableInTouchMode(true);
                weatherSetting.requestFocus();
                weatherSetting.requestFocusFromTouch();
                layout.initFocusView(weatherSetting);
            }

        }, 500);
    }

    private void setListener() {
        weatherSetting.setOnClickListener(this);
        connectTesting.setOnClickListener(this);
        garbageClear.setOnClickListener(this);
        autoRunSetting.setOnClickListener(this);
        systemLogin.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent jumpIntent;
        switch (view.getId()) {
            case R.id.weatherSetting:
//                jumpIntent = new Intent(context, GarbageClear.class);
//                startActivity(jumpIntent);
                break;
            case R.id.connectTesting:
                jumpIntent = new Intent(context, SpeedTestActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.garbageClear:
                jumpIntent = new Intent(context, GarbageClear.class);
                startActivity(jumpIntent);
                break;
            case R.id.autoRunSetting:
                jumpIntent = new Intent(context, AppAutoRun.class);
                startActivity(jumpIntent);
                break;
            case R.id.systemLogin:
                jumpIntent = new Intent(context, LoginActivity.class);
                startActivity(jumpIntent);
                break;
            case R.id.aboutUs:
//                jumpIntent = new Intent(context, AppAutoRun.class);
//                startActivity(jumpIntent);
                break;
        }
    }
}