 package ru.ivadimn.fragmentsapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 public class MainActivity extends AppCompatActivity {

    public static final String TAG = "FrgamentsApp.MainActivity";
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = findViewById(R.id.btn_one);
        mButton2 = findViewById(R.id.btn_two);
        mButton3 = findViewById(R.id.btn_three);

    }

    public void onButtonClick(View view) {

        Button btn;

        if (view instanceof Button)
            btn = (Button) view;
        else
            return;

        switch(btn.getId()) {
            case R.id.btn_one:
                showFragment(btn.getText().toString(), R.color.rose);
                return;
            case R.id.btn_two:
                showFragment(btn.getText().toString(), R.color.blue);
                return;
            case R.id.btn_three:
                showFragment(btn.getText().toString(), R.color.brown);
                return;

            default: return;

        }
    }

    public void showFragment(String text, int color) {
        FragmentManager fm = getSupportFragmentManager();
        SampleFragment fragment = SampleFragment.newInstance(text, color);
        fm.beginTransaction()
            .replace(R.id.fragment_container_id, fragment)
            .commit();
    }

}
