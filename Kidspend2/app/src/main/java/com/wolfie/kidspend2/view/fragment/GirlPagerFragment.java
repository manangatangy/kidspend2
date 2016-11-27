package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
//        mRecyclerView.setItemScrollListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewPager.setAdapter(new GirlPagerAdapter(getChildFragmentManager(), getContext()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
