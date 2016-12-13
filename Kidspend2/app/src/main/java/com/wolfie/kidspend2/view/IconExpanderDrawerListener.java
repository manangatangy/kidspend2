package com.wolfie.kidspend2.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.widget.FrameLayout;

import com.wolfie.kidspend2.R;

public class IconExpanderDrawerListener implements DrawerListener {

    private FrameLayout mDrawerContainer;
    private TwirlyView mTwirlyView;

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

    public IconExpanderDrawerListener(Context context, FrameLayout drawerContainer, TwirlyView twirlyView) {
        mDrawerContainer = drawerContainer;
        mTwirlyView = twirlyView;
        mOpenDrawerWidth = (float)context.getResources().getDimensionPixelSize(R.dimen.drawer_width_open);
        mMinSpacerHeight = context.getResources().getDimensionPixelSize(R.dimen.icon_height_closed);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (mDrawerContainer == null) {
            return;
        }
        // 0 (closed) to 1 (open)
        int visibleDrawerWidth = (int)(slideOffset * mOpenDrawerWidth);
        // The drawerFrame top padding is minimum of icon_height_closed.  Once the drawer
        // width exceeds that value, then the padding increases in height so that
        // it is square.
        int topPadding = (visibleDrawerWidth < mMinSpacerHeight) ? mMinSpacerHeight : visibleDrawerWidth;
        mDrawerContainer.setPadding(0, topPadding, 0, 0);

        mTwirlyView.setIconImageViewLevel(3333 + (float)(6666) * slideOffset);
        mTwirlyView.setBackgroundVisibility(slideOffset);
    }

    //         mIconImageView.setImageDrawable(getResources().getDrawable(R.drawable.alogo_claire_scaled, null));

}
