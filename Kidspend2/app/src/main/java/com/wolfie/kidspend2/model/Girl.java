package com.wolfie.kidspend2.model;

import android.support.annotation.DrawableRes;

public enum Girl {
    CLAIRE("Claire"),
    NINA("Nina"),
    RACHEL("Rachel");

    public final String mName;
    private int[] mImageIds;

    Girl(String name) {
        mName = name;
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

}
