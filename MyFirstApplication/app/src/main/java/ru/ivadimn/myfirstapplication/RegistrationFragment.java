package ru.ivadimn.myfirstapplication;

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

public class RegistrationFragment extends Fragment {

    private EditText mLogin;
    private EditText mPassword;
    private EditText mPasswordAgain;
    private Button mRegistartion;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    public static RegistrationFragment newInstance() {
        return  new RegistrationFragment();
    }


    private View.OnClickListener mRegistrationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isInputValid()) {
                boolean isAdded = mSharedPreferencesHelper.addUser(new User(
                        mLogin.getText().toString(),
                        mPassword.getText().toString()
                ));
                if(isAdded) {
                    showMessage(R.string.login_register_success);
                    getFragmentManager().popBackStack();
                }
                else
                    showMessage(R.string.login_register_error);
            }
            else {
                showMessage(R.string.login_input_error);
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_registration, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        mLogin = view.findViewById(R.id.reg_login_id);
        mPassword = view.findViewById(R.id.reg_password_id);
        mPasswordAgain = view.findViewById(R.id.reg_password_again_id);
        mRegistartion = view.findViewById(R.id.btn_registration_id);

        mRegistartion.setOnClickListener(mRegistrationClickListener);

        return view;
    }

    private boolean isInputValid() {
        String email = mLogin.getText().toString();
        return isEmailValid(email) && isPasswordValid();
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid() {
        String password = mPassword.getText().toString();
        String passwordAgain = mPasswordAgain.getText().toString();

        return password.equals(passwordAgain)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(passwordAgain);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }
}
