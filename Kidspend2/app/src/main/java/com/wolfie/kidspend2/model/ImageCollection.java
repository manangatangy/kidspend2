package com.wolfie.kidspend2.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;

import com.wolfie.kidspend2.R;

/**
 * Fixed set of all images used by Girl class.
 */

public enum ImageCollection {

    CLAIRE00(Girl.CLAIRE, R.drawable.claire_00, ImageShade.DARK),
//    CLAIRE00(Girl.CLAIRE, R.drawable.android_logo_1, ImageShade.DARK),

    CLAIRE01(Girl.CLAIRE, R.drawable.claire_01, ImageShade.DARK),
    CLAIRE02(Girl.CLAIRE, R.drawable.claire_02, ImageShade.DARK),
    CLAIRE03(Girl.CLAIRE, R.drawable.claire_03, ImageShade.DARK),
    CLAIRE04(Girl.CLAIRE, R.drawable.claire_04, ImageShade.DARK),
    CLAIRE05(Girl.CLAIRE, R.drawable.claire_05, ImageShade.DARK),
    CLAIRE06(Girl.CLAIRE, R.drawable.claire_06, ImageShade.DARK),
    CLAIRE07(Girl.CLAIRE, R.drawable.claire_07, ImageShade.DARK),
    CLAIRE08(Girl.CLAIRE, R.drawable.claire_08, ImageShade.DARK),
    CLAIRE09(Girl.CLAIRE, R.drawable.claire_09, ImageShade.DARK),
    CLAIRE10(Girl.CLAIRE, R.drawable.claire_10, ImageShade.DARK),
    CLAIRE11(Girl.CLAIRE, R.drawable.claire_11, ImageShade.DARK),
    CLAIRE12(Girl.CLAIRE, R.drawable.claire_12, ImageShade.DARK),
    CLAIRE13(Girl.CLAIRE, R.drawable.claire_13, ImageShade.DARK),
    CLAIRE14(Girl.CLAIRE, R.drawable.claire_14, ImageShade.DARK),
    CLAIRE15(Girl.CLAIRE, R.drawable.claire_15, ImageShade.DARK),
    CLAIRE16(Girl.CLAIRE, R.drawable.claire_16, ImageShade.DARK),
    CLAIRE17(Girl.CLAIRE, R.drawable.claire_17, ImageShade.DARK),

    NINA00(Girl.NINA, R.drawable.nina_00, ImageShade.DARK),
//    NINA00(Girl.NINA, R.drawable.android_logo_2, ImageShade.DARK),

    NINA01(Girl.NINA, R.drawable.nina_01, ImageShade.DARK),
    NINA02(Girl.NINA, R.drawable.nina_02, ImageShade.DARK),
    NINA03(Girl.NINA, R.drawable.nina_03, ImageShade.DARK),
    NINA04(Girl.NINA, R.drawable.nina_04, ImageShade.DARK),
    NINA05(Girl.NINA, R.drawable.nina_05, ImageShade.DARK),
    NINA06(Girl.NINA, R.drawable.nina_06, ImageShade.DARK),
    NINA07(Girl.NINA, R.drawable.nina_07, ImageShade.DARK),
    NINA08(Girl.NINA, R.drawable.nina_08, ImageShade.DARK),
    NINA09(Girl.NINA, R.drawable.nina_09, ImageShade.DARK),
    NINA10(Girl.NINA, R.drawable.nina_10, ImageShade.DARK),
    NINA11(Girl.NINA, R.drawable.nina_11, ImageShade.DARK),
    NINA12(Girl.NINA, R.drawable.nina_12, ImageShade.DARK),
    NINA13(Girl.NINA, R.drawable.nina_13, ImageShade.DARK),
    NINA14(Girl.NINA, R.drawable.nina_14, ImageShade.DARK),
    NINA15(Girl.NINA, R.drawable.nina_15, ImageShade.DARK),
    NINA16(Girl.NINA, R.drawable.nina_16, ImageShade.DARK),
    NINA17(Girl.NINA, R.drawable.nina_17, ImageShade.DARK),
    NINA18(Girl.NINA, R.drawable.nina_18, ImageShade.DARK),
    NINA19(Girl.NINA, R.drawable.nina_19, ImageShade.DARK),
    NINA20(Girl.NINA, R.drawable.nina_20, ImageShade.DARK),

