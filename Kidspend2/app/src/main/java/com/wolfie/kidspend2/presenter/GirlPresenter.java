package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;

public class GirlPresenter extends BasePresenter<GirlUi> {

    public GirlPresenter(GirlUi girlUi) {
        super(girlUi);
    }

    @Override
    public void resume() {
        super.resume();
        // TODO load up the girl data
        Girl girl = getUi().getGirl();
        getUi().setLabel(girl.name());
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void onSaveState(Bundle outState) {
//        outState.putBoolean(KEY_DRAWER_SHOWING, mIsOpen);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
//        mIsOpen = savedState.getBoolean(KEY_DRAWER_SHOWING, false);
    }

    public interface GirlUi extends BaseUi {

        Girl getGirl();
        void setLabel(String text);
    }

}
