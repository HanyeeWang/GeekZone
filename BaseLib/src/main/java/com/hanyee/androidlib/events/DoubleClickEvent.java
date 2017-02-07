package com.hanyee.androidlib.events;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


/**
 * Created by Hanyee on 15/7/15.
 */
public abstract class DoubleClickEvent {

    private static final int MSG_TIMEOUT = 1;
    // 连续点击的最大时间间隔2000毫秒
    private static final int TIMEOUT = 2000;

    private boolean isClicked = false;

    public DoubleClickEvent() {
    }

    public void onClick() {
        if (isClicked) {
            reset();
            onDoubleClick();
        } else {
            isClicked = true;
            onSingleClick();
            mLooperHandler.sendMessageDelayed(mLooperHandler.obtainMessage(MSG_TIMEOUT), TIMEOUT);
        }
    }

    public abstract void onSingleClick();

    public abstract void onDoubleClick();

    private void reset() {
        isClicked = false;
        mLooperHandler.removeMessages(MSG_TIMEOUT);
    }

    private Handler mLooperHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TIMEOUT:
                    reset();
                    break;
            }
        }
    };
}
