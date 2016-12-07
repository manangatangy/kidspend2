package com.wolfie.kidspend2.view.activity;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wolfie.kidspend2.R.id.toolbar;

public abstract class SimpleActivity extends BaseActivity {

    @BindView(toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.fragment_container_main)
    public FrameLayout fragmentContainer;

    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);

        // Set the toolbar
        mToolbar.setTitleTextAppearance(this, R.style.AppTheme_H1); // Set title text to black (since toolbar is white?)
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.white));
        boolean inhibitElevationAdjust = false;
        if (inhibitElevationAdjust) {
            fragmentContainer.setForeground(null);
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                mToolbar.setElevation(0f);
            }
        }
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Set the back-arrow-colour
//        final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(getResources().getColor(R.color.red_on_black), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // Set a different icon for up indicator
        int resId = -1;
        if (resId != -1) {
            getSupportActionBar().setHomeActionContentDescription(resId);
        }

        // Create the main content fragment into it's container.
        setupFragment(GirlPagerFragment.class.getName(), R.id.fragment_container_main, null);

    }

    @LayoutRes
    public abstract int getLayoutResource();

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

}
