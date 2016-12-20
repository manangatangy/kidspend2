package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.internal.util.Predicate;
import com.wolfie.kidspend2.model.DataSet;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.model.SpendGroup;
import com.wolfie.kidspend2.model.loader.AsyncListeningTask;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.view.BaseUi;

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
     */
    public void setSpendType(@Nullable String spendType) {
        mSpendType = spendType;
        if (mDataSet != null) {
            List<SpendGroup> groups = SpendGroup.buildGroups(mSpendType, mDataSet);
            getUi().showEntries(groups);
        }
    }

    private List<SpendGroup> mTempGroups;           // Contains all spends.
    private List<SpendGroup> mFilteredGroups;       // Contains spends filtered from mTempGroups.

//    @Override
//    public void onQueryClick() {
//        // For the duration of the query, the mGroupName and mDataSet are left untouched
//        // and a new temporary filtered dataSet is used for display.
//        // As the query text changes, filter the tempGroups --> filteredGroups
//        // and pass it into showEntries with the searchText
//
//        mTempGroups = EntryGroup.buildSingleGroup("Matching entries", mDataSet);
//        mFilteredGroups = EntryGroup.buildSingleGroup("Matching entries", null);
//        getUi().showFilteredEntries(mTempGroups, "");       // Will show all all entries, since no search-filtering yet
//        getUi().setAddEntryVisibility(false);               // Add function disabled.
//    }

//    @Override
//    public void onQueryTextChange(String criteria) {
//        // Use mTempGroups as a flag to indicate if onQueryClick has been called yet
//        // because onQueryTextChange seems to get called first.
//        if (mTempGroups != null) {
//            getUi().hideNoFilteredEntriesWarning();
//            if (TextUtils.isEmpty(criteria)) {
//                // Use unfiltered list
//                getUi().showFilteredEntries(mTempGroups, "");
//            } else {
//                // Filter
//                List<Entry> filteredEntries = mFilteredGroups.get(0).getEntries();
//                filteredEntries.clear();
//                Predicate<Entry> predicate = getFilterPredicate(criteria);
//                for (Entry entry : mTempGroups.get(0).getEntries()) {
//                    if (predicate.apply(entry)) {
//                        filteredEntries.add(entry);
//                    }
//                }
//                getUi().showFilteredEntries(mFilteredGroups, criteria);
//                if (filteredEntries.size() == 0) {
//                    getUi().showNoFilteredEntriesWarning();
//                }
//            }
//        }
//    }

//    @Override
//    public void onQueryClose() {
//        getUi().hideNoFilteredEntriesWarning();
//        // Refresh the list with the previous groupName/mDataSet
//        onCompletion(mDataSet);
//        getUi().setAddEntryVisibility(true);       // Add function re-enabled.
//        mTempGroups = null;
//    }

    protected Predicate<Spend> getFilterPredicate(final String criteria) {
        return new Predicate<Spend>() {
            @Override
            public boolean apply(Spend spend) {
                return spend.getCreated().toLowerCase().contains(criteria.toLowerCase())
                        || spend.getAmountAsString().toLowerCase().contains(criteria.toLowerCase());
            }
        };
    }

    public void onListItemClick(Spend selectedSpend) {
//        EditPresenter editPresenter = getUi().findPresenter(EditFragment.class);
//        if (editPresenter != null) {
//            if (selectedEntry != null) {
//                editPresenter.editEntry(selectedEntry);
//            } else {
//                // Set up a new Entry for the currently selected groupname.
//                editPresenter.editNewEntry(mSpendType);
//            }
//        }
    }

    @Nullable
    public String getSpendType() {
        return mSpendType;
    }

    public List<String> getHeadings() {
        return mHeadings;
    }

    public void onClickAdd() {
    }

    public interface GirlUi extends BaseUi {
        Girl getGirl();
        void setPageImage(@DrawableRes int resourceId);
        void updateIcon(Girl girl, @ImageShade int imageShade);
        void showEntries(@Nullable List<SpendGroup> groups);
        void setAddEntryVisibility(boolean visible);
        void hideStickyHeader();
    }

}
