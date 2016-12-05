package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.MainPresenter;
import com.wolfie.kidspend2.view.fragment.DrawerFragment;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

import butterknife.BindView;

public class KidspendActivity extends SimpleActivity {

//    @BindView(R.id.layout_activity_drawer)
//    public DrawerLayout mDrawer;

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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mMainPresenter = new MainPresenter(null, getApplicationContext());

//        // Set the initial values for some settings.  May be changed later by SettingsPresenter
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        int sessionTimeout = prefs.getInt(SettingsPresenter.PREF_SESSION_TIMEOUT, TimeoutMonitor.DEFAULT_TIMEOUT);
//        mMainPresenter.setTimeout(sessionTimeout, false);       // No need to start; we are not yet logged in
//        int enumIndex = prefs.getInt(SettingsPresenter.PREF_SESSION_BACKGROUND_IMAGE, SimpleActivity.DEFAULT_BACKGROUND_IMAGE);
//        setBackgroundImage(enumIndex);
//
        // Create the main content fragment into it's container.
        setupFragment(GirlPagerFragment.class.getName(), R.id.fragment_container_main, null);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;       // No app bar menu.
    }

}
