package com.wolfie.kidspend2.model;

import android.support.annotation.DrawableRes;

public enum Girl {
    CLAIRE("Claire"),
    NINA("Nina"),
    RACHEL("Rachel");

    public final String mName;
    private final int[] mImageIds;

    Girl(String name) {
        mName = name;
        mImageIds = ImageCollection.getImageIds(this);
    }

    public int getImageCount() {
        return mImageIds.length;
    }

    public @DrawableRes int getImageResourceId(int index) {
        return mImageIds[index];
    }

}
