package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.view.BaseUi;

public class GirlPresenter extends BasePresenter<GirlUi> {

    // Each GirlPresenter will save/restore its imageIndex only and the actual
    // Girl (for this instance) is always retrieved from the GirlFragment args.
    public static final String KEY_GIRL_IMAGE_INDEX = "KEY_GIRL_IMAGE_INDEX";

    private int mImageIndex = 0;    // Index into the Girl.mImageIds.

    public GirlPresenter(GirlUi girlUi) {
        super(girlUi);
    }

    @Override
    public void resume() {
        super.resume();
        Girl girl = getUi().getGirl();
        if (girl == null) {
            Log.d("Kidspend", "GirlPresenter.resume(): mGirl is null ! uh-oh");
        }
        getUi().setLabel(girl.name());
        getUi().setPageImage(girl.getImageResourceId(mImageIndex));
        // Note that resume may be called although this instance is not currently showing
        // so we should not call updateIcon here; use the onShowing protocol instead.
    }

    public void onShowing() {
        // This instance is now currently showing.
        // Update the icon using the current image's shade.
        Girl girl = getUi().getGirl();
        getUi().updateIcon(girl, girl.getImageShade(mImageIndex));
    }

    public void bumpImage() {
        Girl girl = getUi().getGirl();
        if (++mImageIndex >= girl.getImageCount()) {
            mImageIndex = 0;
        }
        getUi().setPageImage(girl.getImageResourceId(mImageIndex));
        getUi().updateIcon(girl, girl.getImageShade(mImageIndex));
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putInt(KEY_GIRL_IMAGE_INDEX, mImageIndex);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            mImageIndex = savedState.getInt(KEY_GIRL_IMAGE_INDEX);
        }
    }

    public void onClickAdd() {
    }

    public interface GirlUi extends BaseUi {
        Girl getGirl();
        void setLabel(String text);
        void setPageImage(@DrawableRes int resourceId);
        void updateIcon(Girl girl, @ImageShade int imageShade);
    }

}
