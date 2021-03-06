package net.xpece.android.support.preference;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

/**
 * @author Eugen on 6. 12. 2015.
 */
public interface TintablePreference {

    boolean isSupportIconTintEnabled();

    void setSupportIconTintEnabled(boolean enabled);

    /**
     * Applies a tint to the icon drawable. Does not modify the current tint
     * mode, which is {@link PorterDuff.Mode#SRC_IN} by default.
     * <p>
     * Subsequent calls to {@code android.support.v7.preference.Preference.setIcon(Drawable)} will automatically
     * mutate the drawable and apply the specified tint and tint mode.
     *
     * @param tint the tint to apply, may be {@code null} to clear tint
     *
     * @see #getSupportIconTintList()
     */
    void setSupportIconTintList(@Nullable ColorStateList tint);

    /**
     * Return the tint applied to the icon drawable, if specified.
     *
     * @return the tint applied to the icon drawable
     */
    @Nullable
    ColorStateList getSupportIconTintList();

    /**
     * Specifies the blending mode used to apply the tint specified by
     * {@link #setSupportIconTintList(ColorStateList)}} to the icon
     * drawable. The default mode is {@link PorterDuff.Mode#SRC_IN}.
     *
     * @param tintMode the blending mode used to apply the tint, may be
     *                 {@code null} to clear tint
     * @see #getSupportIconTintMode()
     */
    void setSupportIconTintMode(@Nullable PorterDuff.Mode tintMode);

    /**
     * Return the blending mode used to apply the tint to the icon
     * drawable, if specified.
     *
     * @return the blending mode used to apply the tint to the icon
     *         drawable
     */
    @Nullable
    PorterDuff.Mode getSupportIconTintMode();
}
