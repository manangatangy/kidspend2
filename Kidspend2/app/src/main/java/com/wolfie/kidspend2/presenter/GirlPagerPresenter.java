package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter.GirlPagerUi;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.view.fragment.GirlFragment;

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
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
    }

    public void onGirlChanged(Girl newGirl) {
        // This GirlFragment is being paged into view.
        GirlFragment girlFragment = getUi().getGirlFragment(newGirl);
        girlFragment.onShowing();
    }

    public GirlPresenter getCurrentGirlPresenter() {
        return getUi().getCurrentFragment().getPresenter();
    }

    public interface GirlPagerUi extends BaseUi {
        GirlFragment getGirlFragment(Girl girl);
        Girl getCurrentGirl();
        GirlFragment getCurrentFragment();
    }

}
