package ru.ivadimn.hamer2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int SHOW_PROGRESS_BAR = 1;
    public static final int HIDE_PROGRESS_BAR = 0;
    public static final int PROGRESS_PROGRESS_BAR = 2;

    private BackgroundThread mBackgroundThread;
    private ProgressBar mProgressBar;
    private ImageView mImage;
    private Button mBtnStart;
    private TextView mTvRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();

        mProgressBar = findViewById(R.id.pb_transform);
        mProgressBar.setMax(100);
        mImage = findViewById(R.id.img_transform);
        mBtnStart = findViewById(R.id.btn_transfer);
        mTvRandom = findViewById(R.id.tv_random);
        mBtnStart.setOnClickListener( view -> mBackgroundThread.doWork());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundThread.exit();
    }

    private class BackgroundThread extends Thread {
        private Handler mBackgroundHandler;

        private Runnable task = () -> {
            Message uiMsg = mUiHandler.obtainMessage(SHOW_PROGRESS_BAR, 0, 0, null);
            mUiHandler.sendMessage(uiMsg);

            int randomInt = 0;
            Random r = new Random();
            while(randomInt < 100)  {
                randomInt += r.nextInt(5);
                uiMsg = mUiHandler.obtainMessage(PROGRESS_PROGRESS_BAR, randomInt, 0, null);
                mUiHandler.sendMessage(uiMsg);
                SystemClock.sleep(200);
            }

            uiMsg = mUiHandler.obtainMessage(HIDE_PROGRESS_BAR, randomInt, 0, null);
            mUiHandler.sendMessage(uiMsg);
        };


        public void run() {
            Looper.prepare();
            mBackgroundHandler = new Handler();
            Looper.loop();
        }

        public void doWork() {
            mBackgroundHandler.post(task);
        }

        public void exit() {
            mBackgroundHandler.getLooper().quit();
        }
    }

    private final Handler mUiHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_PROGRESS_BAR:
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case PROGRESS_PROGRESS_BAR:
                    mProgressBar.setProgress(msg.arg1);
                    break;
                case HIDE_PROGRESS_BAR:
                    mTvRandom.setText(String.valueOf(msg.arg1));
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };


}
