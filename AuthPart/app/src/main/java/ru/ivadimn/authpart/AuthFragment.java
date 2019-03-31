package ru.ivadimn.authpart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthFragment extends Fragment {

    private EditText mLogin;
    private EditText mPassword;
    private Button mEnter;
    private Button mRegister;

    public static AuthFragment newInstance() {
        Bundle args = new Bundle();
        AuthFragment fragment = new AuthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View.OnClickListener mOnEnterClickListener = (view) -> {
        if (isEmailValid() && isPasswordValid()) {
            Intent profileIntent = new Intent(getActivity(), ProfileActivity.class);
            profileIntent.putExtra(ProfileActivity.USER_KEY,
                    new User(mLogin.getText().toString(),mPassword.getText().toString()));

            startActivity(profileIntent);
        }
        else {
            showMessage(R.string.login_input_error);
        }
    };

    private View.OnClickListener mOnRegisterClickListener = (view) -> {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, RegistrationFragment.newInstance())
                .commit();
    };

    private boolean isEmailValid() {
        return !TextUtils.isEmpty(mLogin.getText())
                && Patterns.EMAIL_ADDRESS.matcher(mLogin.getText()).matches();
    }

    private boolean isPasswordValid() {
        return !TextUtils.isEmpty(mPassword.getText());
    }

    private void showMessage(@StringRes int message) {
        Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_auth, container,false);
        mLogin = v.findViewById(R.id.etLogin);
        mPassword = v.findViewById(R.id.etPassword);
        mEnter = v.findViewById(R.id.btnEnter);
        mRegister = v.findViewById(R.id.btnRegister);

        mEnter.setOnClickListener(mOnEnterClickListener);
        mRegister.setOnClickListener(mOnRegisterClickListener);
        return v;
    }
}
