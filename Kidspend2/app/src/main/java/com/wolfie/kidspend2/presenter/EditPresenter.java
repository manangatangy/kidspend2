package com.wolfie.kidspend2.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.Spend;
import com.wolfie.kidspend2.model.loader.AsyncListeningTask;
import com.wolfie.kidspend2.presenter.EditPresenter.EditUi;
import com.wolfie.kidspend2.view.ActionSheetUi;
import com.wolfie.kidspend2.view.fragment.GirlPagerFragment;

public class EditPresenter extends BasePresenter<EditUi>
        implements AsyncListeningTask.Listener<Boolean> {

    private final static String KEY_EDIT_ACTION_SHEET_SHOWING = "KEY_EDIT_ACTION_SHEET_SHOWING";
    private final static String KEY_EDIT_ACTION_SHEET_ENTRY = "KEY_EDIT_ACTION_SHEET_ENTRY";

    private Spend mSpend;
    private boolean mIsShowing;

    public EditPresenter(EditUi editUi) {
        super(editUi);
    }

    @Override
    public void resume() {
        super.resume();
        if (!mIsShowing) {
            getUi().hide();
        }
    }

    @Override
    public void pause() {
        super.pause();
        mIsShowing = getUi().isShowing();
        getUi().dismissKeyboard(false);
    }

    @Override
    public void onSaveState(Bundle outState) {
        outState.putBoolean(KEY_EDIT_ACTION_SHEET_SHOWING, mIsShowing);
        // TODO save/restore Entry being editted
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        mIsShowing = savedState.getBoolean(KEY_EDIT_ACTION_SHEET_SHOWING, false);
    }

    /**
     * Use the existing values in the Spend to populate the fields, and show the view.
     * A null spend is allowed, it means create a new spend with all empty fields for editing.
     */
    public void editSpend(@Nullable Spend spend, Girl girl) {
        mSpend = (spend != null) ? spend : Spend.create(girl.name(), "", 0, "today");
        getUi().show();
    }

    /**
     * Create a new Spend for the specified spendType, and show its fields for editing.
     * If the spendType is null, then set all the fields to empty string
     */
    public void editNewEntry(@Nullable String spendType, Girl girl) {
        editSpend(spendType == null
                  ? null
                  : Spend.create(girl.name(), spendType, 0, "today"), girl);
    }

    /**
     * This is not called on resume, only when the view is shown by the user.
     */
    public void onShow() {
        getUi().enableDeleteButton(!mSpend.isNew());
        getUi().setTitleText((mSpend.isNew() ? "Create " : "Modify ") + mSpend.getGirl() + " Spend");
        getUi().clearErrorMessage();
        getUi().clearDescription();
        getUi().setTextValues(mSpend);
    }

    public void onClickSave() {
        Spend spend = getUi().getTextValues(mSpend);
        if (spend == null) {
            return;     // Error is displayed.
        }
        getUi().dismissKeyboard(true);
        mSpend = spend;
        MainPresenter mainPresenter = getUi().findPresenter(null);
        if (mSpend.isNew()) {
            mainPresenter.getSpendLoader().insert(mSpend, this);
        } else {
            mainPresenter.getSpendLoader().update(mSpend, this);
        }
    }

    public void onClickDelete() {
        if (!showErrorIfModified()) {
            getUi().dismissKeyboard(true);
            MainPresenter mainPresenter = getUi().findPresenter(null);
            mainPresenter.getSpendLoader().delete(mSpend, this);
        }
    }

    public void onClickCancel() {
        getUi().dismissKeyboard(true);
        if (!showErrorIfModified()) {
            getUi().hide();
        }
    }

    @Override
    public boolean backPressed() {
        if (!getUi().isShowing() || getUi().isKeyboardVisible()) {
            return true;        // Means: not consumed here
        }
        // Check if fields have been modified and show error message if so, else close.
        if (!showErrorIfModified()) {
            getUi().hide();
        }
        return false;
    }

    private boolean showErrorIfModified() {
        boolean isModified = getUi().isEntryModified(mSpend);
        if (isModified) {
            getUi().setErrorMessage(R.string.er003);
        }
        return isModified;
    }

    /**
     * callback from getSpendLoader().insert/update/delete
     */
    @Override
    public void onCompletion(Boolean aBoolean) {
        GirlPagerPresenter girlPagerPresenter = getUi().findPresenter(GirlPagerFragment.class);
        if (girlPagerPresenter != null) {
            GirlPresenter girlPresenter =  girlPagerPresenter.getCurrentGirlPresenter();
            if (girlPresenter != null) {
                girlPresenter.loadEntries();
            }
        }
    }

    public interface EditUi extends ActionSheetUi {

        void setTitleText(String title);
        void enableDeleteButton(boolean enable);
        void setTextValues(@NonNull Spend spend);
        Spend getTextValues(Spend spend);
        boolean isEntryModified(Spend spend);
        void setDescription(@StringRes int resourceId);
        void clearDescription();
        void setErrorMessage(@StringRes int resourceId);
        void clearErrorMessage();

    }
}
