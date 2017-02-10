/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hanyee.androidlib.cache.image.glide;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.hanyee.androidlib.cache.image.ImageUtils;

public class WaterFallTransform implements Transformation<byte[]> {

    private Context mContext;
    private int mDesiredWidth;
    private int mDesiredHeight;

    public WaterFallTransform(Context context, int width, int height) {
        super();
        mContext = context;
        mDesiredWidth = width;
        mDesiredHeight = height;
    }

    @Override
    public Resource<byte[]> transform(Resource<byte[]> resource, int outWidth, int outHeight) {
        //        float ratio = toTransform.getWidth() / (mDesiredWidth * 1f);
//        mDesiredHeight = (int) (toTransform.getHeight() / ratio);
        Bitmap bitmap = ImageUtils.compressBitmap(resource.get(), mDesiredWidth, mDesiredHeight);
        if (bitmap != null) {
//            VolleyImageLoader.getInstance(mContext).getImageLoader().
        }
        return null;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
