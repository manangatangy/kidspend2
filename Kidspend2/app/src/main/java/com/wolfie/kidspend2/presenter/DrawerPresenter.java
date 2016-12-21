package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.presenter.DrawerPresenter.DrawerUi;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.view.fragment.FileFragment;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

import java.util.List;

public class DrawerPresenter extends BasePresenter<DrawerUi> {

    private final static String KEY_DRAWER_SHOWING = "KEY_DRAWER_SHOWING";
    private boolean mIsOpen;

    public DrawerPresenter(DrawerUi drawerUi) {
        super(drawerUi);
    }

    @Override
    public void resume() {
        super.resume();
        if (!mIsOpen) {
            getUi().closeDrawer();
        } else {
            getUi().openDrawer();
        }
    }

    @Override
    public void pause() {
        super.pause();
        mIsOpen = getUi().isDrawerOpen();
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putBoolean(KEY_DRAWER_SHOWING, mIsOpen);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        mIsOpen = savedState.getBoolean(KEY_DRAWER_SHOWING, false);
    }

    @Override
    public boolean backPressed() {
        if (!getUi().isDrawerOpen()) {
            return true;        // Means: not consumed here
        }
        getUi().closeDrawer();
        return false;
    }

    public void onDrawerOpened() {
        // Fetch the headings and selected from current GirlPresenter and set to the view
        GirlPresenter girlPresenter = getCurrentGirlPresenter();
        if (girlPresenter != null) {
            List<String> headings = girlPresenter.getHeadings();
            String selected = girlPresenter.getSpendType();
            getUi().refreshListWithHeadings(headings);
            getUi().selectListItem(selected);
        }
    }

    public void onItemSelected(String spendType, boolean hasChanged) {
        getUi().closeDrawer();
        if (hasChanged) {
            // Inform GirlPresenter of a new filtered spendType value
            GirlPresenter girlPresenter = getCurrentGirlPresenter();
            if (girlPresenter != null) {
                girlPresenter.setSpendType(spendType);
            }
        }
    }

    private GirlPresenter getCurrentGirlPresenter() {
        GirlPagerPresenter girlPagerPresenter = getUi().findPresenter(GirlPagerFragment.class);
        return (girlPagerPresenter == null) ? null : girlPagerPresenter.getCurrentGirlPresenter();
    }

    public void onMenuSettingsClick() {
//        getUi().closeDrawer();
//        SettingsPresenter settingsPresenter = getUi().findPresenter(SettingsFragment.class);
//        settingsPresenter.show();
    }
    public void onMenuExportClick() {
        getUi().closeDrawer();
        FilePresenter filePresenter = getUi().findPresenter(FileFragment.class);
        filePresenter.exporting();
    }
    public void onMenuImportClick() {
        getUi().closeDrawer();
        FilePresenter filePresenter = getUi().findPresenter(FileFragment.class);
        filePresenter.importing();
    }
    public void onMenuHelp() {
//        getUi().closeDrawer();
//        HelpPresenter helpPresenter = getUi().findPresenter(HelpFragment.class);
//        helpPresenter.show();
    }
    public void onMenuBackup() {
//        getUi().closeDrawer();
//        FilePresenter filePresenter = getUi().findPresenter(FileFragment.class);
//        filePresenter.backup();
    }
    public void onMenuRestore() {
//        getUi().closeDrawer();
//        FilePresenter filePresenter = getUi().findPresenter(FileFragment.class);
//        filePresenter.restore();
    }

    public interface DrawerUi extends BaseUi {

        void refreshListWithHeadings(List<String> headings);
        void selectListItem(@Nullable String selected);
        void onNavMenuItemClick(String spendType, boolean hasChanged);
        boolean isDrawerOpen();
        void closeDrawer();
        void openDrawer();
    }


}
