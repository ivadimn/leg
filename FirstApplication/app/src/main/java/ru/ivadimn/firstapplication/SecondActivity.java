package ru.ivadimn.firstapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SecondActivity extends AppCompatActivity {

    public static final String TEXT_KEY = "TEXT_KEY";

    private TextView mShowText;
    private Button mGoogleButton;

    private View.OnClickListener mGoogleButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
           showGoogleCom(mShowText.getText().toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mShowText = findViewById(R.id.show_text_id);
        mGoogleButton = findViewById(R.id.google_button_id);

        mShowText.setText(getIntent().getExtras().getString(TEXT_KEY));
        mGoogleButton.setOnClickListener(mGoogleButtonClickListener);


    }

    private void showGoogleCom(String text) {

        String url = "http://www.google.com/#q=";
        Intent intent  = new Intent(Intent.ACTION_VIEW);
        try {
           intent.setData(Uri.parse(url + URLEncoder.encode(text, "UTF-8")));
           startActivity(intent);
        }
        catch (UnsupportedEncodingException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();;
        }

    }
}
