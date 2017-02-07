package com.hanyee.androidlib.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanyee on 16/11/28.
 */

public class FlowLayout extends ViewGroup {

    // Record all child views
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    // Record every line height
    private List<Integer> mLineHeight = new ArrayList<Integer>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // Actual width/height
        int width = 0;
        int height = 0;

        // For every line width/height
        int lineWidth = 0;
        int lineHeight = 0;
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childViewWidth = child.getMeasuredWidth() + mlp.leftMargin + mlp.rightMargin;
            int childViewHeight = child.getMeasuredHeight() + mlp.topMargin + mlp.bottomMargin;
            // Change to another line
            if (lineWidth + childViewWidth > widthSize - getPaddingLeft() - getPaddingRight()) {
                // Compare the max width and get this layout actual max width
                width = Math.max(width, lineWidth);
                // Reset the line  width
                lineWidth = childViewWidth;
                // Superimpose total height and get this layout actual max height
                height += lineHeight;
                // Reset the line height
                lineHeight = childViewHeight;
                // Still in original line
            } else {
                // Superimpose the line width
                lineWidth += childViewWidth;
                // Compare the line height
                lineHeight = Math.max(lineHeight, childViewHeight);
            }
            // If the last child, compare and get this layout actual max width/height
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }

        setMeasuredDimension(
                widthMode == MeasureSpec.EXACTLY ? widthSize : width + getPaddingLeft() + getPaddingRight(),
                heightMode == MeasureSpec.EXACTLY ? heightSize : height + getPaddingTop() + getPaddingBottom());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            // If change to another line
            if (lineWidth + childWidth + mlp.leftMargin + mlp.rightMargin >
                    width - getPaddingLeft() - getPaddingRight()) {
                // Record previous line height and views
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);

                // Reset the new line width and height
                lineWidth = 0;
                lineHeight = childHeight + mlp.topMargin + mlp.bottomMargin;
                lineViews = new ArrayList<View>();
            }
            // Still in original line
            lineWidth += childWidth + mlp.leftMargin + mlp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + mlp.topMargin + mlp.bottomMargin);
            lineViews.add(child);
        }
        // Record the last line height and views
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        int left = 0;
        int top = 0;
        int lineNum = mAllViews.size();

        // Layout all child views
        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            // For every line views
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == GONE)
                    continue;

                // Get the child left/top/right/bottom
                MarginLayoutParams mlp = (MarginLayoutParams) child.getLayoutParams();
                int leftChild = left + mlp.leftMargin;
                int topChild = top + mlp.topMargin;
                int rightChild = leftChild + child.getMeasuredWidth();
                int bottomChild = topChild + child.getMeasuredHeight();
                // Layout the child view
                child.layout(leftChild, topChild, rightChild, bottomChild);
            }
            // Reset into another line
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