    RACHEL00(Girl.RACHEL, R.drawable.rachel_00, ImageShade.DARK),
//    RACHEL00(Girl.RACHEL, R.drawable.android_logo_3, ImageShade.DARK),

    RACHEL01(Girl.RACHEL, R.drawable.rachel_01, ImageShade.DARK),
    RACHEL02(Girl.RACHEL, R.drawable.rachel_02, ImageShade.DARK),
    RACHEL03(Girl.RACHEL, R.drawable.rachel_03, ImageShade.DARK),
    RACHEL04(Girl.RACHEL, R.drawable.rachel_04, ImageShade.DARK),
    RACHEL05(Girl.RACHEL, R.drawable.rachel_05, ImageShade.DARK),
    RACHEL06(Girl.RACHEL, R.drawable.rachel_06, ImageShade.DARK),
    RACHEL07(Girl.RACHEL, R.drawable.rachel_07, ImageShade.DARK),
    RACHEL08(Girl.RACHEL, R.drawable.rachel_08, ImageShade.DARK),
    RACHEL09(Girl.RACHEL, R.drawable.rachel_09, ImageShade.DARK),
    RACHEL10(Girl.RACHEL, R.drawable.rachel_10, ImageShade.DARK),
    RACHEL11(Girl.RACHEL, R.drawable.rachel_11, ImageShade.DARK),
    RACHEL12(Girl.RACHEL, R.drawable.rachel_12, ImageShade.DARK),
    RACHEL13(Girl.RACHEL, R.drawable.rachel_13, ImageShade.DARK),
    RACHEL14(Girl.RACHEL, R.drawable.rachel_14, ImageShade.DARK),
    RACHEL15(Girl.RACHEL, R.drawable.rachel_15, ImageShade.DARK),
    RACHEL16(Girl.RACHEL, R.drawable.rachel_16, ImageShade.DARK),
    RACHEL17(Girl.RACHEL, R.drawable.rachel_17, ImageShade.DARK),
    RACHEL18(Girl.RACHEL, R.drawable.rachel_18, ImageShade.DARK),
    RACHEL19(Girl.RACHEL, R.drawable.rachel_19, ImageShade.DARK),
    RACHEL20(Girl.RACHEL, R.drawable.rachel_20, ImageShade.DARK),
    RACHEL21(Girl.RACHEL, R.drawable.rachel_21, ImageShade.DARK),
    RACHEL22(Girl.RACHEL, R.drawable.rachel_22, ImageShade.DARK),
    RACHEL23(Girl.RACHEL, R.drawable.rachel_23, ImageShade.DARK),
    RACHEL24(Girl.RACHEL, R.drawable.rachel_24, ImageShade.DARK),
    RACHEL25(Girl.RACHEL, R.drawable.rachel_25, ImageShade.DARK),
    RACHEL26(Girl.RACHEL, R.drawable.rachel_26, ImageShade.DARK);

    private Girl mGirl;
    private @DrawableRes int mImageResourceId;
    private @ImageShade int mImageShade;

    ImageCollection(Girl girl, int imageResourceId, int imageShade) {
        mGirl = girl;
        mImageResourceId = imageResourceId;
        mImageShade = imageShade;
    }

    /**
     * @param girl
     * @return the array of all image resource ids for the specified Girl
     */
    public static int[] getImageIds(Girl girl) {
        int[] imageIds = new int[size(girl)];
        int size = 0;
        for (ImageCollection image : ImageCollection.values()) {
            if (image.mGirl == girl) {
                imageIds[size++] = image.mImageResourceId;
            }
        }
        return imageIds;
    }

    /**
     * @param girl
     * @return the array of all image shade values for the specified Girl
     */
    public static @ImageShade int[] getImageShades(Girl girl) {
        @ImageShade int[] imageShades = new int[size(girl)];
        int size = 0;
        for (ImageCollection image : ImageCollection.values()) {
            if (image.mGirl == girl) {
                imageShades[size++] = image.mImageShade;
            }
        }
        return imageShades;
    }

    private static int size(Girl girl) {
        int size = 0;
        for (ImageCollection image : ImageCollection.values()) {
            if (image.mGirl == girl) {
                ++size;
            }
        }
        return size;
    }

    @IntDef({ImageShade.DARK, ImageShade.LIGHT})
    public @interface ImageShade {
        int DARK = 0;
        int LIGHT = 1;
    }
}
