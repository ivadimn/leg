package ru.ivadimn.myfirstapp;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LaunchActivity extends AppCompatActivity {

    private final static String TAG = "......LAUNCH-ACTIVITY";

    private Button mBtnClick;
    private TextView mText;

    private boolean isSettings = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        Log.d(TAG, "Into OnCreate M<ethod");


        final String newString = getString(R.string.new_text);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(R.color.red);
        }

        ContextCompat.getColor(this, R.color.red);

        mText = findViewById(R.id.tv_text);

        mBtnClick = findViewById(R.id.btn_click);

        mBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText.setText(newString);
                showCustomToast(newString);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerForContextMenu(mText);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterForContextMenu(mText);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.tv_text) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
        }
        else
            super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_context_id1:
                showCustomToast(item.getTitle().toString());
                return true;
            case R.id.item_context_id2:
                showCustomToast(item.getTitle().toString());
                return true;

            default: return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (!isSettings) {
            menu.removeItem(R.id.item_settings_id);
            return true;
        }
        else
        return super.onPrepareOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_add_id:
                 startSecondActivity();
                 return true;
            case R.id.item_edit_id:
                showCustomToast(item.getTitle().toString());
                return true;
            case R.id.item_delete_id:
                showCustomToast(item.getTitle().toString());
                return true;
            case R.id.item_settings_id:
                showCustomToast(item.getTitle().toString());
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void showCustomToast(String msg) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_toast, null);

        TextView text = layout.findViewById(R.id.message_id);
        text.setText(msg);

        Toast toast = new Toast(this);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER_VERTICAL, 20, 20);
        toast.show();
    }

    public void startSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);

    }
}
