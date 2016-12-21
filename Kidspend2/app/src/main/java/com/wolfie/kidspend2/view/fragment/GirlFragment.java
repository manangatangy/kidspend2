package com.wolfie.kidspend2.view.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
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
        implements GirlUi, GroupingRecyclerAdapter.OnItemInListClickedListener,
                   SwipeRefreshLayout.OnRefreshListener {

    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";

    @BindView(R.id.background_image)
    ImageView mImageView;

    @BindView(R.id.text_heading1)
    TextView mHeader1Text;

    @BindView(R.id.text_heading2)
    TextView mHeader2Text;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.add_entry_fab)
    View mAddEntryButton;

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
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new DefaultLayoutManager(getContext()));
    }

    @Override
    public void onRefresh() {
        mGirlPresenter.bumpImage();
    }

    @Override
    public void hidePullToRefreshSpinner() {
        mSwipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void showEntries(SpendGroup spendGroup) {
        getAdapter().setGroups(spendGroup.getSpends());
        mHeader1Text.setText(spendGroup.getHeading());
        mHeader2Text.setText(spendGroup.getTotalAsString());
    }

    private GroupingRecyclerAdapter getAdapter() {
        GroupingRecyclerAdapter adapter = (GroupingRecyclerAdapter)mRecyclerView.getAdapter();
        if (adapter == null) {
            adapter = new GroupingRecyclerAdapter();
            adapter.setOnItemInListClickerListener(this);
            mRecyclerView.setAdapter(adapter);
        }
        return adapter;
    }

    @Override
    public void setAddEntryVisibility(boolean visible) {
        mAddEntryButton.setVisibility(visible ? View.VISIBLE : View.GONE);
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
