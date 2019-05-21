package ru.ivadimn.barservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbProcess;
    private Button btnReduce;
    private boolean bound = false;
    private ProcessService processService;

    View.OnClickListener  onReduceClickListener = (view) ->  {
        if(processService != null) {
            processService.reduce();
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ProcessService.ProcessBinder processBinder
                    = (ProcessService.ProcessBinder) iBinder;
            processService = processBinder.getProcessService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbProcess = findViewById(R.id.pb_process);
        btnReduce = findViewById(R.id.btn_reduce);
        btnReduce.setOnClickListener(onReduceClickListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ProcessService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        process();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    private void process() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int progress;
                if (processService != null) {
                    progress = processService.getProcessValue();
                    pbProcess.setProgress(progress);
                }
                handler.postDelayed(this, 200);
            }
        });
    }
}
