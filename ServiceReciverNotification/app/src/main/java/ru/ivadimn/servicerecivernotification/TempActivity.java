package ru.ivadimn.servicerecivernotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        TextView tvTime = findViewById(R.id.tv_time);
        tvTime.setText("It is from notification " + getIntent().getStringExtra(CountService.TIME));

    }
}
