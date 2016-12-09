package com.wolfie.kidspend2.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.view.activity.BaseActivity;

public class NavDrawerListener implements DrawerListener {

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    private ActionBar mActionBar;

    private int mToolbarOpenColor;
    private int mToolbarAlpha;
    private int mToolbarRed;
    private int mToolbarGreen;
    private int mToolbarBlue;

    private ActionBarDrawerToggle mToggle;

    public NavDrawerListener(Activity activity, ActionBarDrawerToggle toggle) {
        if (activity != null) {
            setUpColor(activity.getApplicationContext());

            if (activity instanceof BaseActivity) {
                mActionBar = ((BaseActivity) activity).getSupportActionBar();
            } else {
                mActionBar = null;
            }
        }
        mToggle = toggle;
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        mToggle.onDrawerSlide(drawerView, slideOffset);
        tintActionBarOnDrawerOpen(slideOffset); // 0 to 1
    }

    private void tintActionBarOnDrawerOpen(float scale) {
        if (mActionBar != null) {
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//            if (scale == 0) { // Fully closed
//                mActionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            } else if (scale == 1) { // Full open
//                mActionBar.setBackgroundDrawable(new ColorDrawable(mToolbarOpenColor));
//            } else { // Opening
//                int scaledAlpha = (int) (((float) mToolbarAlpha) * scale);
//                mActionBar.setBackgroundDrawable(new ColorDrawable(
//                        Color.argb(scaledAlpha, mToolbarRed, mToolbarGreen, mToolbarBlue)));
//            }
        }
    }

    private void setUpColor(Context context) {
        mToolbarOpenColor = ContextCompat.getColor(context, R.color.nav_draw_bg);
        mToolbarAlpha = Color.alpha(mToolbarOpenColor);
        mToolbarRed = Color.red(mToolbarOpenColor);
        mToolbarGreen = Color.green(mToolbarOpenColor);
        mToolbarBlue = Color.blue(mToolbarOpenColor);
    }

}
