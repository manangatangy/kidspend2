package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wolfie.kidspend2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class SimpleActivity extends BaseActivity {

    @BindView(R.id.activity_root_layout)
    View mActivityRootView;

    // Needed public by child frags (drawer)
    @Nullable
    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
        // https://guides.codepath.com/android/Using-the-App-ToolBar
        // http://stackoverflow.com/questions/29069070/completely-transparent-status-bar-and-navigation-bar-on-lollipop/31596735#31596735
        // https://medium.com/google-developers/why-would-i-want-to-fitssystemwindows-4e26d9ce1eec
    }

    @LayoutRes
    public abstract int getLayoutResource();

    @Override
    public View getActivityRootView() {
        return mActivityRootView;
    }

    /**
     * Create the named fragment and add its view to the specified container.
     * If not null, the specified Bundle will be given to the fragment as args.
     */
    protected Fragment setupFragment(String fragClassName, @IdRes int containerViewId,
                                     @Nullable Bundle args) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragClassName);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragClassName, args);
            getSupportFragmentManager().beginTransaction().add(containerViewId, fragment, fragClassName).commit();
        }
        return fragment;
    }

    /**
     * Remove the specified fragment from the activity state and its container.
     */
    protected void teardownFragment(String fragClassName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragClassName);
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }

//    @Override
//    public void showToolbar(boolean show) {
//        mToolbar.setVisibility(show ? View.VISIBLE : View.GONE);
//    }
//
//    protected void setupToolbar(boolean inhibitElevationAdjust) {
//        mToolbar.setTitleTextAppearance(this, R.style.AppTheme_Text_H1);
//        mToolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.black));
//        if (inhibitElevationAdjust) {
//            fragmentContainer.setForeground(null);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mToolbar.setElevation(0f);
//            }
//        }
//        setSupportActionBar(mToolbar);
//    }

//    protected void setupTitle(String title) {
//        getSupportActionBar().setTitle(title);
//    }
//
//    public void setTitle(int resId) {
//        getSupportActionBar().setTitle(resId);
//    }
//
//    protected void setupHomeUp(boolean homeAsUp) {
//        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
//    }

//    private void setupBackArrowColour() {
//        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.red_on_black), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);
//    }

//    protected void setupUpIndicator(int resId) {
//        getSupportActionBar().setHomeAsUpIndicator(resId);
//    }

}
