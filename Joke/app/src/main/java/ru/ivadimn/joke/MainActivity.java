package ru.ivadimn.joke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnStartService;

    View.OnClickListener onStartService = (view) -> {
        Intent serviceIntent = new Intent(this, DelayedMessageService.class);
        serviceIntent.putExtra(DelayedMessageService.EXTRA_MESSAGE,
                getResources().getString(R.string.button_response));
        startService(serviceIntent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_service);
        btnStartService.setOnClickListener(onStartService);
    }
}
