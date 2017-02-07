/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hanyee.geekzone.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RatioImageView extends ImageView {

    private int mOriginalWidth;
    private int mOriginalHeight;
    private int mSetWidth;
    private int mSetHeight;
    private Bitmap mBitmap;

    public RatioImageView(Context context) {
        super(context);
    }


    public RatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public RatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setOriginalSize(int originalWidth, int originalHeight) {
        mOriginalWidth = originalWidth;
        mOriginalHeight = originalHeight;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOriginalWidth > 0 && mOriginalHeight > 0) {
            float ratio = (float) mOriginalWidth / (float) mOriginalHeight;

            mSetWidth = MeasureSpec.getSize(widthMeasureSpec);
            mSetHeight = MeasureSpec.getSize(heightMeasureSpec);
            // TODO: 现在只支持固定宽度
            if (mSetWidth > 0) {
                mSetHeight = (int) ((float) mSetWidth / ratio);
            }

            setMeasuredDimension(mSetWidth, mSetHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * Called from layout when this view should
     * assign a size and position to each of its children.
     * <p>
     * Derived classes with children should override
     * this method and call layout on each of
     * their children.
     *
     * @param changed This is a new size or position for this view
     * @param left    Left position, relative to parent
     * @param top     Top position, relative to parent
     * @param right   Right position, relative to parent
     * @param bottom  Bottom position, relative to parent
     */
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        int width = right - left;
//        int height = bottom - top;
//        if (mBitmap != null) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            super.setImageBitmap(ImageUtils.compressBitmap(baos.toByteArray(), width, height));
//        }
//    }
//
//    @Override
//    public void setImageBitmap(Bitmap bitmap) {
//        mBitmap = bitmap;
//        invalidate();
//    }
}
