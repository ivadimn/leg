package ru.ivadimn.simplehandler;

import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private SimpleHandler messageThread;
    private EditText etMessage;
    private Button btnMessage;

    private View.OnClickListener onMessageClickLictener = (view) -> {
        Message msg = messageThread.getmHanler().obtainMessage(0,
                etMessage.getText().toString());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    SystemClock.sleep(new Random().nextInt(10));
                    messageThread.doWork(msg + " " + j);
                }
            }).start();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMessage = findViewById(R.id.et_message);
        btnMessage = findViewById(R.id.btn_message);
        btnMessage.setOnClickListener(onMessageClickLictener);
        messageThread = new SimpleHandler();
        messageThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageThread.exit();
    }
}
