package com.hanyee.geekzone.ui.adapter.tianxin;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanyee.geekzone.R;
import com.hanyee.androidlib.base.BaseAdapter;
import com.hanyee.geekzone.model.bean.tianxin.TianXinNewsBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_DESC;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TIME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TITLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_IMAGE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_TIANXIN;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_TIANXIN_TYPE;


public class TianXinNewsAdapter extends BaseAdapter<TianXinNewsBean> {

    private String mTianXinType;

    @Inject
    public TianXinNewsAdapter(Fragment fragment) {
        super(fragment);
    }

    public void setTianXinType(String tianXinType) {
        mTianXinType = tianXinType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TianXinNewsViewHolder(
                getCreatedView(parent, R.layout.list_item_tianxin_news), mTianXinType);
    }

    public static class TianXinNewsViewHolder extends BaseViewHolder<TianXinNewsBean> {
        @BindView(R.id.article_img)
        ImageView articleImg;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.create_time)
        TextView createTime;
        @BindView(R.id.article_content_item)
        ViewGroup articleContentItem;

        private String tianXinType;

        public TianXinNewsViewHolder(View view, String type) {
            super(view);
            tianXinType = type;
        }

        @Override
        public void onBindData2View(final TianXinNewsBean data) {
            title.setText(data.getTitle());
            desc.setText(data.getDescription());
            createTime.setText(data.getCtime());
            loadAndCompressImage(data.getPicUrl(), articleImg);
            articleContentItem.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ArticleDetailActivity.class);
                            intent.putExtra(EXTRA_ARTICLE_URL, data.getUrl());
                            intent.putExtra(EXTRA_IMAGE_URL, data.getPicUrl());
                            intent.putExtra(EXTRA_ARTICLE_TITLE, data.getTitle());
                            intent.putExtra(EXTRA_ARTICLE_DESC, data.getDescription());
                            intent.putExtra(EXTRA_ARTICLE_TIME, data.getCtime());
                            intent.putExtra(EXTRA_TYPE_TIANXIN_TYPE, tianXinType);
                            intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_TIANXIN);
                            context.startActivity(intent);
                        }
                    });
        }
    }
}
