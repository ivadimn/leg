package ru.ivadimn.hamer3;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;


public class ImageTransferThread extends Thread {

    public static final int MASK1 = 0xFF0000;
    public static final int MASK2 = 0x00FF00;
    public static final int MASK3 = 0x0000FF;
    private static final int PERCENT = 100;
    private static final int PARTS_COUNT = 50;
    private static final int PART_SIZE = PERCENT / PARTS_COUNT;

    private Handler backgroundHandler;
    private Handler mainHandler;

    private Bitmap image;

    public ImageTransferThread(Handler uiHandler) {
        this.mainHandler = uiHandler;
    }

    Runnable transferTask = () -> {
        Message uiMsg = mainHandler.obtainMessage(MainActivity.SHOW_PROGRESS_BAR, 0, 0, null);
        mainHandler.sendMessage(uiMsg);
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
                uiMsg = mainHandler.obtainMessage(MainActivity.PROGRESS_BAR_PROCESS, i / part * PART_SIZE, 0, null);
                mainHandler.sendMessage(uiMsg);
            }
        }

        Bitmap result = Bitmap.createBitmap(pixels, w, h, Bitmap.Config.RGB_565);
        uiMsg = mainHandler.obtainMessage(MainActivity.IMAGE_WAS_TRANSFERRED, result);
        mainHandler.sendMessage(uiMsg);

        uiMsg = mainHandler.obtainMessage(MainActivity.HIDE_PROGRESS_BAR, 100, 0, null);
        mainHandler.sendMessage(uiMsg);

    };

    @Override
    public void run() {
        Looper.prepare();
        backgroundHandler = new Handler();
        Looper.loop();
    }

    public void doTransfer(Bitmap img) {
        this.image = img;
        backgroundHandler.post(transferTask);
    }

    public void exit() {
        backgroundHandler.getLooper().quit();
    }
}
