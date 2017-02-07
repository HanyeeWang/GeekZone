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
import com.hanyee.geekzone.model.bean.zhihu.AuthorBean;
import com.hanyee.geekzone.model.bean.zhihu.RecommendAuthorBean;
import com.hanyee.geekzone.ui.activity.zhihu.ColumnListActivity;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_AUTHOR_NAME;

public class ColumnsAdapter extends BaseAdapter<RecommendAuthorBean> {

    @Inject
    public ColumnsAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColumnAuthorsViewHolder(getCreatedView(parent, R.layout.list_item_column_author));
    }

    public static class ColumnAuthorsViewHolder extends BaseViewHolder<RecommendAuthorBean> {

        @BindView(R.id.holderView)
        ViewGroup holderView;
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.pop)
        TextView pop;
        @BindView(R.id.info)
        TextView intro;
        @BindView(R.id.name)
        TextView name;

        public ColumnAuthorsViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindData2View(final RecommendAuthorBean data) {
            name.setText(data.getName());
            pop.setText(getApplicationContext().getString(R.string.zhihu_column_author_pop,
                    data.getFollowersCount(),
                    data.getPostsCount()));
            intro.setText(data.getDescription());
            AuthorBean.AvatarBean avatarInfo = data.getAvatar();
            loadCircleAvatar(avatarInfo.getTemplate().replace("{id}", avatarInfo.getId()).replace("{size}", "m"), avatar);
            holderView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ColumnListActivity.class);
                            intent.putExtra(EXTRA_AUTHOR_NAME, data.getUrl().substring(1));
                            context.startActivity(intent);
                        }
                    });
        }

    }
}
