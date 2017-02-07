package com.lcodecore.tkrefreshlayout.header.bezierlayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.R;

/**
 * Created by lcodecore on 2016/10/2.
 */

public class BezierLayout extends FrameLayout implements IHeaderView {
    public BezierLayout(Context context) {
        this(context, null);
    }

    public BezierLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    View headView;
    WaveView waveView;
    RippleView rippleView;
    RoundDotView r1;
    RoundProgressView r2;

    private void init(AttributeSet attrs) {
        /**
         * attrs  需要在xml设置什么属性  自己自定义吧  啊哈哈
         */

        /**
         * 初始化headView
         */
        headView = LayoutInflater.from(getContext()).inflate(R.layout.view_bezier, null);
        waveView = (WaveView) headView.findViewById(R.id.draweeView);
        rippleView = (RippleView) headView.findViewById(R.id.ripple);
        r1 = (RoundDotView) headView.findViewById(R.id.round1);
        r2 = (RoundProgressView) headView.findViewById(R.id.round2);
        r2.setVisibility(View.GONE);

        rippleView.setRippleListener(new RippleView.RippleListener() {
            @Override
            public void onRippleFinish() {
                //执行一个自定义动画
            }
        });

        addView(headView);
    }

    /**
     * 限定值
     *
     * @param a
     * @param b
     * @return
     */
    public float limitValue(float a, float b) {
        float valve = 0;
        final float min = Math.min(a, b);
        final float max = Math.max(a, b);
        valve = valve > min ? valve : min;
        valve = valve < max ? valve : max;
        return valve;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        waveView.setHeadHeight((int) (headHeight * limitValue(1, fraction)));
        waveView.setWaveHeight((int) (maxHeadHeight * Math.max(0, fraction - 1)));
        waveView.invalidate();

                /*处理圈圈**/
        r1.setCir_x((int) (30 * limitValue(1, fraction)));
        r1.setVisibility(View.VISIBLE);
        r1.invalidate();

        r2.setVisibility(View.GONE);
        r2.animate().scaleX((float) 0.1);
        r2.animate().scaleY((float) 0.1);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        r1.setCir_x((int) (30 * limitValue(1, fraction)));
        r1.invalidate();
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        waveView.setHeadHeight((int) headHeight);
        ValueAnimator animator = ValueAnimator.ofInt(waveView.getWaveHeight(), 0, -300, 0, -100, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                        Log.i("anim", "value--->" + (int) animation.getAnimatedValue());
                waveView.setWaveHeight((int) animation.getAnimatedValue() / 2);
                waveView.invalidate();

            }
        });
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(800);
        animator.start();
        /*处理圈圈进度条**/
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                r1.setVisibility(GONE);
                r2.setVisibility(View.VISIBLE);
                r2.animate().scaleX((float) 1.0);
                r2.animate().scaleY((float) 1.0);
                r2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //r2.setAnimStart();
                        r2.startAnim();
                    }
                }, 200);
            }
        });

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                r1.setCir_x((int) (-value * 40));
                r1.invalidate();
            }

        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }

    @Override
    public void onFinish() {
        r2.stopAnim();
        r2.animate().scaleX((float) 0.0);
        r2.animate().scaleY((float) 0.0);
        rippleView.startReveal();
    }
}
