package com.lcodecore.tkrefreshlayout;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.lcodecore.tkrefreshlayout.Footer.BottomProgressView;
import com.lcodecore.tkrefreshlayout.header.GoogleDotView;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;
import com.lcodecore.tkrefreshlayout.utils.ScrollingUtil;

/**
 * Created by lcodecore on 16/3/2.
 */
public class TwinklingRefreshLayout extends FrameLayout {

    private static final int PULL_DOWN_REFRESH = 1;//标志当前进入的刷新模式
    private static final int PULL_UP_LOAD = 2;
    private int state = PULL_DOWN_REFRESH;

    //波浪的高度,最大扩展高度
    protected float mWaveHeight;

    //头部的高度
    protected float mHeadHeight;

    //允许的越界回弹的高度
    protected float mOverScrollHeight;

    //子控件
    private View mChildView;

    //头部layout
    protected FrameLayout mHeadLayout;

    private IHeaderView mHeadView;
    private IBottomView mBottomView;

    //底部高度
    private float mBottomHeight;

    //底部layout
    private FrameLayout mBottomLayout;


    //刷新的状态
    protected boolean isRefreshing;

    //加载更多的状态
    protected boolean isLoadingmore;

    //是否需要加载更多,默认需要
    protected boolean enableLoadmore = true;
    //是否需要下拉刷新,默认需要
    protected boolean enableRefresh = true;

    //是否在越界回弹的时候显示下拉图标
    protected boolean isOverlayRefreshShow = true;

    //是否隐藏刷新控件,开启越界回弹模式(开启之后刷新控件将隐藏)
    protected boolean isPureScrollModeOn = false;

    //触摸获得Y的位置
    private float mTouchY;
    //触摸获得X的位置(为防止滑动冲突而设置)
    private float mTouchX;

    //动画的变化率
    private DecelerateInterpolator decelerateInterpolator;


