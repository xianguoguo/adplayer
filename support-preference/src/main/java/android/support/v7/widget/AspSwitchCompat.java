/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;

/**
 * Works around https://code.google.com/p/android/issues/detail?id=196652.
 */
@RestrictTo(RestrictTo.Scope.GROUP_ID)
public class AspSwitchCompat extends SwitchCompat {
    private boolean isInSetChecked = false;

    public AspSwitchCompat(Context context) {
        super(context);
    }

    public AspSwitchCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        isInSetChecked = true;
        super.setChecked(checked);
        isInSetChecked = false;
    }

    @Override
    public boolean isShown() {
        if (isInSetChecked) {
            return getVisibility() == VISIBLE;
        }
        return super.isShown();
    }
}
