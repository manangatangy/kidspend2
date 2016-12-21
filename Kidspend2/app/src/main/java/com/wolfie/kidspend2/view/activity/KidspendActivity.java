package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.presenter.MainPresenter;
import com.wolfie.kidspend2.view.IconAnimator;
import com.wolfie.kidspend2.view.TwirlingImage;
import com.wolfie.kidspend2.view.fragment.DrawerFragment;
import com.wolfie.kidspend2.view.fragment.EditFragment;
import com.wolfie.kidspend2.view.fragment.FileFragment;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

import butterknife.BindView;

import static com.wolfie.kidspend2.view.IconAnimator.SLIDE_OFFSET_CLOSED;
import static com.wolfie.kidspend2.view.IconAnimator.SLIDE_OFFSET_OPEN;

public class KidspendActivity extends SimpleActivity {

    @BindView(R.id.activity_root_layout)
    View mActivityRootView;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.drawer_container)
    public FrameLayout mDrawerContainer;           // Second child of mDrawerLayout holds nav-menu

    @BindView(R.id.twirling_image)
    public TwirlingImage mTwirlingImage;

    private IconAnimator mIconAnimator;
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

        mIconAnimator = new IconAnimator(getApplicationContext(), mDrawerContainer, mTwirlingImage) {
            @Override
            public void onDrawerOpened(View drawerView) {
                // DrawerPresenter needs to reload menu items
                DrawerFragment drawerFragment = KidspendActivity.this.findFragment(DrawerFragment.class);
                drawerFragment.onDrawerOpened();

            }
        };
        mDrawerLayout.setDrawerListener(mIconAnimator);
        mTwirlingImage.setOnClickListener(new OnClickListener() {
            /**
             * The drawer/icon/animator are coupled together and work as follows:
             * 1. When the user drags the drawer, the {@link IconAnimator} listens
             * to onDrawerSlide and animates the icon accordingly.
             * 2. When the program calls {@link KidspendActivity#openDrawer()} (from resume)
             * then the {@link DrawerLayout} is opened and the iconAnimator is told to
             * move the {@link TwirlingImage} to the open state. No animation occurs.
             * 3. When the Icon is clicked, then the DrawerLayout is animated open
             * and it also calls back to the IconAnimator (which is also a
             * {@link android.support.v4.widget.DrawerLayout.DrawerListener}) to
             * animate the TwirlyIcon etc.
             */
            @Override
            public void onClick(View v) {
                if (isDrawerOpen()) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });

        // Create the main content fragment into it's container.
        setupFragment(GirlPagerFragment.class.getName(), R.id.content_container, null);

        // Create the drawer fragment into it's container.
        setupFragment(DrawerFragment.class.getName(), R.id.drawer_container, null);

        // Create the file (activity sheet) fragment into it's container.
        setupFragment(FileFragment.class.getName(), R.id.fragment_container_file, null);

        // Create the edit (activity sheet) fragment into it's container.
        setupFragment(EditFragment.class.getName(), R.id.fragment_container_edit, null);

        mMainPresenter = new MainPresenter(null, getApplicationContext());

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
            mIconAnimator.onDrawerSlide(null, SLIDE_OFFSET_OPEN);
        }
    }

    public void closeDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mDrawerContainer);
            mIconAnimator.onDrawerSlide(null, SLIDE_OFFSET_CLOSED);
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
