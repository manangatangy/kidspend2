package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.view.BaseUi;

public class GirlPresenter extends BasePresenter<GirlUi> {

    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";
    public static final String KEY_GIRL_IMAGE_INDEX = "KEY_GIRL_IMAGE_INDEX";

    private Girl mGirl;
    private int mImageIndex;    // Index into the Girl.mImageIds.

    public GirlPresenter(GirlUi girlUi) {
        super(girlUi);
    }

    @Override
    public void resume() {
        super.resume();
        if (mGirl == null) {
            mGirl = getUi().getGirl();      // As passed from GirlPagerAdapter
        }
        getUi().setLabel(mGirl.name());
        setImage();
    }

    private void setImage() {
        getUi().setImage(mGirl.getImageResourceId(mImageIndex),
                         mGirl.getImageShade(mImageIndex));
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putString(KEY_GIRL_NAME, mGirl.name());
        outState.putInt(KEY_GIRL_IMAGE_INDEX, mImageIndex);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            mGirl = Girl.valueOf(savedState.getString(KEY_GIRL_NAME));
            mImageIndex = savedState.getInt(KEY_GIRL_IMAGE_INDEX);
        }
    }

    public void bumpImage() {
        if (++mImageIndex >= mGirl.getImageCount()) {
            mImageIndex = 0;
        }
        setImage();
    }

    public void onClickAdd() {

    }

    public interface GirlUi extends BaseUi {

        Girl getGirl();
        void setLabel(String text);
        void setImage(@DrawableRes int resourceId, @ImageShade int imageShade);

    }

}
