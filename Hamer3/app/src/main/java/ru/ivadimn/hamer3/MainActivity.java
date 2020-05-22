 package ru.ivadimn.hamer3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

 public class MainActivity extends AppCompatActivity {

    public static final int SHOW_PROGRESS_BAR = 1;
    public static final int HIDE_PROGRESS_BAR = 2;
    public static final int PROGRESS_BAR_PROCESS = 3;
    public static final int IMAGE_WAS_TRANSFERRED = 4;

    public static final int GET_PHOTO_REQUEST_CODE = 100;

    private ProgressBar pbTransfer;
    private ImageView imgTransfer;
    private Button btnTransfer;
    private ImageTransferThread imageThread;

    private View.OnClickListener onTransfer = (view) -> {
        BitmapDrawable drawable = (BitmapDrawable) imgTransfer.getDrawable();
        imageThread.doTransfer(drawable.getBitmap());
    };
    private View.OnClickListener onGetPhoto = (view) -> {
        openGalery();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pbTransfer = findViewById(R.id.pb_transfer);
        imgTransfer = findViewById(R.id.img_transfer);
        btnTransfer = findViewById(R.id.btn_transfer);
        btnTransfer.setOnClickListener(onTransfer);
        imgTransfer.setOnClickListener(onGetPhoto);
        imageThread = new ImageTransferThread(mainHandler);
        imageThread.start();

    }


    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_PROGRESS_BAR:
                    pbTransfer.setVisibility(View.VISIBLE);
                    break;
                case HIDE_PROGRESS_BAR:
                    pbTransfer.setVisibility(View.INVISIBLE);
                    break;
                case PROGRESS_BAR_PROCESS:
                    pbTransfer.setProgress(msg.arg1);
                    break;
                case IMAGE_WAS_TRANSFERRED:
                    imgTransfer.setImageBitmap((Bitmap) msg.obj);
                    break;
            }

        }
    };

     @Override
     protected void onDestroy() {
         super.onDestroy();
         imageThread.exit();
     }

     private void openGalery() {
         Intent intent = new Intent();
         intent.setType("image/*");
         intent.setAction(Intent.ACTION_GET_CONTENT);
         startActivityForResult(intent, GET_PHOTO_REQUEST_CODE);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         if (resultCode == RESULT_OK && requestCode == GET_PHOTO_REQUEST_CODE
            && data != null) {
            Uri photoUri = data.getData();
            imgTransfer.setImageURI(photoUri);
         }
         else {
             super.onActivityResult(requestCode, resultCode, data);
         }
     }
 }
