package ru.ivadimn.secondapplication;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_id, MainFragment.newInstance())
                    .addToBackStack(MainFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.mi_settings_id:
                showMessage(R.string.menu_item_settings);
                showFragment(SettingFragment.newInstance());
                break;
            case R.id.mi_search_id:
                showMessage(R.string.menu_item_search);
                showFragment(SearchFragment.newInstance());
                break;
            case R.id.mi_exit_id:
                showMessage(R.string.menu_item_exit);
                exit();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(@StringRes int string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_id, fragment)
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        }
        else {
            fragmentManager.popBackStack();
        }
    }

    private void exit() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        }
    }

}
