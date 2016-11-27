package com.wolfie.kidspend2.model;

import android.support.annotation.DrawableRes;

import com.wolfie.kidspend2.R;

/**
 * Fixed set of all images used by Girl class.
 */

public enum ImageCollection {

    IMAGE38(Girl.CLAIRE, R.mipmap.ic_launcher);

    private Girl mGirl;
    private @DrawableRes int mImageResourceId;

    ImageCollection(Girl girl, int imageResourceId) {
        mGirl = girl;
        mImageResourceId = imageResourceId;
    }

    /**
     * @param girl
     * @return the array of all image resource ids for the specified Girl
     */
    public static int[] getImageIds(Girl girl) {
        int size = 0;
        for (ImageCollection image : ImageCollection.values()) {
            if (image.mGirl == girl) {
                size++;
            }
        }
        int[] imageIds = new int[size];
        size = 0;
        for (ImageCollection image : ImageCollection.values()) {
            if (image.mGirl == girl) {
                imageIds[size++] = image.mImageResourceId;
            }
        }
        return imageIds;
    }
}
