package ru.ivadimn.servicerecivernotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {
    public static final String TAG = "_____COUNT_SERVICE";
    public static final String TIME = "TIME";

    private ScheduledExecutorService scheduledTask;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;


    public CountService() {
    }

    private Runnable task = () -> {
        long currentTime = System.currentTimeMillis();
        Log.d(TAG, "run: " + System.currentTimeMillis());
        mManager.notify(123, getNotification("Current time is " + currentTime));
        Intent intent = new Intent(SimpleReciver.SIMPLE_ACTION);
        intent.putExtra(TIME, currentTime);
        sendBroadcast(intent);
    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "Service onCreate" );
        scheduledTask = new ScheduledThreadPoolExecutor(1);
        mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = getNotificationBuilder();
        mBuilder.setContentTitle("Count service notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return new NotificationCompat.Builder(this);
        }
        else {
            String channelId = "my_channel_id";
            if (mManager.getNotificationChannel(channelId) == null) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "Text for user", NotificationManager.IMPORTANCE_LOW);
                mManager.createNotificationChannel(channel);
            }
            return new NotificationCompat.Builder(this, channelId);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service onStartCommand");
        startForeground(123, getNotification("Start notification"));
        scheduledTask.scheduleAtFixedRate(task, 1000, 4000, TimeUnit.MILLISECONDS);
        return START_STICKY;
    }

    private Notification getNotification(String contentText) {
        Intent intent = new Intent(this, TempActivity.class);
        intent.putExtra(TIME, contentText);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1234,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mBuilder.setContentText(contentText)
                .setContentIntent(pendingIntent)
                .build();
    }

    @Override
    public void onDestroy() {
        scheduledTask.shutdownNow();
        Log.d(TAG, "Service onDestroy");
    }
}
