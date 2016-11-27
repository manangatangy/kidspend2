package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;

public class GirlPresenter extends BasePresenter<GirlUi> {

    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";

    private Girl mGirl;
    private int mImageIndex;    // Index into the Girl.mImageIds.

    public GirlPresenter(GirlUi girlUi) {
        super(girlUi);
    }

    @Override
    public void resume() {
        super.resume();
        // TODO load up the girl data
        if (mGirl == null) {
            mGirl = getUi().getGirl();
        }
        getUi().setLabel(mGirl.name());
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putString(KEY_GIRL_NAME, mGirl.name());
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            mGirl = Girl.valueOf(savedState.getString(KEY_GIRL_NAME));
        }
    }

    public void onClickAdd() {

    }

    public interface GirlUi extends BaseUi {

        Girl getGirl();
        void setLabel(String text);

    }

}
