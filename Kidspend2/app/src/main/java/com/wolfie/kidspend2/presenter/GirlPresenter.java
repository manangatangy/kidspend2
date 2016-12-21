package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
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
import com.wolfie.kidspend2.view.fragment.EditFragment;

import java.util.List;

public class GirlPresenter extends BasePresenter<GirlUi>
        implements AsyncListeningTask.Listener<DataSet>{

    // Each GirlPresenter will save/restore its imageIndex only and the actual
    // Girl (for this instance) is always retrieved from the GirlFragment args.
    public static final String KEY_GIRL_IMAGE_INDEX = "KEY_GIRL_IMAGE_INDEX";
    private final static String KEY_LIST_SPENDTYPE = "KEY_LIST_SPENDTYPE";

    // If non-null, then only show spends from this group.
    @Nullable
    private String mSpendType;

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
    }

    public void bumpImage() {
        Girl girl = getUi().getGirl();
        if (++mImageIndex >= girl.getImageCount()) {
            mImageIndex = 0;
        }
        getUi().setPageImage(girl.getImageResourceId(mImageIndex));
        getUi().updateIcon(girl, girl.getImageShade(mImageIndex));
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putInt(KEY_GIRL_IMAGE_INDEX, mImageIndex);
        outState.putString(KEY_LIST_SPENDTYPE, mSpendType);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        if (savedState != null) {
            mImageIndex = savedState.getInt(KEY_GIRL_IMAGE_INDEX);
            mSpendType = savedState.getString(KEY_LIST_SPENDTYPE);
        }
    }

    @Override
    public boolean backPressed() {
        return true;        // Means: not consumed here.
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
            SpendGroup spendGroup = SpendGroup.buildGroup(mSpendType, mDataSet);
            getUi().showEntries(spendGroup);
        }
    }

    public void onListItemClick(Spend selectedSpend) {
        EditPresenter editPresenter = getUi().findPresenter(EditFragment.class);
        if (editPresenter != null) {
            if (selectedSpend != null) {
                editPresenter.editSpend(selectedSpend, getUi().getGirl());
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

    public List<String> getHeadings() {
        return mHeadings;
    }

    public void onClickAdd() {
        onListItemClick(null);
    }

    public interface GirlUi extends BaseUi {
        Girl getGirl();
        void setPageImage(@DrawableRes int resourceId);
        void updateIcon(Girl girl, @ImageShade int imageShade);
        void showEntries(SpendGroup spendGroup);
        void setAddEntryVisibility(boolean visible);
    }

}
