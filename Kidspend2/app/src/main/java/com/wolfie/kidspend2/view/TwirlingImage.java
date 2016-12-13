package com.wolfie.kidspend2.view;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wolfie.kidspend2.R;
import com.wolfie.kidspend2.model.Girl;
import com.wolfie.kidspend2.model.ImageCollection.ImageShade;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TwirlingImage extends RelativeLayout {

    @BindView(R.id.icon_image_view)
    public ImageView mIconImageView;

    @BindView(R.id.icon_background_view)
    public View mIconBackgroundView;

    private @DrawableRes int mResourceId = -1;

    protected Unbinder unbinder;

    public TwirlingImage(Context context) {
        super(context);
        initView();
    }

    public TwirlingImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TwirlingImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public TwirlingImage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
    }

    @Override
    @CallSuper
    protected void onFinishInflate() {
        super.onFinishInflate();
        unbinder = ButterKnife.bind(this);

        // Init the icon for closed
        float slideOffset = 0;
        setIconImageViewLevel(3333 + (float)(6666) * slideOffset);
        setBackgroundVisibility(slideOffset);
    }

    public void updateIcon(Girl girl, @ImageShade int imageShade) {
        @DrawableRes int resourceId = girl.getIconResourceId(imageShade);
        if (mResourceId != resourceId) {
            // Only set the drawable if it's changed.
            mResourceId = resourceId;
            mIconImageView.setImageDrawable(getResources().getDrawable(mResourceId, null));
        }
        // TODO change the black/white backing view too.
    }

    public void setIconImageViewLevel(float level) {                // 0 to 10000
        mIconImageView.setImageLevel((int)level);
    }

    public void setBackgroundVisibility(float slideOffset) {        // 0 to 1.0
        // Once the drawer is fully open, then blacken/whiten the background.
        mIconBackgroundView.setVisibility((slideOffset == 1) ? View.VISIBLE : View.GONE);
    }

}
