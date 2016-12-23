package com.wolfie.kidspend2.view.component.settings;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.wolfie.kidspend2.R;

import butterknife.BindView;

public class ItemAutoEmailThreshold extends ItemLayout {

    private OnHideSettingListener mListener;

    @Nullable
    @BindView(R.id.edit_text_auto_email_threshold)
    TextView mTextAutoEmailThreshold;

    public ItemAutoEmailThreshold(Context context) {
        super(context);
    }

    public ItemAutoEmailThreshold(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemAutoEmailThreshold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemAutoEmailThreshold(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public String getHeadingText() {
        return "Threshold amount for auto-summary";
    }

    public void setAutoEmailThreshold(String text) {
        mTextAutoEmailThreshold.setText(text);
    }

    @Override
    public void hide() {
        super.hide();
        if (mListener != null) {
            mListener.onHideSetting(mTextAutoEmailThreshold.getText().toString());
        }
    }

    public void setOnHideSettingListener(final OnHideSettingListener listener) {
        mListener = listener;
    }

    public interface OnHideSettingListener {
        void onHideSetting(String amountThreshold);
    }

}
