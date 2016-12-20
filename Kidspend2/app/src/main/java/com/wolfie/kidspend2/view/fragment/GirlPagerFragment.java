package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter.GirlPagerUi;
import com.wolfie.kidspend2.view.adapter.GirlPagerAdapter;

import butterknife.BindView;

public class GirlPagerFragment extends BaseFragment implements GirlPagerUi {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private GirlPagerPresenter mGirlPagerPresenter;

    @Override
    public GirlPagerPresenter getPresenter() {
        return mGirlPagerPresenter;
    }

    public GirlPagerFragment() {
        mGirlPagerPresenter = new GirlPagerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_girl_pager, container, false);
    }

    // mSelectedPosition is the value passed from onPageSelected(), and mCurrentSettledPosition
    // is the values most recently passed to GirlPagerPresenter.onGirlChanged().
    private int mSelectedPosition = -1;
    private int mCurrentSettledPosition = -1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
//        mRecyclerView.setItemScrollListener(this);
        mViewPager.setAdapter(new GirlPagerAdapter(getChildFragmentManager(), getContext()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mSelectedPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE && mSelectedPosition != mCurrentSettledPosition) {
                    // We have settled to a position (mSelectedPosition) that is different from
                    // what we last told the GirlPagerPresenter.  Therefore inform the presenter
                    // of a change in Girl.
                    Girl newGirl = Girl.values()[mSelectedPosition];
                    mGirlPagerPresenter.onGirlChanged(newGirl);
                    mCurrentSettledPosition = mSelectedPosition;
                }
            }
        });
    }

    @Override
    public GirlFragment getGirlFragment(Girl girl) {
        // Iterate the child frags and return the GirlFragment associated with the specified Girl.
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            if (fragment instanceof GirlFragment) {
                GirlFragment girlFragment = (GirlFragment)fragment;
                if (girlFragment.getGirl() == girl) {
                    return girlFragment;
                }
            }
        }
        return null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().post(new Runnable() {
            @Override
            public void run() {
                // Initialise the currently showing GirlFragment.
                // Note that the ViewPager will be restored with the correctly saved current
                // GirlFragment, and so no further state need be saved in the GirlPagerPresenter.
                // This has to be delayed until the GirlFragments are attached to the activity
                // so that getGirlFragment() can find them.
                GirlFragment girlFragment = getCurrentFragment();
                girlFragment.onShowing();
            }
        });
    }

    /**
     * We use the ViewPager to hold (and save/restore) the currently showing GirlFragment.
     * This means that the GirlPagerPresenter doesn't have to keep track of this state
     * @return the GirlFragment currently showing in the ViewPager.
     */
    @Override
    public Girl getCurrentGirl() {
        int currentItem = mViewPager.getCurrentItem();
        return Girl.values()[currentItem];
    }

    @Override
    public GirlFragment getCurrentFragment() {
        Girl girl = getCurrentGirl();
        GirlFragment girlFragment = getGirlFragment(girl);
        return girlFragment;
    }
}
