package ru.ivadimn.barservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProcessService extends Service {

    private int processValue = 0;
    private IBinder binder = new ProcessBinder();
    private Lock lock = new ReentrantLock();

    public interface UpdateUI {
        public void updateUI(int value);
    }


    public ProcessService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {

    }

    private class ProcessBinder extends Binder {
        ProcessService getProcessService() {
            return ProcessService.this;
        }
    }

    private void addProcessValue(int value) {
        synchronized (lock) {
            processValue += value;
            if (processValue > 100) {
                processValue = 0;
            }
        }
    }

    private void reduceProcessValue() {
        synchronized (lock) {
            if (processValue > 50) {
                processValue -= 50;
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
