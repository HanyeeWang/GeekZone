package com.hanyee.androidlib.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.hanyee.androidlib.R;
import com.hanyee.androidlib.cache.image.ImageUtils;
import com.hanyee.androidlib.cache.image.glide.CircleTransform;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by Hanyee on 16/11/21.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int DEFAULT_VIEW_TYPE_COUNT = 1;

    protected List<T> mData = new ArrayList<>();

    private Object mContext;
    private boolean mIsScrollStateFling;

    public BaseAdapter(Object context) {
        mContext = context;
    }

    public void setData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        mData.addAll(data);
        notifyItemRangeChanged(mData.size() - data.size(), mData.size());
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setScrollStateFling(boolean scrollState) {
        mIsScrollStateFling = scrollState;
    }

    protected View getCreatedView(ViewGroup parent, int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        baseViewHolder.setContext(mContext);
        baseViewHolder.setScrollState(mIsScrollStateFling);
        if (getViewTypeCount() == 1) {
            baseViewHolder.onBindData2View(mData.get(position));
        } else {
            int type = getItemViewType(position);
            onBindMultipliedTypeView(baseViewHolder, type, position);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected int getViewTypeCount() {
        return DEFAULT_VIEW_TYPE_COUNT;
    }

    protected void onBindMultipliedTypeView(BaseViewHolder holder, int type, int position) {
        holder.onBindData2View(mData.get(position));
    }

    public abstract static class BaseViewHolder<T> extends RecyclerView.ViewHolder {

        private View mView;
        private Object mContext;
        private boolean mScrollStateFling;

        public BaseViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

        protected Context getApplicationContext() {
            return mView.getContext().getApplicationContext();
        }

        private RequestManager getRequestManager() {
            RequestManager requestManager = null;
            if (mContext instanceof Activity) {
                requestManager = Glide.with((Activity) mContext);
            } else if (mContext instanceof Fragment) {
                requestManager = Glide.with((Fragment) mContext);
            } else {
                requestManager = Glide.with((Context) mContext);
            }
            return requestManager;
        }

        protected void loadAvatar(String url, ImageView view) {
            getRequestManager()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_default_image)
                    .fitCenter()
                    .crossFade()
                    .into(view);
        }

        protected void loadCircleAvatar(String url, ImageView view) {
            getRequestManager()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CircleTransform(getApplicationContext()))
                    .error(R.drawable.ic_default_image)
                    .crossFade()
                    .into(view);
        }

        protected void loadCustomSizeImage(String url, ImageView view, Map<String, ImageSize> map) {
            final ImageSize imageSize;
            if (map.get(url) == null) {
                imageSize = new ImageSize();
                map.put(url, imageSize);
            } else {
                imageSize = map.get(url);
            }
            getRequestManager()
                    .load(url)
                    .asBitmap()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_default_image)
                    .skipMemoryCache(true)
                    .into(new BitmapImageViewTarget(view) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (imageSize.isNull()) {
                                int viewWidth = view.getWidth();
                                float scale = resource.getWidth() / (viewWidth * 1.0f);
                                int viewHeight = (int) (resource.getHeight() / scale);
                                ViewGroup.LayoutParams vlp = view.getLayoutParams();
                                vlp.width = viewWidth;
                                vlp.height = viewHeight;
                                view.setLayoutParams(vlp);
                                imageSize.setWidth(viewWidth);
                                imageSize.setHeight(viewHeight);
                            } else {
                                ViewGroup.LayoutParams vlp = view.getLayoutParams();
                                vlp.width = imageSize.getWidth();
                                vlp.height = imageSize.getHeight();
                                view.setLayoutParams(vlp);
                            }
                            super.onResourceReady(resource, glideAnimation);
                        }
                    });
        }

        protected void loadAndCompressImage(String url, ImageView view) {
            getRequestManager()
                    .load(url)
                    .downloadOnly(new ImageViewTarget<File>(view) {
                        @Override
                        protected void setResource(File resource) {
                            Bitmap bitmap = ImageUtils.resizeBitmap(resource, view.getWidth(), view.getHeight());
                            view.setImageBitmap(bitmap);
                        }
                    });
        }

        private void setContext(Object context) {
            mContext = context;
        }

        public void setScrollState(boolean scrollState) {
            mScrollStateFling = scrollState;
        }

        public abstract void onBindData2View(T data);
    }

    public static class ImageSize {

        private int width;
        private int height;

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public boolean isNull() {
            return width == 0 || height == 0;
        }
    }
}
