package ru.ivadimn.myfirstapp;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchActivity extends AppCompatActivity {

    private final static String TAG = "......LAUNCH-ACTIVITY";

    private Button mBtnClick;
    private TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.d(TAG, "Into OnCreate M<ethod");


        final String newString = getString(R.string.new_text);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(R.color.red);
        }

        ContextCompat.getColor(this, R.color.red);

        mText = findViewById(R.id.tv_text);

        mBtnClick = findViewById(R.id.btn_click);

        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText.setText(newString);
                showCustomToast(newString);
            }
        });


    }

    public void showCustomToast(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_toast, null);

        TextView text = layout.findViewById(R.id.message_id);
        text.setText(msg);

        Toast toast = new Toast(this);
        toast.setView(layout);
        toast.show();
    }
}
