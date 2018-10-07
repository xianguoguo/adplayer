package com.adshow.player.activitys.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.adshow.player.R;

import java.util.Calendar;
import java.util.StringTokenizer;


/**
 * Created by Calin Martinconi on 2/11/14.
 */
public class TimePickerPreference extends DialogPreference {
    private int hour=0;
    private int minute=0;
    private TimePicker picker=null;


    public TimePickerPreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        setPositiveButtonText(ctxt.getString(R.string.time_picker_set));
        setNegativeButtonText(null);
    }

    @Override
    protected View onCreateDialogView() {
        picker=new TimePicker(getContext());
        return(picker);
    }

    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);

        picker.setCurrentHour(hour);
        picker.setCurrentMinute(minute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            hour=picker.getCurrentHour();
            minute=picker.getCurrentMinute();

            String time=String.valueOf(hour)+":"+String.valueOf(minute);

            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return(a.getString(index));
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {

        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

    }

    public void setTimeVales(String sTime){
        StringTokenizer stringTokenizer = new StringTokenizer(sTime, ":");
        hour = Integer.parseInt(stringTokenizer.nextToken());
        minute = Integer.parseInt(stringTokenizer.nextToken());
    }

}
