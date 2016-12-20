package com.wolfie.kidspend2.view.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.model.SpendGroup;
import com.wolfie.kidspend2.presenter.GirlPagerPresenter;
import com.wolfie.kidspend2.presenter.GirlPresenter;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.util.BitmapWorkerTask;
import com.wolfie.kidspend2.util.DefaultLayoutManager;
import com.wolfie.kidspend2.view.adapter.GroupingRecyclerAdapter;
import com.wolfie.kidspend2.view.adapter.GroupingRecyclerAdapter.AdapterMode;
import com.wolfie.kidspend2.view.adapter.ScrollListeningRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * There sill be instantiated a GirlFragment/GirlPresenter for each {@link Girl} value.
 * The {@link GirlPresenter} holds the corresponding {@link Girl} instance so that it
 * knows which one it corresponds to.  Note that the currently selected Girl is determined
 * by the {@link GirlPagerPresenter}
 */
public class GirlFragment extends BaseFragment
        implements GirlUi, ScrollListeningRecyclerView.ItemScrollListener,
                   GroupingRecyclerAdapter.OnItemInListClickedListener {

    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";

    @BindView(R.id.background_image)
    ImageView mImageView;

    @BindView(R.id.sticky_header)
    View mStickyHeaderFrame;

    @BindView(R.id.heading_divider_top)
    View mStickyHeaderDividerTop;

    @BindView(R.id.heading_text_view)
    TextView mStickyHeaderText;

    @BindView(R.id.recycler_view)
    ScrollListeningRecyclerView mRecyclerView;

    @BindView(R.id.add_entry_fab)
    View mAddEntryButton;

    private boolean mAutoUpdateStickyHeader;

    private GirlPresenter mGirlPresenter;

    @Override
    public GirlPresenter getPresenter() {
        return mGirlPresenter;
    }

    public GirlFragment() {
        mGirlPresenter = new GirlPresenter(this);
    }

    /**
     * We bind this object here rather than waiting for {@link BaseFragment#onViewCreated(View, Bundle)}
     * since the presenter resume will have occurred by then (which is too late).
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));

//        mRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
        mRecyclerView.setItemScrollListener(this);
    }
    public void onShowing() {
        getPresenter().onShowing();
    }

    @Override
    public Girl getGirl() {
        // Rely on the Girl state always being available in the Fragment args
        return Girl.valueOf(getArguments().getString(GirlFragment.KEY_GIRL_NAME));
    }

    @Override
    public void setPageImage(@DrawableRes int resourceId) {
        // If this is called before the toolbar is laid out then the height will be zero :/
//        int toolBarHeight = mToolbar.getHeight();
        int toolBarHeight = 0;
        mImageView.setPadding(0, toolBarHeight, 0, 0);
        // adapt the image to the size of the display
        Display display = mBaseActivity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        new BitmapWorkerTask(mImageView, getResources(), resourceId, size.x, size.y);
    }

    @Override
    public void updateIcon(Girl girl, @ImageShade int imageShade) {
        // Inform the main activity (which owns the twirling icon)
        // that there's been a change of girl or image background shade.
        getKidspendActivity().updateIcon(girl, imageShade);
    }

    /**
     * Display the spends in an expanding/contracting list view, with no highlighting.
     * @param groups Lists of spends to display.  If null, clear the list. Otherwise
     *               also scroll to the top of the list.
     */
    @Override
    public void showEntries(@Nullable List<SpendGroup> groups) {
        mAutoUpdateStickyHeader = true;
        if (groups == null) {
            getAdapter(AdapterMode.EXPANDING_CONTRACTING).clearItems();
        } else {
            getAdapter(AdapterMode.EXPANDING_CONTRACTING).setGroups(groups, null);
            // Scroll back to the top of the list.  If the list is short, no scrolling
            // will occur and so we also have to trigger the sticky header refresh.
            mRecyclerView.scrollToPosition(0);
            onItemAlignedToTop(0);
        }
    }

    /**
     * Display the spends in an fixed expanded list view, with highlighting.
     * Shows all the spends expanded, fixes the sticky header to a static text,
     * rather than the auto-updating value (depending on which spend is at the top).
     * @param groups Lists of spends to display.  If null, clear the list. Otherwise
     *               also scroll to the top of the list.
     * @param searchText If not blank, then use to highlight the fields in the viewHolders.
     */
    public void showFilteredEntries(@NonNull List<SpendGroup> groups, @NonNull String searchText) {
        mAutoUpdateStickyHeader = false;
        int matches = groups.get(0).getSpends().size();        // Must be one SpendGroup
        mStickyHeaderText.setText(groups.get(0).getHeading() + ":  " + matches);

        getAdapter(AdapterMode.FIXED_EXPANDED).setGroups(groups, searchText);
        // Scroll back to the top of the list.  If the list is short, no scrolling
        // will occur and so we also have to trigger the sticky header refresh.
        mRecyclerView.scrollToPosition(0);
        onItemAlignedToTop(0);
    }

    /**
     * @param mode specifies if the list items should be shown as initially contracted or
     *             expanded. If this mode is different to the current adapter's mode, then
     *             create a new one with the required mode.
     * @return the GroupingRecyclerAdapter which should be used for supplying the recyclerView
     */
    private GroupingRecyclerAdapter getAdapter(@GroupingRecyclerAdapter.AdapterMode int mode) {
        GroupingRecyclerAdapter adapter = (GroupingRecyclerAdapter)mRecyclerView.getAdapter();
        if (adapter == null || adapter.getMode() != mode) {
            adapter = new GroupingRecyclerAdapter(mode);
            adapter.setOnItemInListClickerListener(this);
            mRecyclerView.setAdapter(adapter);
        }
        return adapter;
    }

    @Override
    public void onItemAlignedToTop(int position) {
        if (mAutoUpdateStickyHeader) {
            // This method is called by the ScrollListeningRecyclerView.  At this stage,
            // showEntries or showFilteredEntries should have already been called to populate
            // the list, so there should be an adapter instance already associated with the
            // recyclerView (and we don't need to specify the AdapterMode).
            if (mRecyclerView.getAdapter() != null) {
                GroupingRecyclerAdapter adapter = (GroupingRecyclerAdapter)mRecyclerView.getAdapter();
                Object item = adapter.getItemAt(position);
                String headerText;
                if (item instanceof Spend) {
                    Spend spend = (Spend) item;
                    headerText = spend.getSpendType();
//            if (headerText == null || headerText.length() == 0) {
//                return;
//            }
                } else {
                    headerText = (String) item;
                }
                mStickyHeaderText.setText(headerText);
                mStickyHeaderFrame.setVisibility(View.VISIBLE);
                mStickyHeaderDividerTop.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setAddEntryVisibility(boolean visible) {
        mAddEntryButton.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void hideStickyHeader() {
        mStickyHeaderFrame.setVisibility(View.GONE);
    }

    @Override
    public void onListItemClick(Spend selectedSpend) {
        mGirlPresenter.onListItemClick(selectedSpend);
    }

    @OnClick(R.id.add_entry_fab)
    public void onAddEntryClick() {
        mGirlPresenter.onClickAdd();
    }

}
