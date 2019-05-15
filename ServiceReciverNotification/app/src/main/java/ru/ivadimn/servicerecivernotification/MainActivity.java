package ru.ivadimn.servicerecivernotification;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ScheduledExecutorService;

public class MainActivity extends AppCompatActivity {


    private Button btnbStartService;
    private Button btnbStopService;

    private View.OnClickListener onStartClickListener = (view) -> {
        Intent intent = new Intent(this, CountService.class);
        startService(intent);

    };

    private View.OnClickListener onStopClickListener = (view) -> {
        Intent intent = new Intent(this, CountService.class);
        stopService(intent);

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnbStartService = findViewById(R.id.btn_start);
        btnbStopService = findViewById(R.id.btn_stop);

        btnbStartService.setOnClickListener(onStartClickListener);
        btnbStopService.setOnClickListener(onStopClickListener);
    }
}
