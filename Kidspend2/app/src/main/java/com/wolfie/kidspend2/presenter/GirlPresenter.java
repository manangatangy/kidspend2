package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wolfie.kidspend2.model.DataSet;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.model.SpendGroup;
import com.wolfie.kidspend2.model.loader.AsyncListeningTask;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.view.BaseUi;
import com.wolfie.kidspend2.view.fragment.DrawerFragment;
import com.wolfie.kidspend2.view.fragment.EditFragment;

import java.util.List;

public class GirlPresenter extends BasePresenter<GirlUi>
        implements AsyncListeningTask.Listener<DataSet>{

    // Each GirlPresenter will save/restore its imageIndex only and the actual
    // Girl (for this instance) is always retrieved from the GirlFragment args.
    public static final String KEY_GIRL_IMAGE_INDEX = "KEY_GIRL_IMAGE_INDEX";
    private final static String KEY_LIST_SPENDTYPE = "KEY_LIST_SPENDTYPE";
    private final static String KEY_LIST_SHOW_ALL = "KEY_LIST_SHOW_ALL";

    // If non-null, then only show spends from this group.
    @Nullable
    private String mSpendType;
    private boolean mShowAll;      // False means "show summary". True means "show all ordered by date"

    // These values are not saved, but refreshed upon resume.
    // Note that mHeadings and mGroupName are taken from here by
    // DrawerPresenter.resume() to reload the nav menu
    private DataSet mDataSet;
    private List<String> mHeadings;

    private int mImageIndex = 0;    // Index into the Girl.mImageIds.

    public GirlPresenter(GirlUi girlUi) {
        super(girlUi);
    }

    @Override
    public void resume() {
        super.resume();
        Girl girl = getUi().getGirl();
        if (girl == null) {
            Log.d("Kidspend", "GirlPresenter.resume(): mGirl is null ! uh-oh");
        }

        MainPresenter mainPresenter = getUi().findPresenter(null);
        boolean listVisibility = mainPresenter.areListsVisible();
        getUi().setListVisibility(listVisibility);

        getUi().setPageImage(girl.getImageResourceId(mImageIndex));
        // Note that resume may be called although this instance is not currently showing
        // so we should not call updateIcon here; use the onShowing protocol instead.
        loadEntries();
    }

    public void onShowing() {
        // This instance is now currently showing.
        // Update the icon using the current image's shade.
        Girl girl = getUi().getGirl();
        getUi().updateIcon(girl, girl.getImageShade(mImageIndex));

        MainPresenter mainPresenter = getUi().findPresenter(null);
        boolean listVisibility = mainPresenter.areListsVisible();
        getUi().setListVisibility(listVisibility);
    }

    public void onToggleListVisibility() {
        MainPresenter mainPresenter = getUi().findPresenter(null);
        mainPresenter.toggleListsVisibility();
        boolean listVisibility = mainPresenter.areListsVisible();
        getUi().setListVisibility(listVisibility);
    }

    public void bumpImage() {
        Girl girl = getUi().getGirl();
        if (++mImageIndex >= girl.getImageCount()) {
            mImageIndex = 0;
        }
        getUi().setPageImage(girl.getImageResourceId(mImageIndex));
        getUi().updateIcon(girl, girl.getImageShade(mImageIndex));
        getUi().hidePullToRefreshSpinner();
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putInt(KEY_GIRL_IMAGE_INDEX, mImageIndex);
        outState.putString(KEY_LIST_SPENDTYPE, mSpendType);
        outState.putBoolean(KEY_LIST_SHOW_ALL, mShowAll);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            mImageIndex = savedState.getInt(KEY_GIRL_IMAGE_INDEX);
            mSpendType = savedState.getString(KEY_LIST_SPENDTYPE);
            mShowAll = savedState.getBoolean(KEY_LIST_SHOW_ALL);
        }
    }

    @Override
    public boolean backPressed() {
        if (mSpendType == null) {
            return true;        // Means: not consumed here.
        } else {
            // Currently viewing spends for a specified spendType, go back to summary list
            selectNavMenuSpendType(null);
            return false;       // Handled here.
        }
    }

    public void loadEntries() {
        MainPresenter mainPresenter = getUi().findPresenter(null);
        if (mainPresenter != null) {
            mainPresenter.getSpendLoader().read(this, getUi().getGirl());
        }
        getUi().setAddEntryVisibility(true);
//        getUi().hideNoFilteredEntriesWarning();
    }

    /**
     * Set the DataSet.  Use the existing spendType and display the (possibly
     * filtered) list on the ui. Then build a new list of group headings
     * which are passed to the DrawerPresenter.
     */
    @Override
    public void onCompletion(DataSet dataSet) {
        mDataSet = dataSet;
        mHeadings = SpendGroup.buildHeadingsList(dataSet);
        setSpendType(mSpendType);
    }

    /**
     * Set the new spendType name for filtering, use it with the existing
     * (already loaded) DataSet to build a list of structured spends,
     * and pass to the ui for display. Called by the DrawerPresenter.
     * SpendType will be null to indicate summary info.
     */
    public void setSpendType(@Nullable String spendType) {
        mSpendType = spendType;
        if (mDataSet != null) {
            if (mShowAll) {
                SpendGroup spendGroup = SpendGroup.makeTotalGroup(mDataSet.getDateSortedSpends());
                getUi().showEntries(spendGroup, true);
            } else {
                SpendGroup spendGroup = SpendGroup.buildGroup(mSpendType, mDataSet.getSpends());
                // It is possible that (for a specified spendType) there will be no spends.
                // This happens if the last spend in a spendType is deleted (and that spendType
                // was selected from the nav menu).  In this case, reset the selected spendType
                // to all (ie null).
                if (mSpendType != null && spendGroup == null) {
                    setSpendType(null);
                } else {
                    getUi().showEntries(spendGroup, false);
                }
            }
        }
    }

    public void onListItemClick(@NonNull Spend selectedSpend) {
        if (!selectedSpend.isNew()) {
            editSpend(selectedSpend);
        } else {
            // This spend represents a sub-total; it's not an actual db spend record.
            selectNavMenuSpendType(selectedSpend.getSpendType());
        }
    }

    private void selectNavMenuSpendType(String spendType) {
        DrawerPresenter drawerPresenter = getUi().findPresenter(DrawerFragment.class);
        // This call will cause callback to setSpendType()
        drawerPresenter.onItemSelected(spendType, true);
    }

    private void editSpend(Spend spend) {
        EditPresenter editPresenter = getUi().findPresenter(EditFragment.class);
        if (editPresenter != null) {
            if (spend != null) {
                editPresenter.editSpend(spend, getUi().getGirl());
            } else {
                // Set up a new Entry for the currently selected spendType.
                editPresenter.editNewEntry(mSpendType, getUi().getGirl());
            }
        }
    }

    @Nullable
    public String getSpendType() {
        return mSpendType;
    }

    // False means "show summary". True means "show all ordered by date"
    public boolean isShowAll() {
        return mShowAll;
    }

    public void setShowAll(boolean showAll) {
        mShowAll = showAll;
    }

    public List<String> getHeadings() {

        // TODO: should be null or empty for when isShowAll is set
        return mHeadings;
    }

    public void onClickAdd() {
        editSpend(null);
    }

    public interface GirlUi extends BaseUi {
        void hidePullToRefreshSpinner();
        Girl getGirl();
        void setPageImage(@DrawableRes int resourceId);
        void updateIcon(Girl girl, @ImageShade int imageShade);
        void showEntries(SpendGroup spendGroup, boolean showDateAndType);
        void setListVisibility(boolean visible);
        void setAddEntryVisibility(boolean visible);
    }

}
