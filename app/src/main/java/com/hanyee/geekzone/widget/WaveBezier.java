package com.hanyee.geekzone.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import static android.animation.ValueAnimator.INFINITE;
import static com.hanyee.geekzone.util.Constants.WAVE_FLOW_ANIMATION_TIME;
import static com.hanyee.geekzone.util.Constants.WAVE_RISE_ANIMATION_TIME;

/**
 * 波浪图形
 * Created by Hanyee on 17/4/1.
 */
public class WaveBezier extends LinearLayout {

    private Paint mDarkerPaint;
    private Paint mLightPaint;
    private Path mDarkerPath;
    private Path mLightPath;
    private AnimatorSet mAnimatorSet;
    private ValueAnimator mFlowAnimator;
    private ValueAnimator mRiseAnimator;
    private ValueAnimator mDownAnimator;

    private int mWaveLength = 2000;
    private int mWaveHeight = 30;
    private int mFlowOffset;
    private int mRiseOffset;
    private int mWaveViewHeight;
    private int mWaveViewWidth;
    private int mWaveCount;
    private int mWaveY;

    private boolean mDrawWaveAnimation;

    public WaveBezier(Context context) {
        this(context, null);
    }

    public WaveBezier(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDarkerPath = new Path();
        mLightPath = new Path();
        mDarkerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDarkerPaint.setColor(Color.parseColor("#77512DA8"));
        mDarkerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLightPaint.setColor(Color.parseColor("#55512DA8"));
        mLightPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWaveViewHeight = h;
        mWaveViewWidth = w;
        mWaveCount = (int) Math.round(mWaveViewWidth / mWaveLength + 1.5);
        mWaveY = mWaveViewHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mDrawWaveAnimation)
            return;

        mDarkerPath.reset();
        mLightPath.reset();
        mDarkerPath.moveTo(-mWaveLength + mFlowOffset, mWaveY - mRiseOffset);
        mLightPath.moveTo(-mWaveLength + mFlowOffset, mWaveY - mRiseOffset);
        for (int i = 0; i < mWaveCount; i++) {
            mLightPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mFlowOffset, mWaveY - mWaveHeight - mRiseOffset,
                    (-mWaveLength / 2) + (i * mWaveLength) + mFlowOffset, mWaveY - mRiseOffset);
            mLightPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mFlowOffset, mWaveY + mWaveHeight - mRiseOffset,
                    i * mWaveLength + mFlowOffset, mWaveY - mRiseOffset);

            mDarkerPath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mFlowOffset, mWaveY + mWaveHeight - mRiseOffset,
                    (-mWaveLength / 2) + (i * mWaveLength) + mFlowOffset, mWaveY - mRiseOffset);
            mDarkerPath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mFlowOffset, mWaveY - mWaveHeight - mRiseOffset,
                    i * mWaveLength + mFlowOffset, mWaveY - mRiseOffset);
        }
        mLightPath.lineTo(mWaveViewWidth, mWaveViewHeight);
        mLightPath.lineTo(0, mWaveViewHeight);
        mLightPath.close();
        mDarkerPath.lineTo(mWaveViewWidth, mWaveViewHeight);
        mDarkerPath.lineTo(0, mWaveViewHeight);
        mDarkerPath.close();
        canvas.drawPath(mLightPath, mLightPaint);
        canvas.drawPath(mDarkerPath, mDarkerPaint);
    }

    public void startAnimation() {
        if (mFlowAnimator == null) {
            mFlowAnimator = ValueAnimator.ofInt(0, mWaveLength);
            mFlowAnimator.setDuration(WAVE_FLOW_ANIMATION_TIME);
            mFlowAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mFlowAnimator.setInterpolator(new LinearInterpolator());
            mFlowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mFlowOffset = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
        }

        if (mRiseAnimator == null) {
            mRiseAnimator = ValueAnimator.ofInt(0, mWaveViewHeight);
            mRiseAnimator.setDuration(WAVE_RISE_ANIMATION_TIME);
            mRiseAnimator.setInterpolator(new LinearInterpolator());
            mRiseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRiseOffset = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
        }

        if (mDownAnimator == null) {
            mDownAnimator = ValueAnimator.ofInt(mWaveViewHeight, 0);
            mDownAnimator.setDuration(WAVE_RISE_ANIMATION_TIME);
            mDownAnimator.setInterpolator(new LinearInterpolator());
            mDownAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRiseOffset = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
        }

        mDrawWaveAnimation = true;
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(mFlowAnimator, mRiseAnimator);
        mAnimatorSet.start();
    }

    public void finishAnimation() {
        if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
            mAnimatorSet.end();
        }
    }
}
