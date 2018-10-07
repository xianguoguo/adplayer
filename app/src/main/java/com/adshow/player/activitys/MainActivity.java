package com.adshow.player.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.adshow.player.R;
import com.adshow.player.activitys.app.AppAutoRun;
import com.adshow.player.activitys.downloadProcess.DownloadProcessActivity;
import com.adshow.player.activitys.fullscreen.PlayerActivity;
import com.adshow.player.activitys.playProcess.PlayTimelineActivity;
import com.adshow.player.service.ADPlayerBackendService;
import com.adshow.player.service.response.RestResult;
import com.adshow.player.util.DeviceUtil;
import com.adshow.player.views.FocusPercentRelativeLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private FocusPercentRelativeLayout layout;

    private Context context;

    private TextView[] clickTexts;

    private PercentRelativeLayout[] clickLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        layout = findViewById(R.id.focusLayout);
        clickLayouts = new PercentRelativeLayout[]{
                (PercentRelativeLayout) findViewById(R.id.playList),
                (PercentRelativeLayout) findViewById(R.id.downloadList),
                (PercentRelativeLayout) findViewById(R.id.weatherSetting),
                (PercentRelativeLayout) findViewById(R.id.systemLogin),
                (PercentRelativeLayout) findViewById(R.id.play),
                (PercentRelativeLayout) findViewById(R.id.setting)
        };

        clickTexts = new TextView[]{
                (TextView) findViewById(R.id.playList_text),
                (TextView) findViewById(R.id.downloadList_text),
                (TextView) findViewById(R.id.weatherSetting_text),
                (TextView) findViewById(R.id.systemLogin_text),
                (TextView) findViewById(R.id.play_text),
                (TextView) findViewById(R.id.setting_text)
        };

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.systemLogin) {
                    startActivity(new Intent(context, LoginActivity.class));
                    return;
                }

                if (ADPlayerBackendService.getInstance() == null || ADPlayerBackendService.getInstance().getWindowManager() == null) {
                    return;
                }

                if (DeviceUtil.getUserToken() == null || TextUtils.isEmpty(DeviceUtil.getUserToken().getAccessToken())
                        || (DeviceUtil.getUserToken().getExpiresIn().getTime() - System.currentTimeMillis()) / (60 * 60 * 24 * 1000) < 7) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    return;
                }

                Call<RestResult<Object>> call = ADPlayerBackendService.getInstance().getRestApi().validateToken();
                call.enqueue(new Callback<RestResult<Object>>() {
                    @Override
                    public void onResponse(Call<RestResult<Object>> call, Response<RestResult<Object>> response) {
                        if (!response.body().isSuccess()) {
                            startActivity(new Intent(context, LoginActivity.class));
                            return;
                        }

                        System.out.println("accessToken 校验成功");
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
                                jumpIntent = new Intent(context, PlayerActivity.class);
                                startActivity(jumpIntent);
                                break;
//                                case R.id.weatherSetting:
//                                    jumpIntent = new Intent(context, GarbageClear.class);
//                                    startActivity(jumpIntent);
//                                    break;
//                                case R.id.connectTesting:
//                                    jumpIntent = new Intent(context, SpeedTestActivity.class);
//                                    startActivity(jumpIntent);
//                                    break;
//                                case R.id.garbageClear:
//                                    jumpIntent = new Intent(context, GarbageClear.class);
//                                    startActivity(jumpIntent);
//                                    break;
                            case R.id.setting:
                                jumpIntent = new Intent(context, AppAutoRun.class);
                                startActivity(jumpIntent);
                                break;
//                                case R.id.aboutUs:
//                                    jumpIntent = new Intent(context, AppAutoRun.class);
//                                    startActivity(jumpIntent);
//                                    break;
                        }
                    }

                    @Override
                    public void onFailure(Call<RestResult<Object>> call, Throwable t) {
                        System.out.println("accessToken 校验失败");
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.run();
                    }
                });
                return;
            }
        };

        for (PercentRelativeLayout layout : clickLayouts) {
            layout.setOnClickListener(clickListener);
        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/helvetica_neueltpro_thex.otf");
        for (TextView text : clickTexts) {
            text.setTypeface(typeface);
        }

    }

}