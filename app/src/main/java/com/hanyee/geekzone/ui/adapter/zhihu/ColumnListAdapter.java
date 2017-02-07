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
import com.hanyee.geekzone.model.bean.zhihu.ColumnsBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;
import com.hanyee.geekzone.util.Utils;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_COLUMN_ARTICLE;


public class ColumnListAdapter extends BaseAdapter<ColumnsBean> {

    @Inject
    public ColumnListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColumnsViewHolder(getCreatedView(parent, R.layout.list_item_column_article));
    }

    public static class ColumnsViewHolder extends BaseViewHolder<ColumnsBean> {
        @BindView(R.id.article_img)
        ImageView articleImg;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.author_time)
        TextView authorTime;
        @BindView(R.id.like_comment)
        TextView likeComment;
        @BindView(R.id.article_content_item)
        ViewGroup articleContentItem;

        public ColumnsViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData2View(final ColumnsBean data) {
            name.setText(data.getTitle());
            authorTime.setText(getApplicationContext().getString(R.string.zhihu_post_name_time,
                    data.getAuthor().getName(),
                    Utils.convertPublishTime(data.getPublishedTime())));
            likeComment.setText(getApplicationContext().getString(R.string.zhihu_post_like_comment_count,
                    data.getLikesCount(),
                    data.getCommentsCount()));
            loadAndCompressImage(data.getTitleImage(), articleImg);
            articleContentItem.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ArticleDetailActivity.class);
                            String href = data.getHref();
                            intent.putExtra(EXTRA_ARTICLE_ID, Integer.valueOf(href.substring(href.lastIndexOf('/') + 1)));
                            intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_COLUMN_ARTICLE);
                            context.startActivity(intent);
                        }
                    });
        }

    }
}
