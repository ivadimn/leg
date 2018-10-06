package ru.ivadimn.firstapplication;

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
            showInputText(mInput.getText().toString());
            mInput.setText("");
        }
    };

    private void showInputText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
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
