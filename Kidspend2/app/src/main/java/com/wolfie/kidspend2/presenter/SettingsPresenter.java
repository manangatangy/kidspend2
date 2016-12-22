package com.wolfie.kidspend2.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.presenter.SettingsPresenter.SettingsUi;
import com.wolfie.kidspend2.view.ActionSheetUi;

public class SettingsPresenter extends BasePresenter<SettingsUi> {

    private final static String KEY_SETTINGS_ACTION_SHEET_SHOWING = "KEY_SETTINGS_ACTION_SHEET_SHOWING";
//    public final static String PREF_SESSION_TIMEOUT = "PREF_SESSION_TIMEOUT";
//    public final static String PREF_SESSION_BACKGROUND_IMAGE = "PREF_SESSION_BACKGROUND_IMAGE";
    public final static String PREF_SESSION_BACKUP_EMAIL_ADDRESS = "PREF_SESSION_BACKUP_EMAIL_ADDRESS";

    private boolean mIsShowing;

    private SharedPreferences mPrefs;

    public SettingsPresenter(SettingsUi settingsUi) {
        super(settingsUi);
    }

    @Override
    public void resume() {
        super.resume();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (mIsShowing) {
            getUi().show();
        } else {
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
        outState.putBoolean(KEY_SETTINGS_ACTION_SHEET_SHOWING, mIsShowing);
    }

    @Override
    public void onRestoreState(@Nullable Bundle savedState) {
        mIsShowing = savedState.getBoolean(KEY_SETTINGS_ACTION_SHEET_SHOWING, false);
    }

    public void show() {
        // Load the current settings into the view elements.
//        int sessionTimeout = mPrefs.getInt(PREF_SESSION_TIMEOUT, TimeoutMonitor.DEFAULT_TIMEOUT);
//        getUi().setTimeout(sessionTimeout);

//        int enumIndex = mPrefs.getInt(PREF_SESSION_BACKGROUND_IMAGE, SimpleActivity.DEFAULT_BACKGROUND_IMAGE);
//        getUi().setImageItem(enumIndex);

        String emailAddress =  mPrefs.getString(PREF_SESSION_BACKUP_EMAIL_ADDRESS, "");
        getUi().setEmailAddress(emailAddress);

        getUi().show();
    }

    public void onClickClose() {
        getUi().dismissKeyboard(false);
        getUi().hide();
    }

    @Override
    public boolean backPressed() {
        if (!getUi().isShowing() || getUi().isKeyboardVisible()) {
            return true;        // Means: not consumed here
        }
        onClickClose();         // Checks if all the settings items are good to close.
        return false;
    }

//    public void onImageSelected(int enumIndex) {
//        // Change the actual background image
//        getUi().setActivityBackgroundImage(enumIndex);
//
//        // Save in prefs
//        SharedPreferences.Editor editor = mPrefs.edit();
//        editor.putInt(PREF_SESSION_BACKGROUND_IMAGE, enumIndex);
//        editor.apply();
//    }
//
//    public void onTimeoutChanged(int timeoutInMillis) {
//        // Change the timing source
//        MainPresenter mainPresenter = getUi().findPresenter(null);
//        mainPresenter.setTimeout(timeoutInMillis, true);        // Also restart timer
//
//        // Save in prefs
//        SharedPreferences.Editor editor = mPrefs.edit();
//        editor.putInt(PREF_SESSION_TIMEOUT, timeoutInMillis);
//        editor.apply();
//    }
//
//    public void onChangePassword(String password, String confirm) {
//        if (password == null || !password.equals(confirm)) {
//            getUi().setPasswordError(R.string.st007);
//        } else if (password.trim().length() != password.length()) {
//            getUi().setPasswordError(R.string.st011);
//        } else {
//            getUi().dismissKeyboard(false);
//
//            MainPresenter mainPresenter = getUi().findPresenter(null);
//            LoginPresenter loginPresenter = getUi().findPresenter(LoginFragment.class);
//            RemasterLoader remasterLoader = mainPresenter.makeRemasterLoader(loginPresenter.getMediumCrypter());
//            remasterLoader.remaster(password, this);
//        }
//    }

    public void onHideEmailSetting(String emailAddress) {
        // Save in prefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_SESSION_BACKUP_EMAIL_ADDRESS, emailAddress);
        editor.apply();
    }

    public interface SettingsUi extends ActionSheetUi {

//        void setTimeout(int timeoutInMillis);
        void setEmailAddress(String emailAddress);

//        // Must clear the field so that hide isn't inhibited.
//        void clearPasswordsAndHidePasswordsSetting();
//        void setPasswordError(@StringRes int resId);
//        void setImageItem(int enumIndex);
//        // This method actually changes the image via the EskeyActivity
//        void setActivityBackgroundImage(int enumIndex);
    }

}
