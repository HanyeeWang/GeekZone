package com.hanyee.geekzone.ui.fragment.main;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hanyee.androidlib.base.BaseActivity;
import com.hanyee.androidlib.cache.image.glide.CircleTransform;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.presenter.AboutPresenter;

import butterknife.BindView;

/**
 * Created by Hanyee on 16/10/18.
 */
public class AboutFragment extends SuperiorFragment<AboutPresenter> {

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.head_layout)
    RelativeLayout mHeadLayout;
    @BindView(R.id.about_content)
    LinearLayout mAboutContent;
    @BindView(R.id.avatar)
    ImageView mAvatar;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initData() {
        setToolBar((BaseActivity) mActivity, mToolbar, null);
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mHeadLayout.getHeight() * 0.7) {
                    mCollapsingToolbar.setTitle(getString(R.string.about));
                } else {
                    mCollapsingToolbar.setTitle("");
                }
            }
        });
        Glide.with(GeekZoneApplication.getInstance()).load(R.drawable.hughjackman)
                .transform(new CircleTransform(GeekZoneApplication.getInstance()))
                .into(mAvatar);
        finishLoading();
    }
}