    public TwinklingRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public TwinklingRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwinklingRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TwinklingRefreshLayout, defStyleAttr, 0);
        mWaveHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_wave_height, (int) DensityUtil.dp2px(context, 120));
        mHeadHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_head_height, (int) DensityUtil.dp2px(context, 80));
        mBottomHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_bottom_height, (int) DensityUtil.dp2px(context, 60));
        mOverScrollHeight = a.getDimensionPixelSize(R.styleable.TwinklingRefreshLayout_tr_overscroll_height, (int) DensityUtil.dp2px(context, 80));
        enableLoadmore = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_enable_loadmore, true);
        isPureScrollModeOn = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_pureScrollMode_on, false);
        isOverlayRefreshShow = a.getBoolean(R.styleable.TwinklingRefreshLayout_tr_show_overlay_refreshview, true);
        a.recycle();
    }

    private void init() {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) return;
        if (getChildCount() > 1)
            throw new RuntimeException("Only one childView is supported. 只能拥有一个子控件哦。");

        //在动画开始的地方快然后慢;
        decelerateInterpolator = new DecelerateInterpolator(10);
        setPullListener(new SimplePullListener());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        //添加头部
        if (mHeadLayout == null) {
            FrameLayout headViewLayout = new FrameLayout(getContext());
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layoutParams.gravity = Gravity.TOP;
            headViewLayout.setLayoutParams(layoutParams);

            mHeadLayout = headViewLayout;

            this.addView(mHeadLayout);//addView(view,-1)添加到-1的位置

            if (mHeadView == null) setHeaderView(new GoogleDotView(getContext()));
        }

        //添加底部
        if (mBottomLayout == null) {
            FrameLayout bottomViewLayout = new FrameLayout(getContext());
            LayoutParams layoutParams2 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            layoutParams2.gravity = Gravity.BOTTOM;
            bottomViewLayout.setLayoutParams(layoutParams2);

            mBottomLayout = bottomViewLayout;
            this.addView(mBottomLayout);

            if (mBottomView == null) {
                BottomProgressView mProgressView = new BottomProgressView(getContext());
                setBottomView(mProgressView);
            }
        }

        if (isPureScrollModeOn) {
            setEnableOverlayRefreshView(false);
            if (mHeadLayout != null) mHeadLayout.setVisibility(GONE);
            if (mBottomLayout != null) mBottomLayout.setVisibility(GONE);
        } else {
            setEnableOverlayRefreshView(true);
            if (mHeadLayout != null) mHeadLayout.setVisibility(VISIBLE);
            if (mBottomLayout != null) mBottomLayout.setVisibility(VISIBLE);
        }

        //获得子控件
        mChildView = getChildAt(0);
        if (mChildView == null) return;
        mChildView.animate().setInterpolator(new DecelerateInterpolator());
        handleScrollEvent();
    }

    /*************************************  触摸事件处理  *****************************************/
    /**
     * 拦截事件
     *
     * @return return true时,ViewGroup的事件有效,执行onTouchEvent事件
     * return false时,事件向下传递,onTouchEvent无效
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchX = ev.getX();
                mTouchY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = ev.getX() - mTouchX;
                float dy = ev.getY() - mTouchY;
                if (Math.abs(dx) <= Math.abs(dy)) {//滑动允许最大角度为45度
                    if (dy > 0 && !ScrollingUtil.canChildScrollUp(mChildView) && enableRefresh) {
                        state = PULL_DOWN_REFRESH;
                        return true;
                    } else if (dy < 0 && !ScrollingUtil.canChildScrollDown(mChildView) && enableLoadmore) {
                        state = PULL_UP_LOAD;
                        return true;
                    }
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRefreshing || isLoadingmore) return super.onTouchEvent(e);

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = e.getY() - mTouchY;

                if (state == PULL_DOWN_REFRESH) {
                    dy = Math.min(mWaveHeight * 2, dy);
                    dy = Math.max(0, dy);

                    if (mChildView != null) {
                        float offsetY = decelerateInterpolator.getInterpolation(dy / mWaveHeight / 2) * dy / 2;
                        mChildView.setTranslationY(offsetY);

                        mHeadLayout.getLayoutParams().height = (int) offsetY;
                        mHeadLayout.requestLayout();

                        if (pullListener != null) {
                            pullListener.onPullingDown(TwinklingRefreshLayout.this, offsetY / mHeadHeight);
                        }
                    }
                } else if (state == PULL_UP_LOAD) {
                    //加载更多的动作
                    dy = Math.min(mBottomHeight * 2, Math.abs(dy));
                    dy = Math.max(0, dy);
                    if (mChildView != null) {
                        float offsetY = -decelerateInterpolator.getInterpolation(dy / mBottomHeight / 2) * dy / 2;
                        mChildView.setTranslationY(offsetY);

                        mBottomLayout.getLayoutParams().height = (int) -offsetY;
                        mBottomLayout.requestLayout();

                        if (pullListener != null) {
                            pullListener.onPullingUp(TwinklingRefreshLayout.this, offsetY / mHeadHeight);
                        }
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mChildView != null) {
                    if (state == PULL_DOWN_REFRESH) {
                        if (!isPureScrollModeOn && mChildView.getTranslationY() >= mHeadHeight - mTouchSlop) {
                            animChildView(mHeadHeight);//回到限制的最大高度处
                            isRefreshing = true;
                            if (pullListener != null) {
                                pullListener.onRefresh(TwinklingRefreshLayout.this);
                            }
                        } else {
                            animChildView(0f);
                        }
                    } else if (state == PULL_UP_LOAD) {
                        if (!isPureScrollModeOn && Math.abs(mChildView.getTranslationY()) >= mBottomHeight - mTouchSlop) {
                            isLoadingmore = true;
                            animChildView(-mBottomHeight);
                            if (pullListener != null) {
                                pullListener.onLoadMore(TwinklingRefreshLayout.this);
                            }
                        } else {
                            animChildView(0f);
                        }
                    }
                }
                return true;
        }
        return super.onTouchEvent(e);
    }

    /*************************************
     * 越界回弹策略
     *****************************************/
    private void handleScrollEvent() {
        mChildView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        if (mChildView instanceof AbsListView) {
            ((AbsListView) mChildView).setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    if (!isRefreshing && !isLoadingmore && firstVisibleItem == 0 || ((AbsListView) mChildView).getLastVisiblePosition() == totalItemCount - 1) {
                        if (mVelocityY >= 5000 && ScrollingUtil.isAbsListViewToTop((AbsListView) mChildView)) {
                            animOverScrollTop();
                        }
                        if (mVelocityY <= -5000 && ScrollingUtil.isAbsListViewToBottom((AbsListView) mChildView)) {
                            animOverScrollBottom();
                        }
                    }
                }
            });
        } else if (mChildView instanceof RecyclerView) {
            ((RecyclerView) mChildView).addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (!isRefreshing && !isLoadingmore && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (mVelocityY >= 5000 && ScrollingUtil.isRecyclerViewToTop((RecyclerView) mChildView)) {
                            animOverScrollTop();
                        }
                        if (mVelocityY <= -5000 && ScrollingUtil.isRecyclerViewToBottom((RecyclerView) mChildView)) {
                            animOverScrollBottom();
                        }
                    }
                    super.onScrollStateChanged(recyclerView, newState);
                }
            });
        }
    }

    //主要为了监测Fling的动作,实现越界回弹
    private float mVelocityY;
    GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (isRefreshing && distanceY >= mTouchSlop) finishRefreshing();
            if (isLoadingmore && distanceY <= -mTouchSlop) finishLoadmore();

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            mVelocityY = velocityY;
            if (!(mChildView instanceof AbsListView || mChildView instanceof RecyclerView)) {
                //既不是AbsListView也不是RecyclerView,由于这些没有实现OnScrollListener接口,无法回调状态,只能采用延时策略
                if (Math.abs(mVelocityY) >= 5000) {
                    mHandler.sendEmptyMessage(MSG_START_COMPUTE_SCROLL);
                } else {
                    cur_delay_times = ALL_DELAY_TIMES;
                }
            }
            return false;
        }
    });

    //针对部分没有OnScrollListener的View的延时策略
    private static final int MSG_START_COMPUTE_SCROLL = 0; //开始计算
    private static final int MSG_CONTINUE_COMPUTE_SCROLL = 1;//继续计算
    private static final int MSG_STOP_COMPUTE_SCROLL = 2; //停止计算

    private int cur_delay_times = 0; //当前计算次数
    private static final int ALL_DELAY_TIMES = 20;  //10ms计算一次,总共计算20次
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_COMPUTE_SCROLL:
                    cur_delay_times = -1; //这里没有break,写作-1方便计数
                case MSG_CONTINUE_COMPUTE_SCROLL:
                    cur_delay_times++;

                    if (!isRefreshing && !isLoadingmore && mVelocityY >= 5000 && (mChildView != null && Math.abs(mChildView.getScrollY()) <= mTouchSlop)) {
                        animOverScrollTop();
                        cur_delay_times = ALL_DELAY_TIMES;
                    }

                    if (!isRefreshing && !isLoadingmore && mVelocityY <= -5000 && mChildView != null) {
                        if (mChildView instanceof ViewGroup) {
                            View subChildView = ((ViewGroup) mChildView).getChildAt(0);
                            if (subChildView != null && subChildView.getMeasuredHeight() <= mChildView.getScrollY() + mChildView.getHeight()) {
                                //滚动到了底部
                                animOverScrollBottom();
                                cur_delay_times = ALL_DELAY_TIMES;
                            }
                        } else if (mChildView.getScrollY() >= mChildView.getHeight()) {
                            animOverScrollBottom();
                            cur_delay_times = ALL_DELAY_TIMES;
                        }
                    }

                    if (cur_delay_times < ALL_DELAY_TIMES)
                        mHandler.sendEmptyMessageDelayed(MSG_CONTINUE_COMPUTE_SCROLL, 10);
                    break;
                case MSG_STOP_COMPUTE_SCROLL:
                    cur_delay_times = ALL_DELAY_TIMES;
                    break;
            }
        }
    };

    /*************************************  执行动画  *****************************************/
    /**
     * 向下兼容,把mChildView.animate().setUpdateListener()改成ObjectAnimator.addUpdateListener
     *
     * @param endValue mChildView最终位移
     */
    private void animChildView(float endValue) {
        animChildView(endValue, 300);
    }

    private void animChildView(float endValue, long duration) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(mChildView, "translationY", mChildView.getTranslationY(), endValue);
        oa.setDuration(duration);
        oa.setInterpolator(new DecelerateInterpolator());//设置速率为递减
        oa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float height = (Float) animation.getAnimatedValue();//获得mChildView当前y的位置
                height = Math.abs(height);

                if (state == PULL_DOWN_REFRESH) {
                    mHeadLayout.getLayoutParams().height = (int) height;
                    mHeadLayout.requestLayout();//重绘

                    if (isOverlayRefreshShow) {
                        mHeadLayout.setVisibility(VISIBLE);
                        mBottomLayout.setVisibility(GONE);
                    }

                    if (pullListener != null) {
                        pullListener.onPullDownReleasing(TwinklingRefreshLayout.this, height / mHeadHeight);
                    }
                } else if (state == PULL_UP_LOAD) {
                    mBottomLayout.getLayoutParams().height = (int) height;
                    mBottomLayout.requestLayout();

                    if (isOverlayRefreshShow) {
                        mHeadLayout.setVisibility(GONE);
                        mBottomLayout.setVisibility(VISIBLE);
                    }

                    if (pullListener != null) {
                        pullListener.onPullUpReleasing(TwinklingRefreshLayout.this, height / mBottomHeight);
                    }
                }
            }
        });
        oa.start();
    }

    private void animOverScrollTop() {
        mVelocityY = 0;
        state = PULL_DOWN_REFRESH;
        if (isOverlayRefreshShow) {
            animChildView(mOverScrollHeight, 150);
        } else {
            mChildView.animate().translationY(mOverScrollHeight).setDuration(150).start();
        }
        mChildView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOverlayRefreshShow) animChildView(0f);
                else mChildView.animate().translationY(0).start();
            }
        }, 150);
    }

    private void animOverScrollBottom() {
        mVelocityY = 0;
        state = PULL_UP_LOAD;
        if (isOverlayRefreshShow) {
            animChildView(-mOverScrollHeight, 150);
        } else {
            mChildView.animate().translationY(-mOverScrollHeight).setDuration(150).start();
        }
        mChildView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOverlayRefreshShow) animChildView(0f);
                else mChildView.animate().translationY(0).start();
            }
        }, 150);
    }

    /**
     * 设置拖动屏幕的监听器
     */
    private PullListener pullListener;

    private void setPullListener(PullListener pullListener) {
        this.pullListener = pullListener;
    }

    /*************************************  开放api区  *****************************************/
    /**
     * 刷新结束
     */
    public void finishRefreshing() {
        if (!isRefreshing)
            return;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefreshing = false;
                if (pullListener != null) pullListener.onFinishRefresh();
                if (mChildView != null) {
                    animChildView(0f);
                }
            }
        }, 1000);
    }

    /**
     * 加载更多结束
     */
    public void finishLoadmore() {
        if (!isLoadingmore)
            return;

        postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoadingmore = false;
                if (pullListener != null) pullListener.onFinishLoadMore();
                if (mChildView != null) {
                    animChildView(0f);
                    ScrollingUtil.scrollAViewBy(mChildView, (int) mBottomHeight);
                }
            }
        }, 1000);
    }

    /**
     * 设置头部View
     */
    public void setHeaderView(final IHeaderView headerView) {
        if (headerView != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    mHeadLayout.removeAllViewsInLayout();
                    mHeadLayout.addView(headerView.getView());
                }
            });
            mHeadView = headerView;
        }
    }

    /**
     * 设置底部View
     */
    public void setBottomView(final IBottomView bottomView) {
        if (bottomView != null) {
            post(new Runnable() {
                @Override
                public void run() {
                    mBottomLayout.removeAllViewsInLayout();
                    mBottomLayout.addView(bottomView.getView());
                }
            });
            mBottomView = bottomView;
        }
    }

    /**
     * 设置wave的下拉高度
     *
     * @param waveHeight
     */
    public void setWaveHeight(float waveHeight) {
        this.mWaveHeight = waveHeight;
    }

    /**
     * 设置下拉头的高度
     */
    public void setHeaderHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }

    /**
     * 设置底部高度
     */
    public void setBottomHeight(float bottomHeight) {
        this.mBottomHeight = bottomHeight;
    }

    /**
     * 是否允许加载更多
     */
    public void setEnableLoadmore(boolean enableLoadmore1) {
        enableLoadmore = enableLoadmore1;
        if (mBottomView != null) {
            if (enableLoadmore) mBottomView.getView().setVisibility(VISIBLE);
            else mBottomView.getView().setVisibility(GONE);
        }
    }

    /**
     * 是否允许下拉刷新
     */
    public void setEnableRefresh(boolean enableRefresh1) {
        this.enableRefresh = enableRefresh1;
    }

    /**
     * 是否允许越界时显示刷新控件
     */
    public void setEnableOverlayRefreshView(boolean enableShow) {
        isOverlayRefreshShow = enableShow;
    }

    /**
     * 是否开启纯净的越界回弹模式,开启时刷新和加载更多控件不显示
     */
    public void setPureScrollModeOn(boolean pureScrollModeOn) {
        isPureScrollModeOn = pureScrollModeOn;
        isOverlayRefreshShow = !isPureScrollModeOn;
    }

    public interface PullListener {
        /**
         * 下拉中
         *
         * @param refreshLayout
         * @param fraction
         */
        void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 上拉
         */
        void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 下拉松开
         *
         * @param refreshLayout
         * @param fraction
         */
        void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 上拉松开
         */
        void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction);

        /**
         * 刷新中。。。
         */
        void onRefresh(TwinklingRefreshLayout refreshLayout);

        /**
         * 加载更多中
         */
        void onLoadMore(TwinklingRefreshLayout refreshLayout);

        /**
         * 手动调用finishRefresh或者finishLoadmore之后的回调
         */
        void onFinishRefresh();

        void onFinishLoadMore();
    }


    private class SimplePullListener implements PullListener {

        @Override
        public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
            mHeadView.onPullingDown(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onPullingDown(refreshLayout, fraction);
        }

        @Override
        public void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction) {
            mBottomView.onPullingUp(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onPullingUp(refreshLayout, fraction);
        }

        @Override
        public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
            mHeadView.onPullReleasing(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null)
                refreshListener.onPullDownReleasing(refreshLayout, fraction);
        }

        @Override
        public void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
            mBottomView.onPullReleasing(fraction, mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onPullUpReleasing(refreshLayout, fraction);
        }

        @Override
        public void onRefresh(TwinklingRefreshLayout refreshLayout) {
            mHeadView.startAnim(mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onRefresh(refreshLayout);
        }

        @Override
        public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
            mBottomView.startAnim(mWaveHeight, mHeadHeight);
            if (refreshListener != null) refreshListener.onLoadMore(refreshLayout);
        }

        @Override
        public void onFinishRefresh() {
            mHeadView.onFinish();
        }

        @Override
        public void onFinishLoadMore() {
            mBottomView.onFinish();
        }
    }

    private OnRefreshListener refreshListener;

    public static class OnRefreshListener implements PullListener {
        @Override
        public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
        }

        @Override
        public void onRefresh(TwinklingRefreshLayout refreshLayout) {
        }

        @Override
        public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
        }

        @Override
        public void onFinishRefresh() {

        }

        @Override
        public void onFinishLoadMore() {

        }
    }

    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        if (refreshListener != null) {
            this.refreshListener = refreshListener;
        }
    }
}