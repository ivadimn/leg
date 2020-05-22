package ru.ivadimn.firstapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private Button mEnter;

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String text =  mInput.getText().toString();
            if (!text.isEmpty()) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                showSecondActivity(text);
            }
        }
    };


    private void showSecondActivity(String text) {
        Intent secondActivityIntent = new Intent(this, SecondActivity.class);
        secondActivityIntent.putExtra(SecondActivity.TEXT_KEY, text);
        startActivity(secondActivityIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = findViewById(R.id.input_text_id);
        mEnter = findViewById(R.id.buttom_enter_id);

        mEnter.setOnClickListener(mOnEnterClickListener);
    }
}
