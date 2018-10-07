package ru.ivadimn.firstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final String TEXT_KEY = "TEXT_KEY";

    private TextView mShowText;
    private Button nothingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mShowText = findViewById(R.id.show_text_id);
        nothingButton = findViewById(R.id.nothing_button_id);

        mShowText.setText(getIntent().getExtras().getString(TEXT_KEY));


    }
}
