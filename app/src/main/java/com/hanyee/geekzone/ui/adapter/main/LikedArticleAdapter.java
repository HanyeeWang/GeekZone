package com.hanyee.geekzone.ui.adapter.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanyee.androidlib.base.BaseAdapter;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.model.bean.zhihu.LikeArticleBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;
import com.hanyee.geekzone.ui.activity.zhihu.ColumnListActivity;
import com.hanyee.geekzone.util.Utils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TITLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_AUTHOR_NAME;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_IMAGE_URL;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_COLUMN_ARTICLE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_GANK;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_TIANXIN;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS;


public class LikedArticleAdapter extends BaseAdapter<LikeArticleBean> {

    //item类型
    public static final int ITEM_TYPE_ZHIHU_NEWS = 0;
    public static final int ITEM_TYPE_ZHIHU_COLUMN_AUTHOR = 1;
    public static final int ITEM_TYPE_ZHIHU_COLUMN_ARTICLE = 2;
    public static final int ITEM_TYPE_WECHAT_NEWS = 3;
    public static final int ITEM_TYPE_GANK_NEWS = 4;
    public static final int ITEM_TYPE_COUNT = ITEM_TYPE_GANK_NEWS + 1;

    private int mZhiHuNewsSize;
    private int mColumnAuthorsSize;
    private int mColumnArticlesSize;
    private int mWeChatNewsSize;
    private int mGankNewsSize;

    @Inject
    public LikedArticleAdapter(Fragment fragment) {
        super(fragment);
    }

    public void setData(List<LikeArticleBean> zhiHuNews, List<LikeArticleBean> columnAuthors,
                        List<LikeArticleBean> columnArticles, List<LikeArticleBean> weChatNews,
                        List<LikeArticleBean> gankNews) {
        mData.clear();
        mData.addAll(zhiHuNews);
        mData.addAll(columnAuthors);
        mData.addAll(columnArticles);
        mData.addAll(weChatNews);
        mData.addAll(gankNews);
        mZhiHuNewsSize = zhiHuNews != null ? zhiHuNews.size() : 0;
        mColumnAuthorsSize = columnAuthors != null ? columnAuthors.size() : 0;
        mColumnArticlesSize = columnArticles != null ? columnArticles.size() : 0;
        mWeChatNewsSize = weChatNews != null ? weChatNews.size() : 0;
        mGankNewsSize = gankNews != null ? gankNews.size() : 0;
        notifyDataSetChanged();
    }

    private boolean isZhiHuDailyView(int position) {
        return mZhiHuNewsSize > 0 && position < mZhiHuNewsSize;
    }

    private boolean isZhiHuColumnAuthorView(int position) {
        return mColumnAuthorsSize > 0 && (position >= mZhiHuNewsSize
                && position < (mZhiHuNewsSize + mColumnAuthorsSize));
    }

    private boolean isZhiHuColumnArticleView(int position) {
        return mColumnArticlesSize > 0 && (position >= (mZhiHuNewsSize + mColumnAuthorsSize)
                && position < (mZhiHuNewsSize + mColumnAuthorsSize + mColumnArticlesSize));
    }

    private boolean isWeChatView(int position) {
        return mWeChatNewsSize > 0 && (position >= (mZhiHuNewsSize + mColumnAuthorsSize + mColumnArticlesSize)
                && position < mWeChatNewsSize + mZhiHuNewsSize + mColumnAuthorsSize + mColumnArticlesSize);
    }

    @Override
    public int getItemViewType(int position) {
        if (isZhiHuDailyView(position)) {
            //知乎日报
            return ITEM_TYPE_ZHIHU_NEWS;
        } else if (isZhiHuColumnAuthorView(position)) {
            //知乎专栏作者
            return ITEM_TYPE_ZHIHU_COLUMN_AUTHOR;
        } else if (isZhiHuColumnArticleView(position)) {
            //知乎专栏文章
            return ITEM_TYPE_ZHIHU_COLUMN_ARTICLE;
        } else if (isWeChatView(position)) {
            //微信精选
            return ITEM_TYPE_WECHAT_NEWS;
        } else {
            //干货精选
            return ITEM_TYPE_GANK_NEWS;
        }
    }

