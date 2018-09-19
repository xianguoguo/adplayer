package com.adshow.player.activitys.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.adshow.player.R;
import com.adshow.player.activitys.preference.TimePickerPreference;


public class AppAutoRun extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting_system);
        addPreferencesFromResource(R.xml.setting_auto);
        setContentView(R.layout.app_auto_run);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        init();

    }

    public void init() {

        TimePickerPreference timePickerPreference = (TimePickerPreference) findPreference("setting_auto_on");
        timePickerPreference.setSummary("07:00");
        timePickerPreference.setTimeVales("07:00");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("setting_auto_on", "07:00");
        editor.commit();

        timePickerPreference = (TimePickerPreference) findPreference("setting_auto_off");
        timePickerPreference.setSummary("23:30");
        timePickerPreference.setTimeVales("23:30");
        editor = sharedPreferences.edit();
        editor.putString("setting_auto_off", "23:30");
        editor.commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String s) {

        if (s.equals("time_picker")) {
            TimePickerPreference preference = (TimePickerPreference) findPreference(s);
            preference.setSummary(sharedPrefs.getString(s, ""));

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}