package ru.ivadimn.barservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessService extends Service {

    public static final int MAX_VALUE = 100;
    public static final int DELTA_VALUE = 5;
    public static final int REDUCE_VALUE = MAX_VALUE / 2;

    private int processValue = 0;
    private IBinder binder = new ProcessBinder();
    private Lock lock = new ReentrantLock();
    private ScheduledExecutorService executorService;

    public ProcessService() {

    }

    Runnable processTask = () -> {
       addProcessValue(DELTA_VALUE);
        Log.d("ProcessService", "progress is " + processValue);
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(processTask, 200, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDestroy() {
        executorService.shutdownNow();
    }

    public class ProcessBinder extends Binder {
        ProcessService getProcessService() {
            return ProcessService.this;
        }
    }

    private void addProcessValue(int value) {
        synchronized (lock) {
            processValue += value;
            if (processValue > MAX_VALUE) {
                processValue = 0;
            }
        }
    }

    private void reduceProcessValue() {
        synchronized (lock) {
            if (processValue > REDUCE_VALUE) {
                processValue -= REDUCE_VALUE;
            }
            else {
                processValue = 0;
            }
        }
    }

    public void reduce() {
        reduceProcessValue();
    }

    public int getProcessValue() {
        return processValue;
    }
}
