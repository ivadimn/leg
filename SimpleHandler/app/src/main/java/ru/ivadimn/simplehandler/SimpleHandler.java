package ru.ivadimn.simplehandler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;


public class SimpleHandler extends Thread implements MessageQueue.IdleHandler {

    public static final String TAG = "SIMPLE_HANDLER";
    public static final int MAX_IDLE_COUNT = 5;

    private int idleCount;
    private Handler mHanler;
    private boolean mIsFirstIdle = true;

    public void run() {
        Looper.prepare();
        mHanler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                loggingMessage((String) msg.obj);
            }
        };
        Looper.myQueue().addIdleHandler(this);
        Looper.loop();
    }

    private void loggingMessage(String msg) {
        Log.d(TAG, msg);
    }

    public Handler getmHanler() {
        return mHanler;
    }

    public void exit() {
        mHanler.getLooper().quit();
    }

    @Override
    public boolean queueIdle() {
        Log.d(TAG, "The thread is IDLE!!");
        return (++idleCount < MAX_IDLE_COUNT);
    }

    public void doWork(String text) {
        Message msg = mHanler.obtainMessage(0, text);
        mHanler.sendMessage(msg);
    }
}
