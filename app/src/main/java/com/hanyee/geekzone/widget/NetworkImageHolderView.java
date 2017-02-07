package com.hanyee.geekzone.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBPageAdapter;
import com.bumptech.glide.Glide;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.app.GeekZoneApplication;
import com.hanyee.geekzone.model.bean.zhihu.NewsDailyBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;
import com.hanyee.geekzone.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetworkImageHolderView implements CBPageAdapter.Holder<NewsDailyBean.TopStoriesBean> {

    @BindView(R.id.image)
    ImageView mImageView;
    @BindView(R.id.title)
    TextView mTitleView;

    @Override
    public View createView(Context context) {
        View container = LayoutInflater.from(context).inflate(R.layout.header_switch_item, null);
        ButterKnife.bind(this, container);
        return container;
    }

    @Override
    public void UpdateUI(Context context, final int position, final NewsDailyBean.TopStoriesBean data) {
        mTitleView.setText(data.getTitle());
        Glide.with(GeekZoneApplication.getInstance())
                .load(data.getImage()).fitCenter().into(mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = mImageView.getContext();
                Intent intent = new Intent(context, ArticleDetailActivity.class);
                intent.putExtra(Constants.ARTICLE.EXTRA_ARTICLE_ID, data.getId());
                intent.putExtra(Constants.ARTICLE.EXTRA_ARTICLE_TYPE, Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS);
                context.startActivity(intent);
            }
        });
    }
}