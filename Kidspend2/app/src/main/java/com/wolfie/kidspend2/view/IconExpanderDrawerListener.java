package com.wolfie.kidspend2.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wolfie.kidspend2.R;

public class IconExpanderDrawerListener implements DrawerListener {

    private FrameLayout mDrawerFrame;
    private ImageView mIconImageView;
    private float mOpenDrawerWidth;
    private int mMinSpacerHeight;

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }

    public IconExpanderDrawerListener(Context context, FrameLayout drawerFrame, ImageView iconImageView) {
        mDrawerFrame = drawerFrame;
        mIconImageView = iconImageView;
        mOpenDrawerWidth = (float)context.getResources().getDimensionPixelSize(R.dimen.drawer_width_open);
        mMinSpacerHeight = context.getResources().getDimensionPixelSize(R.dimen.icon_height_closed);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (mDrawerFrame == null) {
            return;
        }
        int visibleDrawerWidth = (int)(slideOffset * mOpenDrawerWidth);
        Log.d("drawer", "visibleDrawerWidth=" + visibleDrawerWidth + ",  slideOffset=" + slideOffset);
        // The drawerFrame top padding is minimum of icon_height_closed.  Once the drawer
        // width exceeds that value, then the padding increases in height so that
        // it is square.
        int topPadding = (visibleDrawerWidth < mMinSpacerHeight) ? mMinSpacerHeight : visibleDrawerWidth;
        Log.d("drawer", "       top-padding=" + (topPadding));
        mDrawerFrame.setPadding(0, topPadding, 0, 0);
//        tintActionBarOnDrawerOpen(slideOffset); // 0 (closed) to 1 (open)
        float level = 3333 + (float)(6666) * slideOffset;
        mIconImageView.setImageLevel((int)level);

    }

}
