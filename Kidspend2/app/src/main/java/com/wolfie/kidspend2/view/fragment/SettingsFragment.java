package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.presenter.SettingsPresenter;
import com.wolfie.kidspend2.presenter.SettingsPresenter.SettingsUi;
import com.wolfie.kidspend2.view.component.settings.GroupSetting;
import com.wolfie.kidspend2.view.component.settings.ItemAutoEmailTargets;
import com.wolfie.kidspend2.view.component.settings.ItemAutoEmailThreshold;
import com.wolfie.kidspend2.view.component.settings.ItemEmailAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingsFragment extends ActionSheetFragment implements SettingsUi {

    @Nullable
    @BindView(R.id.setting_item_email_address)
    ItemEmailAddress mItemEmailAddress;

    @Nullable
    @BindView(R.id.setting_item_auto_email_targets)
    ItemAutoEmailTargets mItemAutoEmailTargets;

    @Nullable
    @BindView(R.id.setting_item_auto_email_threshold)
    ItemAutoEmailThreshold mItemAutoEmailThreshold;

    @Nullable
    @BindView(R.id.button_close)
    Button mButtonClose;

    private Unbinder mUnbinder2;

    private SettingsPresenter mSettingsPresenter;

    private GroupSetting mGroupSetting = new GroupSetting();

    @Nullable
    @Override
    public SettingsPresenter getPresenter() {
        return mSettingsPresenter;
    }

    public SettingsFragment() {
        mSettingsPresenter = new SettingsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        View content = inflater.inflate(R.layout.fragment_settings, container, false);
        mHolderView.addView(content);
        // This bind will re-bind the superclass members, so the entire view hierarchy must be
        // available, hence the content should be added to the parent view first.
        mUnbinder2 = ButterKnife.bind(this, view);
        mButtonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSettingsPresenter.onClickClose();
            }
        });
        mItemEmailAddress.setOnHideEmailSettingListener(new ItemEmailAddress.OnHideEmailSettingListener() {
            @Override
            public void onHideEmailSetting(String emailAddress) {
                mSettingsPresenter.onHideEmailSetting(emailAddress);
            }
        });
        mItemAutoEmailTargets.setOnHideSettingListener(new ItemAutoEmailTargets.OnHideSettingListener() {
            @Override
            public void onHideSetting(String emailTargets) {
                mSettingsPresenter.onHideAutoEmailTargetsSetting(emailTargets);
            }
        });
        mItemAutoEmailThreshold.setOnHideSettingListener(new ItemAutoEmailThreshold.OnHideSettingListener() {
            @Override
            public void onHideSetting(String amountThreshold) {
                mSettingsPresenter.onHideAutoEmailThresholdSetting(amountThreshold);
            }
        });

        mItemEmailAddress.setGroupSetting(mGroupSetting);
        mItemAutoEmailTargets.setGroupSetting(mGroupSetting);
        mItemAutoEmailThreshold.setGroupSetting(mGroupSetting);

        return view;
    }

    @Override
    public void hide() {
        // Can only hide the action sheet if the group agrees
        if (mGroupSetting.requestToHideCurrent()) {
            super.hide();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder2.unbind();
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        mItemEmailAddress.setEmailAddress(emailAddress);
    }

    @Override
    public void setAutoTargets(String autoTargets) {
        mItemAutoEmailTargets.setAutoEmailTargets(autoTargets);
    }

    @Override
    public void setAutoThreshold(String autoThreshold) {
        mItemAutoEmailThreshold.setAutoEmailThreshold(autoThreshold);
    }

    @Override
    public void onShowComplete() {
    }

    @Override
    public void onHideComplete() {
    }

    @Override
    public void onTouchBackground() {
    }

}
