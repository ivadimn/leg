package ru.ivadimn.hamer2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.util.BitSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int SHOW_PROGRESS_BAR = 1;
    public static final int HIDE_PROGRESS_BAR = 0;
    public static final int PROGRESS_PROGRESS_BAR = 2;
    public static final int PROGRESS_FINISHED = 3;

    public static final int MASK1 = 0xFF0000;
    public static final int MASK2 = 0x00FF00;
    public static final int MASK3 = 0x0000FF;
    private static final int PERCENT = 100;
    private static final int PARTS_COUNT = 50;
    private static final int PART_SIZE = PERCENT / PARTS_COUNT;

    private BackgroundThread mBackgroundThread;
    private ProgressBar mProgressBar;
    private ImageView mImage;
    private Button mBtnStart;
    private TextView mTvRandom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.pb_transform);
        mProgressBar.setMax(100);
        mImage = findViewById(R.id.img_transform);
        mImage.getDrawable();
        BitmapDrawable drawable = (BitmapDrawable) mImage.getDrawable();

        mBtnStart = findViewById(R.id.btn_transfer);
        mTvRandom = findViewById(R.id.tv_random);
        mBtnStart.setOnClickListener( view -> mBackgroundThread.doWork());

        mBackgroundThread = new BackgroundThread(drawable.getBitmap());
        mBackgroundThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundThread.exit();
    }

    private class BackgroundThread extends Thread {
        private Handler mBackgroundHandler;

        private Bitmap image;
        public BackgroundThread(Bitmap image) {
            this.image = image;
        }

        private Runnable task = () -> {
            Message uiMsg = mUiHandler.obtainMessage(SHOW_PROGRESS_BAR, 0, 0, null);
            mUiHandler.sendMessage(uiMsg);
            int h = image.getHeight();
            int w = image.getWidth();
            int[] pixels = new int[h * w];
            image.getPixels(pixels, 0, w, 0, 0, w, h);

            int one;
            int two;
            int three;
            for (int i = 0; i < h * w; i++) {
                one = (pixels[i] & MASK1) >> 16;
                two = (pixels[i] & MASK2) << 8;
                three = (pixels[i] & MASK3) << 8;
                pixels[i] = two | three | one;

                int part = w * h / PARTS_COUNT;
                if (i % part == 0) {
                    uiMsg = mUiHandler.obtainMessage(PROGRESS_PROGRESS_BAR, i / part * PART_SIZE, 0, null);
                    mUiHandler.sendMessage(uiMsg);
                }
            }
            Bitmap result = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.RGB_565);
            uiMsg = mUiHandler.obtainMessage(PROGRESS_FINISHED, result);
            mUiHandler.sendMessage(uiMsg);



            uiMsg = mUiHandler.obtainMessage(HIDE_PROGRESS_BAR, 100, 0, null);
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
                case PROGRESS_FINISHED:
                    Bitmap bmp = (Bitmap) msg.obj;
                    mImage.setImageBitmap(bmp);
                    break;
            }
        }
    };


}
