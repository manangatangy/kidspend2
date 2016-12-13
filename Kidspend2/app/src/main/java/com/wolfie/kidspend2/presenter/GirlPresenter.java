package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.view.BaseUi;

public class GirlPresenter extends BasePresenter<GirlUi> {

    // Each GirlPresenter needs to save/restore its Girl and imageIndex
    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";
    public static final String KEY_GIRL_IMAGE_INDEX = "KEY_GIRL_IMAGE_INDEX";

    private Girl mGirl;
    private int mImageIndex = 0;    // Index into the Girl.mImageIds.

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
        getUi().setPageImage(mGirl.getImageResourceId(mImageIndex));
        // Note that resume may be called although this instance is not currently showing.
    }

    public void onShowing() {
        // This instance is now currently showing.
        // Update the icon using the current image's shade.
        getUi().updateIcon(mGirl, mGirl.getImageShade(mImageIndex));
        // TODO set timed calls to mGirlPresenter.bumpImage();
    }

    public void onHidden() {
        // TODO stop timed calls to bump
    }

    public void bumpImage() {
        if (++mImageIndex >= mGirl.getImageCount()) {
            mImageIndex = 0;
        }
        getUi().setPageImage(mGirl.getImageResourceId(mImageIndex));
        getUi().updateIcon(mGirl, mGirl.getImageShade(mImageIndex));
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

    // TODO - this happened when backgrounding/resume with the drawer open
    // note that the icon doesnt resore to the full size
    /*
     java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String com.wolfie.kidspend2.model.Girl.name()' on a null object reference
                                                                        at com.wolfie.kidspend2.presenter.GirlPresenter.onSaveState(GirlPresenter.java:61)
                                                                        at com.wolfie.kidspend2.view.fragment.BaseFragment.onSaveInstanceState(BaseFragment.java:78)

     */
    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            mGirl = Girl.valueOf(savedState.getString(KEY_GIRL_NAME));
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
