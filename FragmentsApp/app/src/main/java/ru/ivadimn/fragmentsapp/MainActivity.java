 package ru.ivadimn.fragmentsapp;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

 public class MainActivity extends AppCompatActivity implements DialogLogin.DialogCallback {

    public static final String TAG = "FrgamentsApp.MainActivity";
    private static final String PREF = "ru.ivadimn.FragmentsApp";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;

    private String mLogin;
    private String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton1 = findViewById(R.id.btn_one);
        mButton2 = findViewById(R.id.btn_two);
        mButton3 = findViewById(R.id.btn_three);

        getUserInfo();

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
            case R.id.btn_alert_dilaog:
                showAlertDialog();
                return;
            case R.id.btn_fragment_dialog:
                showFragmentDialog();
                return;

            default: return;

        }
    }

    public void showFragment(String text, int color) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag(text);
        if (fragment == null) {
            fragment = SampleFragment.newInstance(text, color);
            fm.beginTransaction()
                    .replace(R.id.fragment_container_id, fragment, text)
                    .commit();
        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Answer the question")
                .setMessage("Is London the capital of Greate Britain?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Your answer is right",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Your answer is not right",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .show();

    }

    public void showFragmentDialog() {
        DialogLogin.showDialog(getSupportFragmentManager(), mLogin);
    }

     @Override
     public void setUserInfo(String login, String password) {
         Toast.makeText(this, login + " " + password, Toast.LENGTH_LONG).show();
         mLogin = login;
         mPassword = password;
         saveUserInfo();
        return;
     }

     private void getUserInfo() {
         SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
         prefs.edit().clear().commit();
         mLogin = prefs.getString(LOGIN, "");
         mPassword = prefs.getString(PASSWORD, "");
     }

     private void saveUserInfo() {
         SharedPreferences prefs = getSharedPreferences(PREF, MODE_PRIVATE);
         prefs.edit()
                 .putString(LOGIN, mLogin)
                 .putString(PASSWORD, mPassword)
                 .commit();

     }
 }
