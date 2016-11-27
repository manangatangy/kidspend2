package com.wolfie.kidspend2.view.fragment;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.presenter.GirlPresenter;
import com.wolfie.kidspend2.util.BitmapWorkerTask;
import com.wolfie.kidspend2.util.DefaultLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GirlFragment extends BaseFragment implements GirlUi {

//    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";

    @BindView(R.id.text_view)
    TextView mTextView;

    @BindView(R.id.background_image)
    ImageView mImageView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.add_entry_fab)
    View mAddEntryButton;

    @OnClick(R.id.add_entry_fab)
    public void onAddEntryClick() {
        mGirlPresenter.onClickAdd();
    }

    // This member is first set from onCreateView() and then saved/restored as needed.
//    private Girl mGirl;
//    private int mImageIndex;    // Index into the Girl.mImageIds.
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

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (outState != null) {
//            outState.putString(GirlFragment.KEY_GIRL_NAME, mGirl.name());
//        }
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) {
//            mGirl = Girl.valueOf(savedInstanceState.getString(KEY_GIRL_NAME));
//        }
//    }
//
//    public void setBackgroundImage(int enumIndex) {
//        // If this is called before the toolbar is laid out then the height will be zero :/
////        int toolBarHeight = mToolbar.getHeight();
//        int toolBarHeight = 0;
//        mImageView.setPadding(0, toolBarHeight, 0, 0);
//        // adapt the image to the size of the display
//        Display display = mBaseActivity.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int resourceId = ImageEnum.getImageId(enumIndex);
//        BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(mImageView,
//                getResources(), resourceId, size.x, size.y);
//    }

    @Override
    public Girl getGirl() {
        return Girl.valueOf(getArguments().getString(GirlPresenter.KEY_GIRL_NAME));
    }

    @Override
    public void setLabel(String text) {
        mTextView.setText(text);
    }
}
