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
import android.widget.ImageButton;
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

    }

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