    @Override
    protected int getViewTypeCount() {
        return ITEM_TYPE_COUNT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_ZHIHU_NEWS) {
            return new ZhiHuNewsViewHolder(getCreatedView(parent, R.layout.list_item_common));
        } else if (viewType == ITEM_TYPE_ZHIHU_COLUMN_AUTHOR) {
            return new ZhiHuColumnAuthorViewHolder(getCreatedView(parent, R.layout.list_item_column_author));
        } else if (viewType == ITEM_TYPE_ZHIHU_COLUMN_ARTICLE) {
            return new ZhiHuColumnArticleViewHolder(getCreatedView(parent, R.layout.list_item_column_article));
        } else if (viewType == ITEM_TYPE_WECHAT_NEWS) {
            return new WeChatNewsViewHolder(getCreatedView(parent, R.layout.list_item_tianxin_news));
        } else if (viewType == ITEM_TYPE_GANK_NEWS) {
            return new GankNewsViewHolder(getCreatedView(parent, R.layout.list_item_gank_news));
        }
        return null;
    }

    public static class ZhiHuNewsViewHolder extends BaseViewHolder<LikeArticleBean> {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.desc)
        TextView title;
        @BindView(R.id.content_item)
        ViewGroup contentItem;

        public ZhiHuNewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(final LikeArticleBean data) {
            title.setText(data.getTitle());
            loadAvatar(data.getPicUrl(), avatar);
            contentItem.setOnClickListener(new View.OnClickListener() {
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

    public static class ZhiHuColumnAuthorViewHolder extends BaseViewHolder<LikeArticleBean> {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.pop)
        TextView pop;
        @BindView(R.id.info)
        TextView info;
        @BindView(R.id.holderView)
        ViewGroup holderView;

        public ZhiHuColumnAuthorViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(final LikeArticleBean data) {
            name.setText(data.getTitle());
            info.setText(data.getDescription());
            pop.setText(getApplicationContext()
                    .getString(R.string.zhihu_column_author_pop,
                            data.getFollowersCount(),
                            data.getPostsCount()));
            loadCircleAvatar(data.getPicUrl(), avatar);
            holderView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ColumnListActivity.class);
                    intent.putExtra(EXTRA_AUTHOR_NAME, data.getAuthorHref());
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class ZhiHuColumnArticleViewHolder extends BaseViewHolder<LikeArticleBean> {
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

        public ZhiHuColumnArticleViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(final LikeArticleBean data) {
            name.setText(data.getTitle());
            authorTime.setText(articleContentItem.getContext()
                    .getString(R.string.zhihu_post_name_time,
                            data.getAuthorName(), Utils.convertPublishTime(data.getCtime())));
            likeComment.setText(articleContentItem.getContext()
                    .getString(R.string.zhihu_post_like_comment_count,
                            data.getLikeCount(),
                            data.getCommentCount()));
            loadAndCompressImage(data.getPicUrl(), articleImg);
            articleContentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra(EXTRA_ARTICLE_ID, data.getId());
                    intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_COLUMN_ARTICLE);
                    context.startActivity(intent);
                }
            });
        }
    }

    public static class WeChatNewsViewHolder extends BaseViewHolder<LikeArticleBean> {
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

        public WeChatNewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(final LikeArticleBean data) {
            title.setText(data.getTitle());
            desc.setText(data.getDescription());
            createTime.setText(data.getCtime());
            loadAndCompressImage(data.getPicUrl(), articleImg);
            articleContentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra(EXTRA_ARTICLE_URL, data.getArticleUrl());
                    intent.putExtra(EXTRA_IMAGE_URL, data.getPicUrl());
                    intent.putExtra(EXTRA_ARTICLE_TITLE, data.getTitle());
                    intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_TIANXIN);
                    context.startActivity(intent);
                }
            });

        }
    }

    public static class GankNewsViewHolder extends BaseViewHolder<LikeArticleBean> {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.authorHref)
        TextView authorHref;
        @BindView(R.id.article_content_item)
        ViewGroup articleContentItem;

        public GankNewsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(final LikeArticleBean data) {
            title.setText(data.getTitle());
            authorHref.setText(data.getAuthorName());
            time.setText(data.getCtime());
            articleContentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArticleDetailActivity.class);
                    intent.putExtra(EXTRA_ARTICLE_URL, data.getArticleUrl());
                    intent.putExtra(EXTRA_IMAGE_URL, data.getPicUrl());
                    intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_GANK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
