package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.DrawerPresenter;
import com.wolfie.kidspend2.presenter.DrawerPresenter.DrawerUi;

public class DrawerFragment extends BaseFragment implements DrawerUi {

    private DrawerPresenter mDrawerPresenter;

    @Nullable
    @Override
    public DrawerPresenter getPresenter() {
        return mDrawerPresenter;
    }

    public DrawerFragment() {
        mDrawerPresenter = new DrawerPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getEskeyActivity().setSupportActionBar(getEskeyActivity().mToolbar);
//
//        getEskeyActivity().mToolbar.post(new Runnable() {
//            @Override
//            public void run() {
//                int height = getEskeyActivity().mToolbar.getHeight();
//                // Read your drawable from somewhere
//                Drawable dr = getResources().getDrawable(R.drawable.claire_00, null);
//                Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
//                // Scale it to 50 x 50
//                Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, height, height, true));
//                // Set your new, scaled drawable "d"
//
//                getEskeyActivity().mToolbar.setNavigationIcon(d);
//            }
//        });
//        getEskeyActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        // http://www.101apps.co.za/index.php/articles/using-toolbars-in-your-apps.html

//        mToggle = new ActionBarDrawerToggle(
//                getEskeyActivity(),
//                getEskeyActivity().mDrawerLayout,
//                getEskeyActivity().mToolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mToggle.syncState();
    }

    @Override
    public boolean isDrawerOpen() {
        return getKidspendActivity().isDrawerOpen();
    }

    @Override
    public void closeDrawer() {
        getKidspendActivity().closeDrawer();
    }

    @Override
    public void openDrawer() {
        getKidspendActivity().openDrawer();
    }

}
