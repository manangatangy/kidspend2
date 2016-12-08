package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.View;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.MainPresenter;
import com.wolfie.kidspend2.view.NavDrawerListener;

import butterknife.BindView;

/**
 * This ref: https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer
 * explains how to style the action/tool bar
 * Ref for custom icon: http://stackoverflow.com/a/27116116
 */
public class KidspendActivity extends SimpleActivity {

    @BindView(R.id.activity_root_layout)
    View mActivityRootView;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;

    @BindView(R.id.fragment_drawer)
    public View mFragmentContainer;

//    @BindView(R.id.viewPager)
//    ViewPager mViewPager;

    private MainPresenter mMainPresenter;

    @Override
    public MainPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        setupFragment("NavigationDrawerFragment.class", R.id.fragment_drawer, null);
        final ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawer, null, 0, 0);
//                                          R.string.drawer_open, R.string.drawer_close);

        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        /*
        This correctly sets the icon, and the drawer can be opened by dragging the edge
        however there is no click listener on the icon, and also the drawer listener
        doesn't shade the toolbar. Prob because getSupportActionBar ==> null
         */
//        drawerButton = (BadgeDrawerButton) findViewById(R.id.badge_drawer_button);
//        drawerButton.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        mDrawerLayout.openDrawer(Gravity.LEFT);
//                    }
//                });
        // Defer code dependent on restoration of previous instance state.
        mDrawer.post(new Runnable() {
            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }
        });
        mDrawer.setDrawerListener(new NavDrawerListener(this, actionBarDrawerToggle));


//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }

//        mMainPresenter = new MainPresenter(null, getApplicationContext());

//        // Set the initial values for some settings.  May be changed later by SettingsPresenter
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        int sessionTimeout = prefs.getInt(SettingsPresenter.PREF_SESSION_TIMEOUT, TimeoutMonitor.DEFAULT_TIMEOUT);
//        mMainPresenter.setTimeout(sessionTimeout, false);       // No need to start; we are not yet logged in
//        int enumIndex = prefs.getInt(SettingsPresenter.PREF_SESSION_BACKGROUND_IMAGE, SimpleActivity.DEFAULT_BACKGROUND_IMAGE);
//        setBackgroundImage(enumIndex);
//

//        mViewPager.setAdapter(new GirlPagerAdapter(getSupportFragmentManager(), this));

//
        // Create the drawer fragment into it's container.
//        setupFragment(DrawerFragment.class.getName(), R.id.fragment_container_activity_drawer, null);

//        // Create the entry edit (activity sheet) fragment into it's container.
//        setupFragment(EditFragment.class.getName(), R.id.fragment_container_edit, null);
//
//        // Create the login (activity sheet) fragment into it's container.
//        setupFragment(LoginFragment.class.getName(), R.id.fragment_container_login, null);
//
//        // Create the file (activity sheet) fragment into it's container.
//        setupFragment(FileFragment.class.getName(), R.id.fragment_container_file, null);
//
//        // Create the help (activity sheet) fragment into it's container.
//        setupFragment(HelpFragment.class.getName(), R.id.fragment_container_help, null);
//
//        // Create the settings (activity sheet) fragment into it's container.
//        setupFragment(SettingsFragment.class.getName(), R.id.fragment_container_settings, null);

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


//    // loads the main content fragment
//    public Fragment setupNavigationFragment(Class fragmentClass, int screenTitle) {
//        int fragmentLayoutResourceId = R.id.fragment_container_main;
//        String fragmentClassName = fragmentClass.getName();
//        Fragment fragment = setupFragment(fragmentClassName, fragmentLayoutResourceId, null);
////        setupTitle(getString(screenTitle));
//        return fragment;
//    }

    public void openDrawer() {
        if (mDrawer != null) {
            mDrawer.openDrawer(mFragmentContainer);
        }
    }

    public void closeDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(mFragmentContainer);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawer != null && mDrawer.isDrawerOpen(mFragmentContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;       // No app bar menu.
    }

}
