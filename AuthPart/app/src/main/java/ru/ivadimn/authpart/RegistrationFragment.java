package ru.ivadimn.authpart;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationFragment extends Fragment  {

    private EditText mLogin;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mRegistration;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    public static RegistrationFragment newInstance() {
        Bundle args = new Bundle();
        RegistrationFragment fragment = new RegistrationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnRegistrationClickListener = (view) -> {
        if (isInputValid()) {
           boolean isAdded = mSharedPreferencesHelper.addUser(new User(mLogin.getText().toString(),
                   mPassword.getText().toString()));
           if (isAdded) {
               showMessage(R.string.login_register_success);
           }
           else {
               showMessage(R.string.login_register_error);
           }

       }
    };

    private boolean isInputValid() {
        String email = mLogin.getText().toString();
        if (isEmailValid(email) && isPasswordValid()) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid() {
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();
        return  password.equals(passwordAgain)
                && !(password.isEmpty() || passwordAgain.isEmpty());
    }

    private void showMessage(@StringRes int message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_registration, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mPasswordAgain = v.findViewById(R.id.etPasswordAgain);
        mRegistration = v.findViewById(R.id.btnRegistration);

        mRegistration.setOnClickListener(mOnRegistrationClickListener);
        return v;
    }
}
