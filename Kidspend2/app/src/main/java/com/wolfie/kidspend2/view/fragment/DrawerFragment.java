package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.DrawerPresenter;
import com.wolfie.kidspend2.presenter.DrawerPresenter.DrawerUi;
import com.wolfie.kidspend2.util.DefaultLayoutManager;
import com.wolfie.kidspend2.view.adapter.NavMenuRecyclerAdapter;
import com.wolfie.kidspend2.view.adapter.NavMenuRecyclerAdapter.MenuItemViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DrawerFragment extends BaseFragment
        implements DrawerUi, NavMenuRecyclerAdapter.OnNavMenuItemClickListener {

    @BindView(R.id.navigation_recycler_view)
    RecyclerView mRecyclerView;

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
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void refreshListWithHeadings(List<String> headings, boolean isShowAll) {
        getAdapter().setMenuItems(headings, isShowAll);
    }

    @Override
    public void selectListItem(@Nullable String selected) {
        // selected == null means select the top item (ALL_GROUPS)
        final int adapterPosition = (selected == null) ? 0 : getAdapter().getAdapterPositionForItem(selected);
        if (adapterPosition >= 0) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    MenuItemViewHolder viewHolder =
                            (MenuItemViewHolder)mRecyclerView.findViewHolderForAdapterPosition(adapterPosition);
                    if (viewHolder != null) {
                        viewHolder.setSelected(false);       // Won't cause call to onNavMenuItemClick
                    }
                }
            });
        }
    }

    private NavMenuRecyclerAdapter getAdapter() {
        NavMenuRecyclerAdapter adapter = (NavMenuRecyclerAdapter)mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new NavMenuRecyclerAdapter(getContext());
            adapter.setNavMenuItemClickListener(this);
            mRecyclerView.setAdapter(adapter);
        }
        return adapter;
    }

    /**
     * @param spendType - will be null to indicate ALL_GROUPS_NAV_HEADING
     * @param hasChanged - means not already selected
     */
    @Override
    public void onNavMenuItemClick(String spendType, boolean hasChanged) {
        mDrawerPresenter.onItemSelected(spendType, hasChanged);
    }

    public void onDrawerOpened() {
        mDrawerPresenter.onDrawerOpened();
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

    @OnClick(R.id.menu_item_settings)
    void onMenuSettings() {
        mDrawerPresenter.onMenuSettingsClick();
    }

    @OnClick(R.id.menu_toggle_summary_all)
    void onMenuToggleSummaryAll() {
        mDrawerPresenter.onMenuToggleSummaryAllClick();
    }
    @OnClick(R.id.menu_item_export)
    void onMenuExport() {
        mDrawerPresenter.onMenuExportClick();
    }
    @OnClick(R.id.menu_item_import)
    void onMenuImport() {
        mDrawerPresenter.onMenuImportClick();
    }
}
