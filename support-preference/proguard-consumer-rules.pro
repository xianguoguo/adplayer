-keepclassmembernames class android.support.v7.preference.PreferenceManager {
    void setNoCommit(boolean);
}

-keepclassmembernames class android.support.v7.preference.PreferenceFragmentCompat {
    android.support.v7.preference.PreferenceManager mPreferenceManager;
}

-dontwarn net.xpece.android.support.preference.SeekBarPreference
-dontwarn net.xpece.android.support.preference.SeekBarDialogPreference

-dontwarn net.xpece.android.support.widget.XpListPopupWindow
-dontwarn net.xpece.android.support.widget.AbstractXpListPopupWindow
