package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.DrawerPresenter;
import com.wolfie.kidspend2.presenter.DrawerPresenter.DrawerUi;

public class DrawerFragment extends BaseFragment implements DrawerUi {

    private DrawerPresenter mDrawerPresenter;

    @Nullable
    @Override
    public DrawerPresenter getPresenter() {
        return mDrawerPresenter;
    }

    public DrawerFragment() {
        mDrawerPresenter = new DrawerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean isDrawerOpen() {
        return getKidspendActivity().isDrawerOpen();
    }

    @Override
    public void closeDrawer() {
        getKidspendActivity().closeDrawer();
    }

    @Override
    public void openDrawer() {
        getKidspendActivity().openDrawer();
    }

}
