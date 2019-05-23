package ru.ivadimn.tasync;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_REQUEST_PERMISSION_INTERNET = 4455;
    private String url = "https://raskraska1.com/detskie-raskraski/raskraska-liczo-devushki" +
            "/_assets_images_resources_827_raskraska-lico-devushki18.jpg";
    private Button btnLoad;

    private View.OnClickListener onLoad = (view) -> {
        checkPermission();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
            StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        btnLoad = findViewById(R.id.btn_load);
        btnLoad.setOnClickListener(onLoad);
    }

    public ProgressBar getProgressBar() {
        return findViewById(R.id.pb_load);
    }

    public ImageView getImageView() {
        return findViewById(R.id.img_load);
    }

    private static class LoadImage extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<MainActivity> activityReference;

        public LoadImage(MainActivity activity) {
            this.activityReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityReference.get();
            activity.getProgressBar().setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
           return getBitmap(strings[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            MainActivity activity = activityReference.get();
            activity.getProgressBar().setVisibility(View.INVISIBLE);
            activity.getImageView().setImageBitmap(bitmap);
        }

        private Bitmap getBitmap(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Bitmap bmp = BitmapFactory.decodeStream(is);
                is.close();
                Log.d("LOAD_IMAGE", "Return OK");
                return bmp;
            }
            catch(Exception e) {
                Log.d("LOAD_IMAGE", "Return null");
                return null;
            }
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.INTERNET}, REQUEST_CODE_REQUEST_PERMISSION_INTERNET);
        }
        else {
            getImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_REQUEST_PERMISSION_INTERNET) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImage();
            }
        }
    }

    private void getImage() {
        new LoadImage(this).execute(url);
    }
}
