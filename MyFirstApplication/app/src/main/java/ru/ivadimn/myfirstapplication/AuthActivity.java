package ru.ivadimn.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;

    private View.OnClickListener mOnEnterClickLstener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //tod
        }
    };

    private View.OnClickListener mOnRegisterClickLstener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //tod
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_auth);

        mLogin = findViewById(R.id.etLogin);
        mPassword = findViewById(R.id.etPassword);
        mEnter = findViewById(R.id.buttonEnter);
        mRegister = findViewById(R.id.buttonEnter);

        mEnter.setOnClickListener(mOnEnterClickLstener);
        mRegister.setOnClickListener(mOnRegisterClickLstener);
    }
}
