package com.wolfie.kidspend2.view.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.wolfie.kidspend2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class SimpleActivity extends BaseActivity {

    protected Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(getLayoutResource());
        unbinder = ButterKnife.bind(this);
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
