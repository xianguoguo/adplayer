package com.adshow.player.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.adshow.player.R;
import com.adshow.player.activitys.setting.SettingFragment;
import com.adshow.player.adapter.MainActivityAdapter;
import com.adshow.player.service.ADPlayerBackendService;
import com.adshow.player.views.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private MyViewPager mViewPager;
    private RadioButton localService;
    private RadioButton setting;

    private List<Fragment> fragments = new ArrayList<>();
    private View mViews[];
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
        initFragment();
        Intent intent = new Intent(this, ADPlayerBackendService.class);
        startService(intent);
    }

    private void initView() {
        mViewPager = (MyViewPager) this.findViewById(R.id.main_viewpager);
        localService = (RadioButton) findViewById(R.id.main_title_local);
        setting = (RadioButton) findViewById(R.id.main_title_setting);
        localService.setSelected(true);
        mViews = new View[]{localService, setting};
    }

    private void setListener() {
        localService.setOnClickListener(this);
        setting.setOnClickListener(this);

        localService.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mViewPager.setCurrentItem(0);
                }
            }
        });
        setting.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mViewPager.setCurrentItem(1);
                }
            }
        });

    }

    // 初始化Fragment
    private void initFragment() {
        fragments.clear();//清空

        LocalServiceFragment interactTV = new LocalServiceFragment();
        SettingFragment setting = new SettingFragment();

        fragments.add(interactTV);
        fragments.add(setting);

        MainActivityAdapter mAdapter = new MainActivityAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                switch (position) {
                    case 0:
                        localService.setSelected(true);
                        MainActivity.this.setting.setSelected(false);
                        break;
                    case 1:
                        localService.setSelected(false);
                        MainActivity.this.setting.setSelected(true);
                        break;
                }
            }
        });
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_title_local:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.main_title_setting:
                mViewPager.setCurrentItem(1);
                break;
        }
    }

    /**
     * 顶部焦点获取
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean focusFlag = false;
        for (View v : mViews) {
            if (v.isFocused()) {
                focusFlag = true;
            }
        }
        if (focusFlag) {
            if (KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                if (mCurrentIndex > 0) {
                    mViews[--mCurrentIndex].requestFocus();
                }
                return true;
            } else if (KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                if (mCurrentIndex < 1) {
                    mViews[++mCurrentIndex].requestFocus();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}