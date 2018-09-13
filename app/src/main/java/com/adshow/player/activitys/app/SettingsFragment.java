package com.adshow.player.activitys.app;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.XpPreferenceFragment;
import android.support.v7.preference.XpPreferenceHelpers;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.adshow.player.R;

import net.xpece.android.support.preference.ListPreference;
import net.xpece.android.support.preference.MultiSelectListPreference;
import net.xpece.android.support.preference.OnPreferenceLongClickListener;
import net.xpece.android.support.preference.PreferenceCategory;
import net.xpece.android.support.preference.PreferenceDividerDecoration;
import net.xpece.android.support.preference.PreferenceScreenNavigationStrategy;
import net.xpece.android.support.preference.RingtonePreference;
import net.xpece.android.support.preference.SeekBarPreference;
import net.xpece.android.support.preference.SharedPreferencesCompat;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Eugen on 7. 12. 2015.
 */
public class SettingsFragment extends XpPreferenceFragment {
    private static final String TAG = SettingsFragment.class.getSimpleName();

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof SeekBarPreference) {
                SeekBarPreference pref = (SeekBarPreference) preference;
                int progress = (int) value;
                pref.setInfo(progress + "%");
            } else if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                    index >= 0
                        ? listPreference.getEntries()[index]
                        : null);
            } else if (preference instanceof MultiSelectListPreference) {
                String summary = stringValue.trim().substring(1, stringValue.length() - 1); // strip []
                preference.setSummary(summary);
            } else if (preference instanceof RingtonePreference) {
                // For ringtone preferences, look up the correct display value using RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Empty values correspond to 'silent' (no ringtone).
                    preference.setSummary(R.string.pref_ringtone_silent);
                } else {
                    final Uri selectedUri = Uri.parse(stringValue);
                    try {
                        final Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), selectedUri);
                        if (ringtone == null) {
                            // Clear the summary if there was a lookup error, i.e. does not exist.
                            preference.setSummary(null);
                        } else {
                            // Set the summary to reflect the new ringtone display name.
                            final String name = ringtone.getTitle(preference.getContext());
                            preference.setSummary(name);
                        }
                    } catch (SecurityException ex) {
                        // The user has selected a ringtone from external storage
                        // and then revoked READ_EXTERNAL_STORAGE permission.
                        // We have no way of guessing the ringtone title.
                        // We'd have to store the title of selected ringtone in prefs as well.
                        preference.setSummary("???");
                    }
                }

            } else {
                // For all other preferences, set the summary to the value's
                // simple string representation.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    public static SettingsFragment newInstance(String rootKey) {
        Bundle args = new Bundle();
        args.putString(SettingsFragment.ARG_PREFERENCE_ROOT, rootKey);
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreatePreferences2(final Bundle savedInstanceState, final String rootKey) {
        // Set an empty screen so getPreferenceScreen doesn't return null -
        // so we can create fake headers from the get-go.
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(getPreferenceManager().getContext()));

        // Add 'general' preferences.
        addPreferencesFromResource(R.xml.setting_general);

        // Add 'notifications' preferences, and a corresponding header.
        PreferenceCategory fakeHeader = new PreferenceCategory(getPreferenceManager().getContext());
        fakeHeader.setTitle(R.string.pref_header_notifications);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.setting_notification);

        // Add 'data and sync' preferences, and a corresponding header.
        fakeHeader = new PreferenceCategory(getPreferenceManager().getContext());
        fakeHeader.setTitle(R.string.pref_header_data_sync);
        fakeHeader.setTitleTextAppearance(R.style.TextAppearance_AppCompat_Button);
        fakeHeader.setTitleTextColor(ContextCompat.getColor(fakeHeader.getContext(), R.color.primary)); // No disabled color state please.
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.setting_data_sync);

        // Bind the summaries of EditText/List/Dialog/Ringtone preferences to
        // their values. When their values change, their summaries are updated
        // to reflect the new value, per the Android Design guidelines.
        bindPreferenceSummaryToValue(findPreference("example_text"));
        bindPreferenceSummaryToValue(findPreference("example_list"));
        bindPreferenceSummaryToValue(findPreference("notifications_new_message_ringtone"));
        bindPreferenceSummaryToValue(findPreference("sync_frequency"));
        bindPreferenceSummaryToValue(findPreference("notif_content"));

        // Setup SeekBarPreference "info" text field.
        final SeekBarPreference volume2 = (SeekBarPreference) findPreference("notifications_new_message_volume2");
        volume2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume2.setInfo(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // Setup an OnPreferenceLongClickListener via XpPreferenceHelpers.
        XpPreferenceHelpers.setOnPreferenceLongClickListener(findPreference("example_text"), new OnLongClickListenerSample());

        // Setup root preference title.
//        getPreferenceScreen().setTitle(R.string.app_name);
        getPreferenceScreen().setTitle(getActivity().getTitle());

        // Setup root preference.
        // Use with ReplaceFragment strategy.
        PreferenceScreenNavigationStrategy.ReplaceFragment.onCreatePreferences(this, rootKey);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Change activity title to preference title. Used with ReplaceFragment strategy.
        getActivity().setTitle(getPreferenceScreen().getTitle());
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        final String key = preference.getKey();
        if (preference instanceof MultiSelectListPreference) {
            Set<String> summary = SharedPreferencesCompat.getStringSet(
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()),
                key,
                new HashSet<String>());
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, summary);
        } else if (preference instanceof SeekBarPreference) {
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, ((SeekBarPreference) preference).getValue());
        } else {
            String value = PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(key, "");
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, value);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView listView = getListView();

        final int padding = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        listView.setPadding(0, padding, 0, padding);

        // We're using alternative divider.
        listView.addItemDecoration(new PreferenceDividerDecoration(getContext())
            .drawBetweenItems(false).paddingDp(listView.getContext(), 8));
        setDivider(null);

        // We don't want this. The children are still focusable.
        listView.setFocusable(false);
    }

    /**
     * No chance of outer class leaks when using another static class.
     * Method args give us everything we need anyway.
     */
    static class OnLongClickListenerSample implements OnPreferenceLongClickListener {
        @Override
        public boolean onLongClick(Preference preference, View view) {
            final Toast toast = Toast.makeText(preference.getContext(), "This showcases long click listeners on preferences.", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
    }
}
