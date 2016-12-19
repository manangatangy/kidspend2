package com.wolfie.kidspend2.view;


public interface ActionSheetUi extends BaseUi {

    // The following are implemented in ActionSheetFragment
    void dismissKeyboard(boolean andClose);
    boolean isKeyboardVisible();
    void show();
    void hide();
    boolean isShowing();

}
