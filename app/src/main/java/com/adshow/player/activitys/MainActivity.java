package com.adshow.player.activitys;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adshow.player.R;
import com.adshow.player.activitys.app.AppAutoRun;
import com.adshow.player.activitys.downloadProcess.DownloadProcessActivity;
import com.adshow.player.activitys.fullscreen.PlayerActivity;
import com.adshow.player.activitys.playProcess.PlayTimelineActivity;
import com.adshow.player.activitys.schedule.ScreenOffAdminReceiver;
import com.adshow.player.service.ADPlayerBackendService;
import com.adshow.player.views.FocusPercentRelativeLayout;

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
                    case R.id.weatherSetting:
//                jumpIntent = new Intent(context, GarbageClear.class);
//                startActivity(jumpIntent);
                        break;
//            case R.id.connectTesting:
//                jumpIntent = new Intent(context, SpeedTestActivity.class);
//                startActivity(jumpIntent);
//                break;
//            case R.id.garbageClear:
//                jumpIntent = new Intent(context, GarbageClear.class);
//                startActivity(jumpIntent);
//                break;
                    case R.id.setting:
                        jumpIntent = new Intent(context, AppAutoRun.class);
                        startActivity(jumpIntent);
                        break;
                    case R.id.systemLogin:
                        jumpIntent = new Intent(context, LoginActivity.class);
                        startActivity(jumpIntent);
                        break;
//            case R.id.aboutUs:
//                jumpIntent = new Intent(context, AppAutoRun.class);
//                startActivity(jumpIntent);
//                break;
                }
            }
        };

        for (PercentRelativeLayout layout : clickLayouts) {
            layout.setOnClickListener(clickListener);
        }

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "font/helvetica_neueltpro_thex.otf");
        for (TextView text : clickTexts) {
            text.setTypeface(typeface);
        }


        Intent intent = new Intent(this, ADPlayerBackendService.class);
        startService(intent);

        intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(this.getApplicationContext(), ScreenOffAdminReceiver.class));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "开启后就可以使用锁屏功能了...");
        startActivityForResult(intent, 0);


    }

}