package com.hanyee.geekzone.ui.adapter.zhihu;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.hanyee.androidlib.base.BaseAdapter;
import com.hanyee.geekzone.R;
import com.hanyee.geekzone.model.bean.zhihu.NewsDailyBean;
import com.hanyee.geekzone.model.bean.zhihu.NewsDailyBean.TopStoriesBean;
import com.hanyee.geekzone.model.bean.zhihu.StoriesBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;
import com.hanyee.geekzone.widget.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS;

public class DailyNewsAdapter extends BaseAdapter<StoriesBean> {

    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    public static final int ITEM_TYPE_COUNT = ITEM_TYPE_BOTTOM + 1;

    private List<TopStoriesBean> mTops = new ArrayList<>();
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数

    @Inject
    public DailyNewsAdapter(Fragment fragment) {
        super(fragment);
    }

    public void setData(NewsDailyBean data) {
        mData.clear();
        mTops.clear();
        mData.addAll(data.getStories());
        mTops.addAll(data.getTop_stories());
        notifyDataSetChanged();
    }

    //内容长度
    public int getContentItemCount() {
        return mData.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (isBottomView(position)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CONTENT) {
            return new ContentViewHolder(getCreatedView(parent, R.layout.list_item_common));
        } else if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(getCreatedView(parent, R.layout.header_item));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(getCreatedView(parent, R.layout.footer_item));
        }
        return null;
    }

    @Override
    protected void onBindMultipliedTypeView(BaseViewHolder holder, int type, int position) {
        if (type == ITEM_TYPE_HEADER) {
            holder.onBindData2View(mTops);
        } else if (type == ITEM_TYPE_CONTENT) {
            holder.onBindData2View(mData.get(position - 1));
        } else if (type == ITEM_TYPE_BOTTOM) {
        }
    }

    @Override
    protected int getViewTypeCount() {
        return ITEM_TYPE_COUNT;
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    //头部 ViewHolder
    public static class HeaderViewHolder extends BaseViewHolder<List<TopStoriesBean>> {
        @BindView(R.id.convenientBanner)
        ConvenientBanner convenientBanner;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(List<TopStoriesBean> data) {
            convenientBanner
                    .setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                        @Override
                        public NetworkImageHolderView createHolder() {
                            return new NetworkImageHolderView();
                        }
                    }, data)
                    .setPageIndicator(new int[]{R.drawable.ic_page_indicator,
                            R.drawable.ic_page_indicator_focused})
                    .setPageTransformer(ConvenientBanner.Transformer.AccordionTransformer)
                    .startTurning(5000);
        }
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends BaseViewHolder<StoriesBean> {
        @BindView(R.id.content_item)
        ViewGroup container;
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.desc)
        TextView desc;

        public ContentViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(final StoriesBean data) {
            desc.setText(data.getTitle());
            loadAvatar(data.getImages().get(0), avatar);
            container.setOnClickListener(new View.OnClickListener() {
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

    //底部 ViewHolder
    public static class BottomViewHolder extends BaseViewHolder<StoriesBean> {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindData2View(StoriesBean data) {
        }
    }
}
