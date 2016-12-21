package com.wolfie.kidspend2.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.presenter.EditPresenter;
import com.wolfie.kidspend2.presenter.EditPresenter.EditUi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EditFragment extends ActionSheetFragment implements EditUi {

    @Nullable
    @BindView(R.id.text_title)
    TextView mTextTitle;

    @Nullable
    @BindView(R.id.text_description)
    TextView mTextDescription;

    @Nullable
    @BindView(R.id.edit_text_amount)
    EditText mEditAmount;

    @Nullable
    @BindView(R.id.edit_text_date)
    EditText mEditDate;

    @Nullable
    @BindView(R.id.edit_text_type)
    EditText mEditType;

    @Nullable
    @BindView(R.id.text_error)
    TextView mTextError;

    @Nullable
    @BindView(R.id.button_save)
    Button mButtonSave;

    @Nullable
    @BindView(R.id.button_cancel)
    Button mButtonCancel;

    @Nullable
    @BindView(R.id.button_delete)
    Button mButtonDelete;

    private Unbinder mUnbinder2;

    private EditPresenter mEditPresenter;

    @Override
    public EditPresenter getPresenter() {
        return mEditPresenter;
    }

    public EditFragment() {
        mEditPresenter = new EditPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);
        View content = inflater.inflate(R.layout.fragment_edit, container, false);
        mHolderView.addView(content);
        // This bind will re-bind the superclass members, so the entire view hierarchy must be
        // available, hence the content should be added to the parent view first.
        mUnbinder2 = ButterKnife.bind(this, view);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPresenter.onClickCancel();
            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPresenter.onClickSave();
            }
        });
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditPresenter.onClickDelete();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder2.unbind();
    }

    @Override
    public void setTitleText(String title) {
        mTextTitle.setText(title);
    }

    @Override
    public void enableDeleteButton(boolean enable) {
        mButtonDelete.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setTextValues(@NonNull Spend spend) {
        mEditAmount.setText("" + spend.getAmount());
        mEditDate.setText(spend.getCreated());
        mEditType.setText(spend.getSpendType());
    }

    @Override
    public Spend getTextValues(Spend spend) {
        // Returns null on error (also shows error messages)
        boolean error = false;
        try {
            String amount = mEditAmount.getText().toString();
            if (amount.length() == 0) {
                setErrorMessage(R.string.er001);
                error = true;
            } else {
                spend.setAmount(Integer.decode(amount));
            }
        } catch (NumberFormatException nfe) {
            mEditAmount.setError(getContext().getString(R.string.er001));
            error = true;
        }

        String date = mEditDate.getText().toString().toUpperCase();
        if (Spend.parseDateString(date) != null) {
            spend.setCreated(date);
        } else {
            setErrorMessage(R.string.er002);
            error = true;
        }

        String type = mEditType.getText().toString().trim();
        spend.setSpendType(type);

        return error ? null : spend;
    }

    @Override
    public boolean isEntryModified(Spend spend) {
        String amount = mEditAmount.getText().toString();
        String date = mEditDate.getText().toString().toUpperCase();
        String type = mEditType.getText().toString().trim();
        String amountPrev = (spend == null) ? null : ("" + spend.getAmount());
        String datePrev = (spend == null) ? null : spend.getCreated();
        String typePrev = (spend == null) ? null : spend.getSpendType();
        return !equals(amount, amountPrev) ||
                !equals(date, datePrev) ||
                !equals(type, typePrev);
    }

    private boolean equals(@Nullable String val1, @Nullable String val2) {
        if (val1 == null) {
            val1 = "";
        }
        if (val2 == null) {
            val2 = "";
        }
        return val1.equals(val2);
    }

    @Override
    public void setDescription(@StringRes int resourceId) {
        mTextDescription.setText(resourceId);
    }

    @Override
    public void clearDescription() {
        mTextDescription.setText("");
    }

    @Override
    public void setErrorMessage(@StringRes int resourceId) {
        mTextError.setVisibility(View.VISIBLE);
        mTextError.setText(resourceId);
    }

    @Override
    public void clearErrorMessage() {
        mTextError.setVisibility(View.GONE);
    }

    @Override
    public void onShowComplete() {
        mEditPresenter.onShow();
    }

    @Override
    public void onHideComplete() {
    }

    @Override
    public void onTouchBackground() {
    }

}
