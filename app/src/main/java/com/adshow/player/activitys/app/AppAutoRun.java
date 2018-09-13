package com.adshow.player.activitys.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.adshow.player.R;


public class AppAutoRun extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "UPDATE";
    private SettingsFragment mSettingsFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.app_auto_run);


        if (savedInstanceState == null) {
            mSettingsFragment = SettingsFragment.newInstance(null);
            getSupportFragmentManager().beginTransaction().add(R.id.content, mSettingsFragment, "Settings").commit();
        } else {
            mSettingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentByTag("Settings");
        }
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "============onRestart========");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "============onStart========");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "============onDestroy========");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "=====onPause===========");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "=========onResume=======");
    }

    @Override
    public void onClick(View v) {

    }
}