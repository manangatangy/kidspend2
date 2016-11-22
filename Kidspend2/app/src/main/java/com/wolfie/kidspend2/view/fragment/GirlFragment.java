package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.presenter.GirlPresenter.GirlUi;
import com.wolfie.kidspend2.presenter.GirlPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GirlFragment extends BaseFragment implements GirlUi {

    public static final String KEY_GIRL_NAME = "KEY_GIRL_NAME";

    @BindView(R.id.text_label)
    TextView mTextView;

    // This member is first set from onCreateView() and then saved/restored as needed.
    private Girl mGirl;
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
        mGirl = Girl.valueOf(getArguments().getString(KEY_GIRL_NAME));
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putString(GirlFragment.KEY_GIRL_NAME, mGirl.name());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mGirl = Girl.valueOf(savedInstanceState.getString(KEY_GIRL_NAME));
        }
    }

    @Override
    public Girl getGirl() {
        return mGirl;
    }

    @Override
    public void setLabel(String text) {
        mTextView.setText(text);
    }
}
