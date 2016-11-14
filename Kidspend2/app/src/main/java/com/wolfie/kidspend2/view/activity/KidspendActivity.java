package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.MainPresenter;
import com.wolfie.kidspend2.view.fragment.DrawerFragment;

import butterknife.BindView;

public class KidspendActivity extends SimpleActivity {

    @BindView(R.id.layout_activity_drawer)
    public DrawerLayout mDrawer;

    private MainPresenter mMainPresenter;

    @Override
    public MainPresenter getPresenter() {
        return mMainPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainPresenter = new MainPresenter(null, getApplicationContext());

//        // Set the initial values for some settings.  May be changed later by SettingsPresenter
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        int sessionTimeout = prefs.getInt(SettingsPresenter.PREF_SESSION_TIMEOUT, TimeoutMonitor.DEFAULT_TIMEOUT);
//        mMainPresenter.setTimeout(sessionTimeout, false);       // No need to start; we are not yet logged in
//        int enumIndex = prefs.getInt(SettingsPresenter.PREF_SESSION_BACKGROUND_IMAGE, SimpleActivity.DEFAULT_BACKGROUND_IMAGE);
//        setBackgroundImage(enumIndex);
//
//        // Create the main content fragment into it's container.
//        setupFragment(ListFragment.class.getName(), R.id.fragment_container_activity_simple, null);
//
        // Create the drawer fragment into it's container.
        setupFragment(DrawerFragment.class.getName(), R.id.fragment_container_activity_drawer, null);

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
        // Specify the layout to use for the KidspendActivity.  This layout include
        // the activity_simple layout, which contains the toolbar and the
        // fragment_container_activity_simple container (for ListFragment) as
        // well as fragment_container_activity_drawer for the DrawerFragment
        return R.layout.activity_drawer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;       // No app bar menu.
    }

}
