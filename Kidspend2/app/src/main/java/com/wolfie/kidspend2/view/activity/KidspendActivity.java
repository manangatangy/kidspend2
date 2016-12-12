package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.MainPresenter;
import com.wolfie.kidspend2.view.IconExpanderDrawerListener;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

import butterknife.BindView;

public class KidspendActivity extends SimpleActivity {

    @BindView(R.id.activity_root_layout)
    View mActivityRootView;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;

    @BindView(R.id.drawer_layout_child_1)
    public RelativeLayout mMainContentContainer;    // First child holds main content, icon

    @BindView(R.id.drawer_layout_child_2)
    public LinearLayout mDrawerContainer;           // Second child of mDrawer holds nav-menu

//    @BindView(R.id.icon_frame)
//    public FrameLayout mIconFrame;

    @BindView(R.id.icon_image_view)
    public ImageView mIconImageView;

    @BindView(R.id.icon_background_view)
    public View mIconBackgroundView;

    @BindView(R.id.drawer_frame)
    public FrameLayout mDrawerFrame;


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

        // Create the main content fragment into it's container.
        setupFragment(GirlPagerFragment.class.getName(), R.id.content_container, null);

        IconExpanderDrawerListener drawerListener
                = new IconExpanderDrawerListener(getApplicationContext(), mDrawerFrame, mIconImageView, mIconBackgroundView);
        mDrawer.setDrawerListener(drawerListener);


        /*
        Sooo, this version has a hidden all the time, action/tool bar.
        There is no action bar icon, so there's nothing to click to open/close.
        The drawer can be dragged, which dims the main content.
        Furthermore, the drawer has gap above it of "?attr/actionBarSize"
        (through which the main content can be seen) where the action bar
        would normally appear.  This was specified in FrameLayout:id/fragment_drawer
        as android:layout_marginTop="?android:attr/actionBarSize"
         */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            actionBar.hide();
        }

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

//        Drawable drawable = getResources().getDrawable(R.drawable.logo_white_nina2, null);
//        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.5),
//                (int)(drawable.getIntrinsicHeight()*0.5));
//        ScaleDrawable scaleDrawable = new ScaleDrawable(drawable, 0, 1.0f, 1.0f);
//        scaleDrawable.setLevel(5000);
//        mIconImageView.setImageDrawable(scaleDrawable.getDrawable());


        /*
        Max size = drawer_width_open  (300dp)
        Min size = icon_height_closed  (100dp)
        Min rot = 360
        Min rot = 0
        0%      33%     100%
        0dp     100dp   300dp
        ??      0(o)    360(o)
        -180
         */
        mIconImageView.setImageDrawable(getResources().getDrawable(R.drawable.alogo_claire_scaled, null));
//        mIconImageView.setImageLevel(3333);
//        mIconImageView.setImageLevel(6666);


//        mIconImageView.setImageLevel(10000);
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
            mDrawer.openDrawer(mDrawerContainer);
        }
    }

    public void closeDrawer() {
        if (mDrawer != null) {
            mDrawer.closeDrawer(mDrawerContainer);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawer != null && mDrawer.isDrawerOpen(mDrawerContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;       // No app bar menu.
    }

}
