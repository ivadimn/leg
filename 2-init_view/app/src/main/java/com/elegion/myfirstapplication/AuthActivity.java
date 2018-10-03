package com.elegion.myfirstapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends AppCompatActivity {

	private EditText mLogin;
	private EditText mPassword;
	private Button mEnter;
	private Button mRegister;

	private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			//todo Обработка нажатия по кнопке
		}
	};

	private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			//todo Обработка нажатия по кнопке
		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_auth);

		mLogin = findViewById(R.id.etLogin);
		mPassword = findViewById(R.id.etPassword);
		mEnter = findViewById(R.id.buttonEnter);
		mRegister = findViewById(R.id.buttonRegister);

		mEnter.setOnClickListener(mOnEnterClickListener);
		mRegister.setOnClickListener(mOnRegisterClickListener);
	}
}
