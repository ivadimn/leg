package ru.ivadimn.barservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private ScheduledExecutorService executorService;
    private int progressValue = 0;

    ProcessService.UpdateUI updateProgressBar = new ProcessService.UpdateUI() {
        @Override
        public void updateUI(int value) {
            pbProcess.setProgress(value);
        }
    };

    Runnable processTask = () -> {
        progressValue += 5;
        if (progressValue > 100) {
            progressValue = 0;
        }
        pbProcess.setProgress(progressValue);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbProcess = findViewById(R.id.pb_process);
        btnReduce = findViewById(R.id.btn_reduce);
        executorService = new ScheduledThreadPoolExecutor(1);

    }

    @Override
    protected void onStart() {
        super.onStart();
        executorService.scheduleAtFixedRate(processTask, 200, 200, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        executorService.shutdownNow();
    }

    private void process() {

    }
}
