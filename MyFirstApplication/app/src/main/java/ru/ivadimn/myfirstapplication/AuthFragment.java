package ru.ivadimn.myfirstapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthFragment extends Fragment {

    public static final String TAG = "AuthFragment";

    private EditText mLogin;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;
    private SharedPreferencesHelper mSharedPreferencesHelper;


    public static AuthFragment newInstance() {

        Bundle args = new Bundle();

        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean isLoginSuccess = false;
            for (User u : mSharedPreferencesHelper.getUsers()) {
                if (u.getLogin().equalsIgnoreCase(mLogin.getText().toString())
                        && u.getPassword().equals(mPassword.getText().toString())) {
                    isLoginSuccess = true;
                    if (isEmailValid() && isPasswordValid()) {
                        Intent startPprofileIntent = new Intent(getActivity(), ProfileActivity.class);
                        startPprofileIntent.putExtra(ProfileActivity.USER_KEY,
                                new User(mLogin.getText().toString(), mPassword.getText().toString()));

                        startActivity(startPprofileIntent);
                        getActivity().finish();
                    } else {
                        showMessage(R.string.login_input_error);
                    }
                    break;
                }
            }
            if (!isLoginSuccess) {
                showMessage(R.string.login_error);
            }
        }

    };

    private View.OnClickListener mOnRegisterClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_id, RegistrationFragment.newInstance())
                    .addToBackStack(RegistrationFragment.class.getName())
                    .commit();
        }
    };


    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mLogin.getText()) &&
                Patterns.EMAIL_ADDRESS.matcher(mLogin.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fr_auth, container, false);

        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        mLogin = view.findViewById(R.id.etLogin);
        mPassword = view.findViewById(R.id.etPassword);
        mEnter = view.findViewById(R.id.buttonEnter);
        mRegister = view.findViewById(R.id.buttonRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);

        return view;
    }
}
