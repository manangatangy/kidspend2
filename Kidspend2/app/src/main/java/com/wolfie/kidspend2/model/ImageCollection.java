package com.wolfie.kidspend2.model;

import android.support.annotation.DrawableRes;

import com.wolfie.kidspend2.R;

/**
 * Fixed set of all images used by Girl class.
 */

public enum ImageCollection {

//    CLAIRE00(Girl.CLAIRE, R.drawable.claire_00),
    CLAIRE01(Girl.CLAIRE, R.drawable.claire_01),
    CLAIRE02(Girl.CLAIRE, R.drawable.claire_02),
    CLAIRE03(Girl.CLAIRE, R.drawable.claire_03),
    CLAIRE04(Girl.CLAIRE, R.drawable.claire_04),
    CLAIRE05(Girl.CLAIRE, R.drawable.claire_05),
    CLAIRE06(Girl.CLAIRE, R.drawable.claire_06),
    CLAIRE07(Girl.CLAIRE, R.drawable.claire_07),
    CLAIRE08(Girl.CLAIRE, R.drawable.claire_08),
    CLAIRE09(Girl.CLAIRE, R.drawable.claire_09),
    CLAIRE10(Girl.CLAIRE, R.drawable.claire_10),
    CLAIRE11(Girl.CLAIRE, R.drawable.claire_11),
    CLAIRE12(Girl.CLAIRE, R.drawable.claire_12),
    CLAIRE13(Girl.CLAIRE, R.drawable.claire_13),
    CLAIRE14(Girl.CLAIRE, R.drawable.claire_14),
    CLAIRE15(Girl.CLAIRE, R.drawable.claire_15),
    CLAIRE16(Girl.CLAIRE, R.drawable.claire_16),
    CLAIRE17(Girl.CLAIRE, R.drawable.claire_17),

//    NINA00(Girl.NINA, R.drawable.nina_00),
    NINA01(Girl.NINA, R.drawable.nina_01),
    NINA02(Girl.NINA, R.drawable.nina_02),
    NINA03(Girl.NINA, R.drawable.nina_03),
    NINA04(Girl.NINA, R.drawable.nina_04),
    NINA05(Girl.NINA, R.drawable.nina_05),
    NINA06(Girl.NINA, R.drawable.nina_06),
    NINA07(Girl.NINA, R.drawable.nina_07),
    NINA08(Girl.NINA, R.drawable.nina_08),
    NINA09(Girl.NINA, R.drawable.nina_09),
    NINA10(Girl.NINA, R.drawable.nina_10),
    NINA11(Girl.NINA, R.drawable.nina_11),
    NINA12(Girl.NINA, R.drawable.nina_12),
    NINA13(Girl.NINA, R.drawable.nina_13),
    NINA14(Girl.NINA, R.drawable.nina_14),
    NINA15(Girl.NINA, R.drawable.nina_15),
    NINA16(Girl.NINA, R.drawable.nina_16),
    NINA17(Girl.NINA, R.drawable.nina_17),
    NINA18(Girl.NINA, R.drawable.nina_18),
    NINA19(Girl.NINA, R.drawable.nina_19),
    NINA20(Girl.NINA, R.drawable.nina_20),

//    RACHEL00(Girl.RACHEL, R.drawable.rachel_00),
    RACHEL01(Girl.RACHEL, R.drawable.rachel_01),
    RACHEL02(Girl.RACHEL, R.drawable.rachel_02),
    RACHEL03(Girl.RACHEL, R.drawable.rachel_03),
    RACHEL04(Girl.RACHEL, R.drawable.rachel_04),
    RACHEL05(Girl.RACHEL, R.drawable.rachel_05),
    RACHEL06(Girl.RACHEL, R.drawable.rachel_06),
    RACHEL07(Girl.RACHEL, R.drawable.rachel_07),
    RACHEL08(Girl.RACHEL, R.drawable.rachel_08),
    RACHEL09(Girl.RACHEL, R.drawable.rachel_09),
    RACHEL10(Girl.RACHEL, R.drawable.rachel_10),
    RACHEL11(Girl.RACHEL, R.drawable.rachel_11),
    RACHEL12(Girl.RACHEL, R.drawable.rachel_12),
    RACHEL13(Girl.RACHEL, R.drawable.rachel_13),
    RACHEL14(Girl.RACHEL, R.drawable.rachel_14),
    RACHEL15(Girl.RACHEL, R.drawable.rachel_15),
    RACHEL16(Girl.RACHEL, R.drawable.rachel_16),
    RACHEL17(Girl.RACHEL, R.drawable.rachel_17),
    RACHEL18(Girl.RACHEL, R.drawable.rachel_18),
    RACHEL19(Girl.RACHEL, R.drawable.rachel_19),
    RACHEL20(Girl.RACHEL, R.drawable.rachel_20),
    RACHEL21(Girl.RACHEL, R.drawable.rachel_21),
    RACHEL22(Girl.RACHEL, R.drawable.rachel_22),
    RACHEL23(Girl.RACHEL, R.drawable.rachel_23),
    RACHEL24(Girl.RACHEL, R.drawable.rachel_24),
    RACHEL25(Girl.RACHEL, R.drawable.rachel_25),
    RACHEL26(Girl.RACHEL, R.drawable.rachel_26);

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
                ++size;
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
