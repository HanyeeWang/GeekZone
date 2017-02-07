package com.hanyee.androidlib.widgets.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.hanyee.androidlib.R;


/**
 * Created by Hanyee on 15/10/27.
 */
public class BaseRecyclerView extends RecyclerView {

    private View emptyView;

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    BaseRecyclerView.this.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    BaseRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initDivider(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaseRecyclerView);
        Drawable drawable = array.getDrawable(R.styleable.BaseRecyclerView_Divider);
        if (drawable != null)
            this.addItemDecoration(new DividerItemDecoration(context, array));
        array.recycle();
    }

    public BaseRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDivider(context, attrs);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }

        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
