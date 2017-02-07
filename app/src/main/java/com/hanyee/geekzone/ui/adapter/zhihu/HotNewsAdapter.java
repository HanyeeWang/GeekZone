package com.hanyee.geekzone.ui.adapter.zhihu;

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
import com.hanyee.geekzone.model.bean.zhihu.HotNewsBean.RecentBean;
import com.hanyee.geekzone.ui.activity.main.ArticleDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_ARTICLE_TYPE;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_TYPE_ZHIHU_NEWS;

public class HotNewsAdapter extends BaseAdapter<RecentBean> {

    @Inject
   public HotNewsAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HotNewsViewHolder(getCreatedView(parent, R.layout.list_item_common));
    }

    public static class HotNewsViewHolder extends BaseViewHolder<RecentBean> {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.content_item)
        ViewGroup hotContentView;

        public HotNewsViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData2View(final RecentBean data) {
            desc.setText(data.getTitle());
            loadAvatar(data.getThumbnail(), avatar);
            hotContentView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ArticleDetailActivity.class);
                            intent.putExtra(EXTRA_ARTICLE_ID, data.getNews_id());
                            intent.putExtra(EXTRA_ARTICLE_TYPE, EXTRA_TYPE_ZHIHU_NEWS);
                            context.startActivity(intent);
                        }
                    });
        }

    }
}
