package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter.GirlPagerUi;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.view.fragment.GirlFragment;

public class GirlPagerPresenter extends BasePresenter<GirlPagerUi> {

    // This member isn't saved/restored because the ViewPager will know
    // which is the current GirlFragment, and restores them correctly.
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
//        outState.putString(KEY_GIRL_PAGER_NAME, mGirlCurrent.name());
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
//            mGirlCurrent = Girl.valueOf(savedState.getString(KEY_GIRL_PAGER_NAME));
        }
    }

    public void onGirlChanged(Girl newGirl) {
        // Inform the previous page's fragment it is no longer showing.
        if (mGirlCurrent != null) {
            // This GirlFragment is being paged out of view.
            GirlFragment girlFragment = getUi().getGirlFragment(mGirlCurrent);
            girlFragment.onHidden();
        }
        // This GirlFragment is being paged into view.
        mGirlCurrent = newGirl;
        GirlFragment girlFragment = getUi().getGirlFragment(mGirlCurrent);
        girlFragment.onShowing();
    }

    public interface GirlPagerUi extends BaseUi {
        GirlFragment getGirlFragment(Girl girl);
    }

}
