package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter.GirlPagerUi;

public class GirlPagerPresenter extends BasePresenter<GirlPagerUi> {

    public GirlPagerPresenter(GirlPagerUi girlPagerUi) {
        super(girlPagerUi);
    }

    @Override
    public void resume() {
        super.resume();
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

    public interface GirlPagerUi extends BaseUi {

    }

}
