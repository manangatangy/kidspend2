package com.wolfie.kidspend2.view.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.DrawerPresenter;
import com.wolfie.kidspend2.presenter.DrawerPresenter.DrawerUi;
import com.wolfie.kidspend2.view.activity.KidspendActivity;

import butterknife.OnClick;

public class DrawerFragment extends BaseFragment implements DrawerUi {

    private DrawerPresenter mDrawerPresenter;
    private ActionBarDrawerToggle mToggle;

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
        getEskeyActivity().setSupportActionBar(getEskeyActivity().mToolbar);

        getEskeyActivity().mToolbar.post(new Runnable() {
            @Override
            public void run() {
                int height = getEskeyActivity().mToolbar.getHeight();
                // Read your drawable from somewhere
                Drawable dr = getResources().getDrawable(R.drawable.claire_00, null);
                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
                // Scale it to 50 x 50
                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, height, height, true));
                // Set your new, scaled drawable "d"

                getEskeyActivity().mToolbar.setNavigationIcon(d);
            }
        });
        getEskeyActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // http://www.101apps.co.za/index.php/articles/using-toolbars-in-your-apps.html

//        mToggle = new ActionBarDrawerToggle(
//                getEskeyActivity(),
//                getEskeyActivity().mDrawer,
//                getEskeyActivity().mToolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mToggle.syncState();
    }

    @OnClick(R.id.menu_item_settings)
    void onMenuSettings() {
        mDrawerPresenter.onMenuSettingsClick();
    }
    @OnClick(R.id.menu_item_export)
    void onMenuExport() {
        mDrawerPresenter.onMenuExportClick();
    }
    @OnClick(R.id.menu_item_import)
    void onMenuImport() {
        mDrawerPresenter.onMenuImportClick();
    }
    @OnClick(R.id.menu_item_help)
    void onMenuHelp() {
        mDrawerPresenter.onMenuHelp();
    }
    @OnClick(R.id.menu_item_backup)
    void onMenuBackup() {
        mDrawerPresenter.onMenuBackup();
    }
    @OnClick(R.id.menu_item_restore)
    void onMenuRestore() {
        mDrawerPresenter.onMenuRestore();
    }

    @Override
    public boolean isDrawerOpen() {
//        return getEskeyActivity().mDrawer.isDrawerOpen(Gravity.LEFT);
        return false;
    }

    @Override
    public void closeDrawer() {
        if (isDrawerOpen()) {
//            getEskeyActivity().mDrawer.closeDrawer(Gravity.LEFT);
        }
    }

    @Override
    public void openDrawer() {
        if (!isDrawerOpen()) {
//            getEskeyActivity().mDrawer.openDrawer(Gravity.LEFT);
        }
    }

    private KidspendActivity getEskeyActivity() {
        return (KidspendActivity) mBaseActivity;
    }

}
