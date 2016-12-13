package com.wolfie.kidspend2.model;

import android.support.annotation.DrawableRes;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;

public enum Girl {
    CLAIRE("Claire", R.drawable.alogo_claire_scaled, R.drawable.alogo_claire_scaled),
    NINA("Nina", R.drawable.alogo_nina_scaled, R.drawable.alogo_nina_scaled),
    RACHEL("Rachel", R.drawable.alogo_rachel_scaled, R.drawable.alogo_rachel_scaled);

    public final String mName;
    // The imageIds and imageShades arrays hold the data for the Girl instance's images,
    // while the iconIds references the icons for this Girl (for different image shades).
    private @DrawableRes int[] mImageIds;
    private @ImageShade int[] mImageShades;

    private @DrawableRes int mIconDarkId;     // For use with ImageShade.DARK images.
    private @DrawableRes int mIconLightId;    // For use with ImageShade.LIGHT images.

    Girl(String name, @DrawableRes int iconDarkId, @DrawableRes int iconLightId) {
        mName = name;
        mIconDarkId = iconDarkId;
        mIconLightId = iconLightId;
    }

    public @DrawableRes int getIconResourceId(@ImageShade int shade) {
        return (shade == ImageShade.DARK) ? mIconDarkId : mIconLightId;
    }

    public int getImageCount() {
        return mImageIds.length;
    }

    public @DrawableRes int getImageResourceId(int index) {
        if (mImageIds == null) {
            mImageIds = ImageCollection.getImageIds(this);
        }
        return mImageIds[index];
    }

    public @ImageShade int getImageShade(int index) {
        if (mImageShades == null) {
            mImageShades = ImageCollection.getImageShades(this);
        }
        return mImageShades[index];
    }
}
