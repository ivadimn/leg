package ru.ivadimn.barservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

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

    public static final String ACTION_FINISH_PROGRESS = "ACTION_FINISH_PROGRESS";
    private ProgressBar pbProcess;
    private Button btnReduce;
    private boolean bound = false;
    private ProcessService processService;
    private FinishReciver finishReciver;

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
        finishReciver = new FinishReciver();

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ACTION_FINISH_PROGRESS);
        registerReceiver(finishReciver, filter);
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
        unregisterReceiver(finishReciver);
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

    public class FinishReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(MainActivity.this, "Process was finished", Toast.LENGTH_SHORT).show();
        }
    }

}
