package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter;
import com.wolfie.kidspend2.presenter.MainPresenter;
import com.wolfie.kidspend2.view.IconAnimator;
import com.wolfie.kidspend2.view.TwirlingImage;
import com.wolfie.kidspend2.view.fragment.DrawerFragment;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

import butterknife.BindView;

public class KidspendActivity extends SimpleActivity {

    @BindView(R.id.activity_root_layout)
    View mActivityRootView;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.drawer_container)
    public FrameLayout mDrawerContainer;           // Second child of mDrawerLayout holds nav-menu

    @BindView(R.id.twirling_image)
    public TwirlingImage mTwirlingImage;

    private MainPresenter mMainPresenter;

    @Override
    public MainPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mDrawerLayout.setDrawerListener(new IconAnimator(getApplicationContext(),
                                                         mDrawerContainer, mTwirlingImage));

        // Create the main content fragment into it's container.
        setupFragment(GirlPagerFragment.class.getName(), R.id.content_container, null);

        // Create the drawer fragment into it's container.
        setupFragment(DrawerFragment.class.getName(), R.id.drawer_container, null);

//        mMainPresenter = new MainPresenter(null, getApplicationContext());

//        // Set the initial values for some settings.  May be changed later by SettingsPresenter
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        int sessionTimeout = prefs.getInt(SettingsPresenter.PREF_SESSION_TIMEOUT, TimeoutMonitor.DEFAULT_TIMEOUT);
//        mMainPresenter.setTimeout(sessionTimeout, false);       // No need to start; we are not yet logged in
//        int enumIndex = prefs.getInt(SettingsPresenter.PREF_SESSION_BACKGROUND_IMAGE, SimpleActivity.DEFAULT_BACKGROUND_IMAGE);
//        setBackgroundImage(enumIndex);
//

//        // Create the settings (activity sheet) fragment into it's container.
//        setupFragment(SettingsFragment.class.getName(), R.id.fragment_container_settings, null);

    }

//    public void onGirlChanged(Girl newGirl) {
//        // Show the specified girl's image
////        mTwirlingImage.setIconImageDrawable(R.drawable.alogo_claire_scaled);
//    }
//
//    public void onBackgroundChanged(@ImageShade int imageShade) {
//        // Show the version of the girl image that best shows against the specified image shade.
////        mTwirlingImage.setIconImageColour(R.drawable.alogo_claire_scaled);
//    }

//    public void onGirlChanged(Girl newGirl) {
//        // Show the specified girl's image
//        mTwirlingImage.setIconImageDrawable(R.drawable.alogo_claire_scaled);
//    }

    public void updateIcon(Girl girl, @ImageShade int imageShade) {
        mTwirlingImage.updateIcon(girl, imageShade);
    }

    @Override
    @LayoutRes
    public int getLayoutResource() {
        return R.layout.activity_kidspend;
    }

    @Override
    public View getActivityRootView() {
        return mActivityRootView;
    }

    public void openDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(mDrawerContainer);
        }
    }

    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mDrawerContainer);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mDrawerContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;       // No app bar menu.
    }

}
