package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter.GirlPagerUi;
import com.wolfie.kidspend2.view.fragment.GirlFragment;

public class GirlPagerPresenter extends BasePresenter<GirlPagerUi> {

    private Girl mGirlCurrent = null;

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

    public void pagerSettled(Girl newGirl) {
        // TODO temp inhibit image bumping
        if (true) {
            return;
        }
        // Inform the previous page's fragment
        // to move to the next image (which will be shown when paging back).
        if (mGirlCurrent != null) {
            GirlFragment girlFragment = getUi().getGirlFragment(mGirlCurrent);
            girlFragment.bumpImage();
        }
        mGirlCurrent = newGirl;
    }

    public interface GirlPagerUi extends BaseUi {

        GirlFragment getGirlFragment(Girl girl);
    }

}
