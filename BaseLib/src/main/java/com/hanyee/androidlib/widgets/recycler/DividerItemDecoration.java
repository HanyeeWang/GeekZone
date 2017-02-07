package com.hanyee.androidlib.widgets.recycler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.hanyee.androidlib.R;

/**
 * Created by Hanyee on 15/10/27.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{
            R.styleable.BaseRecyclerView_Divider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    /**
     * item之间分割线的size，默认为0.5
     */
    private float mItemSize = 0.5f;

    private Drawable mDivider;

    private int mOrientation;

    /**
     * 绘制item分割线的画笔，和设置其属性
     * 来绘制个性分割线
     */
    private Paint mPaint;

    public DividerItemDecoration(Context context, TypedArray array) {
        initDividerRes(context, array);
        setOrientation(VERTICAL_LIST);
    }

    public DividerItemDecoration(Context context, int orientation, TypedArray array) {
        initDividerRes(context, array);
        setOrientation(orientation);
    }

    private void initDividerRes(Context context, TypedArray array) {
        mDivider = array.getDrawable(0);
        if ((mDivider == null) || (mDivider instanceof ColorDrawable)) {
            mDivider = null;
            initPaint(array.getColor(0, Color.GRAY));
        } else {
            mItemSize = mDivider.getIntrinsicHeight();
        }
        mItemSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mItemSize,
                context.getResources().getDisplayMetrics());
    }

    private void initPaint(int color) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {

        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }


    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final float bottom = top + mItemSize;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, (int) bottom);
                mDivider.draw(c);
            } else {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }


    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final float right = left + mItemSize;
            if (mDivider != null) {
                mDivider.setBounds(left, top, (int) right, bottom);
                mDivider.draw(c);
            } else {
                c.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, (int) mItemSize);
        } else {
            outRect.set(0, 0, (int) mItemSize, 0);
        }
    }
}
