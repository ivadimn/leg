package ru.ivadimn.fragmentsapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

public class DialogLogin extends DialogFragment {

    public static final String TAG = "ru.ivadimn.FragmentsApp.DialogLogin";
    public static final String LOGIN_NAME = "LOGIN_NAME";

    private EditText mLogin;
    private EditText mPassword;

    private String mLoginName;

    public static interface DialogCallback {
        public void setUserInfo(String login, String password);
    }

    private DialogCallback mGetResult;

    public static void showDialog(FragmentManager manager, String login) {
        DialogLogin dialog = new DialogLogin();
        Bundle bundle = new Bundle();
        bundle.putString(LOGIN_NAME, login);
        dialog.setArguments(bundle);
        dialog.show(manager, TAG);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof DialogCallback) {
            mGetResult = (DialogCallback) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLoginName = bundle.getString(LOGIN_NAME);
        }
        else
            mLoginName = "";
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View inflate = getActivity().getLayoutInflater().inflate(R.layout.dialog_login, null);

        mLogin = inflate.findViewById(R.id.edit_login_id);
        mLogin.setText(mLoginName);
        mPassword = inflate.findViewById(R.id.edit_password_id);

        builder.setTitle("Input login and password")
        .setView(inflate)
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGetResult.setUserInfo(mLogin.getText().toString(), mPassword.getText().toString());
            }
        })
        .setNegativeButton("Cancel", null);


        return builder.create();
    }
}
