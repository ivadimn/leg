package ru.ivadimn.servicerecivernotification;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {


    private Button btnbStartService;
    private Button btnbStopService;
    private Button btnSendBroadcast;
    private Button btnStartMp3;
    private SimpleReciver reciver;
    private IntentFilter intentFilter;

    private View.OnClickListener onStartClickListener = (view) -> {
        Intent intent = new Intent(this, CountService.class);
        startService(intent);
    };

    private View.OnClickListener onStartMp3ClickListener = (view) -> {
        Intent intent = new Intent(this, WombleService.class);
        startService(intent);
    };

    private View.OnClickListener onStopClickListener = (view) -> {
        Intent intent = new Intent(this, CountService.class);
        stopService(intent);

    };

    private View.OnClickListener onSendClickListener = (view) -> {
        Intent intent = new Intent(SimpleReciver.SIMPLE_ACTION);
        sendBroadcast(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnbStartService = findViewById(R.id.btn_start);
        btnbStopService = findViewById(R.id.btn_stop);
        btnSendBroadcast = findViewById(R.id.btn_send_broadcast);
        btnStartMp3 = findViewById(R.id.btn_start_mp3);

        btnbStartService.setOnClickListener(onStartClickListener);
        btnbStopService.setOnClickListener(onStopClickListener);
        btnSendBroadcast.setOnClickListener(onSendClickListener);
        btnStartMp3.setOnClickListener(onStartMp3ClickListener);

        reciver = new SimpleReciver((TextView)  findViewById(R.id.tv_time_plus));
        intentFilter = new IntentFilter(SimpleReciver.SIMPLE_ACTION);
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(reciver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(reciver);
    }
}
