package com.hanyee.geekzone.ui.adapter.zhihu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanyee.androidlib.base.BaseAdapter;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.model.bean.zhihu.StoriesBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS;

public class ThemeListAdapter extends BaseAdapter<StoriesBean> {

    @Inject
    public ThemeListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThemeListViewHolder(getCreatedView(parent, R.layout.list_item_common));
    }

    public static class ThemeListViewHolder extends BaseViewHolder<StoriesBean> {

        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.content_item)
        ViewGroup hotContentView;

        public ThemeListViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData2View(final StoriesBean data) {
            desc.setText(data.getTitle());
            if (data.getImages() != null
                    && data.getImages().size() > 0) {
                avatar.setVisibility(View.VISIBLE);
                loadAvatar(data.getImages().get(0), avatar);
            } else {
                avatar.setVisibility(View.GONE);
            }
            hotContentView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ArticleDetailActivity.class);
                            intent.putExtra(EXTRA_ARTICLE_ID, data.getId());
                            intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_ZHIHU_NEWS);
                            context.startActivity(intent);
                        }
                    });
        }

    }
}
