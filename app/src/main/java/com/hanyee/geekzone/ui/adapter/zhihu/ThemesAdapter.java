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
import com.hanyee.geekzone.model.bean.zhihu.ThemesBean.OthersBean;
import com.hanyee.geekzone.ui.activity.zhihu.ThemeListActivity;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;

import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_THEME_ID;
import static com.hanyee.geekzone.util.Constants.ARTICLE.EXTRA_THEME_NAME;

public class ThemesAdapter extends BaseAdapter<OthersBean> {

    protected Map<String, ImageSize> mImageSizesMap;

    @Inject
    public ThemesAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        for (OthersBean item : mData) {
            if (mImageSizesMap == null) {
                mImageSizesMap = new HashMap<>();
            }
            mImageSizesMap.put(item.getThumbnail(), new ImageSize());
        }
        return new ThemeViewHolder(getCreatedView(parent, R.layout.water_fall_list_item), mImageSizesMap);
    }

    public static class ThemeViewHolder extends BaseViewHolder<OthersBean> {

        @BindView(R.id.holderView)
        ViewGroup holderView;
        @BindView(R.id.avatar)
        ImageView imageView;
        @BindView(R.id.desc)
        TextView desc;
        @BindView(R.id.title)
        TextView title;

        private Map<String, ImageSize> mImageSizeMap;

        public ThemeViewHolder(View view, Map<String, ImageSize> map) {
            super(view);
            mImageSizeMap = map;
        }

        @Override
        public void onBindData2View(final OthersBean data) {
            title.setText(data.getName());
            desc.setText(data.getDescription());
            loadCustomSizeImage(data.getThumbnail(), imageView, mImageSizeMap);
            holderView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, ThemeListActivity.class);
                            intent.putExtra(EXTRA_THEME_ID, data.getId());
                            intent.putExtra(EXTRA_THEME_NAME, data.getName());
                            context.startActivity(intent);
                        }
                    });
        }
    }
}
