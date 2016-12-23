package com.wolfie.kidspend2.presenter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.wolfie.kidspend2.presenter.SettingsPresenter.SettingsUi;
import com.wolfie.kidspend2.view.ActionSheetUi;

public class SettingsPresenter extends BasePresenter<SettingsUi> {

    private final static String KEY_SETTINGS_ACTION_SHEET_SHOWING = "KEY_SETTINGS_ACTION_SHEET_SHOWING";
    public final static String PREF_SETTINGS_BACKUP_EMAIL_ADDRESS = "PREF_SETTINGS_BACKUP_EMAIL_ADDRESS";
    public final static String PREF_SETTINGS_AUTO_EMAIL_TARGETS = "PREF_SETTINGS_AUTO_EMAIL_TARGETS";
    public final static String PREF_SETTINGS_AUTO_EMAIL_THRESHOLD = "PREF_SETTINGS_AUTO_EMAIL_THRESHOLD";

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

    public static final String targetEmails =
            "david.x.weiss@gmail.com claireweiss1988@gmail.com nina.e.weiss@hotmail.com rachel.weiss26@hotmail.com"
    ;

    public void show() {
        // Load the current settings into the view elements.
        String emailAddress =  mPrefs.getString(PREF_SETTINGS_BACKUP_EMAIL_ADDRESS, "");
        getUi().setEmailAddress(emailAddress);
        String autoTargets =  mPrefs.getString(PREF_SETTINGS_AUTO_EMAIL_TARGETS, targetEmails);
        getUi().setAutoTargets(autoTargets);
        String autoThreshold =  mPrefs.getString(PREF_SETTINGS_AUTO_EMAIL_THRESHOLD, "0");
        getUi().setAutoThreshold(autoThreshold);

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

    public void onHideEmailSetting(String emailAddress) {
        // Save in prefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_SETTINGS_BACKUP_EMAIL_ADDRESS, emailAddress);
        editor.apply();
    }

    public void onHideAutoEmailTargetsSetting(String emailTargets) {
        // Save in prefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_SETTINGS_AUTO_EMAIL_TARGETS, emailTargets);
        editor.apply();
    }

    public void onHideAutoEmailThresholdSetting(String amountThreshold) {
        // Save in prefs
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_SETTINGS_AUTO_EMAIL_THRESHOLD, amountThreshold);
        editor.apply();
    }

    public interface SettingsUi extends ActionSheetUi {
        void setEmailAddress(String emailAddress);
        void setAutoTargets(String autoTargets);
        void setAutoThreshold(String autoThreshold);
    }

}
