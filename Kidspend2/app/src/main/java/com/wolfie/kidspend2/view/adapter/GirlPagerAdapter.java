package com.wolfie.kidspend2.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.view.fragment.GirlFragment;

/**
 * Ref: https://www.bignerdranch.com/blog/viewpager-without-fragments/
 */
public class GirlPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public GirlPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    /**
     * @param position is the index into Girl.values, bounded by {@link #getCount()}
     * @return the new or existing {@link GirlFragment} instance for the specified
     * {@link Girl}
     */
    @Override
    public Fragment getItem(int position) {
        Girl girl = Girl.values()[position];
        Bundle args = new Bundle();
        args.putString(GirlFragment.KEY_GIRL_NAME, girl.name());
        Fragment fragment = Fragment.instantiate(mContext, GirlFragment.class.getName(), args);
        return fragment;
    }

    @Override
    public int getCount() {
        return Girl.values().length;
    }
}
