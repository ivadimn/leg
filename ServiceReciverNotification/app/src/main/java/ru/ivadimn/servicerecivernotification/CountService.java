package ru.ivadimn.servicerecivernotification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {
    public static final String TAG = "_____COUNT_SERVICE";

    private ScheduledExecutorService scheduledTask;

    public CountService() {
    }

    private Runnable task = () -> {
        Log.d(TAG, "run: " + System.currentTimeMillis());
    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service onCreate" );
        scheduledTask = new ScheduledThreadPoolExecutor(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");
        scheduledTask.scheduleAtFixedRate(task, 1000, 1000, TimeUnit.MILLISECONDS);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        scheduledTask.shutdownNow();
        Log.d(TAG, "Service onDestroy");
    }
}
