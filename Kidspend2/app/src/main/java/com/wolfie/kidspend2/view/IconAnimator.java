package com.wolfie.kidspend2.view;

import android.content.Context;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;
import android.widget.FrameLayout;

import com.wolfie.kidspend2.R;

/**
 * Animates the {@link TwirlingImage} in response to the Drawer change
 */
public class IconAnimator implements DrawerListener {

    private FrameLayout mDrawerContainer;
    private TwirlingImage mTwirlingImage;

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

    public IconAnimator(Context context, FrameLayout drawerContainer, TwirlingImage twirlingImage) {
        mDrawerContainer = drawerContainer;
        mTwirlingImage = twirlingImage;
        mOpenDrawerWidth = (float)context.getResources().getDimensionPixelSize(R.dimen.drawer_width_open);
        mMinSpacerHeight = context.getResources().getDimensionPixelSize(R.dimen.icon_height_closed);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        if (mDrawerContainer == null) {
            return;
        }
        // 0 (closed) to 1 (open)
        // Max size = drawer_width_open  (300dp)
        // Min size = icon_height_closed  (100dp)
        // Min rot = 360
        // Min rot = 0
        // 0%      33%     100%
        // 0dp     100dp   300dp
        // ??      0(o)    360(o)
        // ?? ==> -180

        int visibleDrawerWidth = (int)(slideOffset * mOpenDrawerWidth);
        // The drawerFrame top padding is minimum of icon_height_closed.  Once the drawer
        // width exceeds that value, then the padding increases in height so that
        // it is square.
        int topPadding = (visibleDrawerWidth < mMinSpacerHeight) ? mMinSpacerHeight : visibleDrawerWidth;
        mDrawerContainer.setPadding(0, topPadding, 0, 0);

        mTwirlingImage.setIconImageViewLevel(3333 + (float)(6666) * slideOffset);
        mTwirlingImage.setBackgroundVisibility(slideOffset);
    }

}
