package com.hanyee.geekzone.ui.adapter.gank;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanyee.androidlib.base.BaseAdapter;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.model.bean.gank.GankNewsBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TIME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TITLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_AUTHOR_NAME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_IMAGE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_GANK;
import static com.hanyee.geekzone.util.Constants.GANK.WELFARE;

public class GankListAdapter extends BaseAdapter<GankNewsBean> {

    protected Map<String, ImageSize> mImageSizesMap;

    @Inject
    public GankListAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (GankNewsBean item : mData) {
            if (mImageSizesMap == null) {
                mImageSizesMap = new HashMap<>();
            }
            mImageSizesMap.put(item.getUrl(), new ImageSize());
        }
        return new GankListViewHolder(getCreatedView(parent, R.layout.list_item_gank_news), mImageSizesMap);
    }

    public static class GankListViewHolder extends BaseViewHolder<GankNewsBean> {

        @BindView(R.id.girl_pic)
        ImageView girl;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.authorHref)
        TextView author;
        @BindView(R.id.news_container)
        ViewGroup newsContainer;
        @BindView(R.id.article_content_item)
        ViewGroup articleContentItem;

        private Map<String, ImageSize> mImageSizeMap;

        public GankListViewHolder(View view, Map<String, ImageSize> map) {
            super(view);
            mImageSizeMap = map;
        }

        @Override
        public void onBindData2View(final GankNewsBean data) {
            if (TextUtils.equals(WELFARE, data.getType())) {
                newsContainer.setVisibility(View.GONE);
                girl.setVisibility(View.VISIBLE);
                loadCustomSizeImage(data.getUrl(), girl, mImageSizeMap);
            } else {
                girl.setVisibility(View.GONE);
                newsContainer.setVisibility(View.VISIBLE);
                title.setText(data.getDesc());
                time.setText(data.getCreatedAt());
                author.setText(data.getWho());
            }
            articleContentItem.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            if (!TextUtils.equals(WELFARE, data.getType())) {
                                Intent intent = new Intent(context, ArticleDetailActivity.class);
                                intent.putExtra(EXTRA_ARTICLE_URL, data.getUrl());
                                intent.putExtra(EXTRA_ARTICLE_TITLE, data.getDesc());
                                intent.putExtra(EXTRA_ARTICLE_TIME, data.getCreatedAt());
                                intent.putExtra(EXTRA_AUTHOR_NAME, data.getWho());
                                List<String> images = data.getImages();
                                if (images != null && !images.isEmpty()) {
                                    intent.putExtra(EXTRA_IMAGE_URL, images.get(0));
                                }
                                intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_GANK);
                                context.startActivity(intent);
                            } else {
                                //TODO:
                            }
                        }
                    });
        }
    }
}
