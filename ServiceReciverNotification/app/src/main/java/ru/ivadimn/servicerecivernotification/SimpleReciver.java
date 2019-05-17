package ru.ivadimn.servicerecivernotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class SimpleReciver extends BroadcastReceiver {

    public static final String SIMPLE_ACTION = "ru.ivadimn.servicerecivernotification.SIMPLE_ACTION";
    private WeakReference<TextView> refTextView;

    public SimpleReciver(TextView textView) {
        refTextView = new WeakReference<>(textView);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        long currentTime = intent.getLongExtra(CountService.TIME, 0L);
        Toast.makeText(context, "Current time is " + currentTime, Toast.LENGTH_SHORT).show();
        StringBuilder builder = new StringBuilder(refTextView.get().getText().toString());
        builder.append("\n" + currentTime);
        refTextView.get().setText(builder.toString());
        //Intent newActivityIntent = new Intent(context, TempActivity.class);
        //newActivityIntent.putExtra(CountService.TIME, currentTime);
        //context.startActivity(newActivityIntent);
    }
}
