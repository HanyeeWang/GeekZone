package com.hanyee.geekzone.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanyee.androidlib.base.BaseActivity;
import com.hanyee.androidlib.utils.AppUtils;
import com.hanyee.androidlib.utils.SnackbarUtil;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.base.SuperiorFragment;
import com.hanyee.geekzone.model.bean.event.NightModeEvent;
import com.hanyee.geekzone.presenter.SettingPresenter;
import com.hanyee.geekzone.presenter.contract.SettingContract;
import com.hanyee.geekzone.util.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.hanyee.androidlib.utils.SnackbarUtil.INFO;

/**
 * Created by Hanyee on 16/10/18.
 */
public class SettingFragment extends SuperiorFragment<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.cb_setting_cache)
    AppCompatCheckBox mCbSettingCache;
    @BindView(R.id.cb_setting_image)
    AppCompatCheckBox mCbSettingImage;
    @BindView(R.id.cb_setting_night)
    AppCompatCheckBox mCbSettingNight;
    @BindView(R.id.setting_feedback)
    LinearLayout mSettingFeedback;
    @BindView(R.id.tv_setting_clear)
    TextView mTvSettingClear;
    @BindView(R.id.setting_clear)
    LinearLayout mSettingClear;
    @BindView(R.id.tv_setting_update)
    TextView mTvSettingUpdate;
    @BindView(R.id.setting_update)
    LinearLayout mSettingUpdate;

    private boolean mIsBundleNull;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mIsBundleNull = savedInstanceState == null;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Object getLayoutRes() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initData() {
        setToolBar((BaseActivity) mActivity, mToolbar, getString(R.string.setting));

        mCbSettingNight.setChecked(mPresenter.isNightModeState());
        mCbSettingCache.setChecked(mPresenter.isArticleAutoCache());
        mCbSettingImage.setChecked(mPresenter.isArticleNoImage());

        //TODO:
        mTvSettingClear.setText("2.14M");
        mTvSettingUpdate.setText(AppUtils.getVersion(mContext));

        finishLoading();
    }

    @OnCheckedChanged({R.id.cb_setting_cache, R.id.cb_setting_image, R.id.cb_setting_night})
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        if (!mIsInited)
            return;

        switch (view.getId()) {
            case R.id.cb_setting_cache:
                mPresenter.setArticleAutoCacheState(isChecked);
                break;
            case R.id.cb_setting_image:
                mPresenter.setArticleNoImageState(isChecked);
                break;
            case R.id.cb_setting_night:
                if (mIsBundleNull) {
                    mPresenter.setNightModeState(isChecked);
                    NightModeEvent event = new NightModeEvent();
                    event.setNightMode(isChecked);
                    EventBus.getDefault().post(event);
                }
                break;
        }
    }

    @OnClick({R.id.setting_feedback, R.id.setting_clear, R.id.setting_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_feedback:
                Utils.sendEmail(mContext, getString(R.string.setting_email));
                break;
            case R.id.setting_clear:
                //TODO:
                break;
            case R.id.setting_update:
                SnackbarUtil.LongSnackbar(view, R.string.setting_update_tips, INFO).show();
                break;
        }
    }
}
