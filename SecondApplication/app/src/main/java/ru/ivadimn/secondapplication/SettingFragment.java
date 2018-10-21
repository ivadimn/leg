package ru.ivadimn.secondapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

public class SettingFragment extends Fragment {

    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String BROWSER_KEY = "BROWSER_KEY";

    private RadioButton mGoogle;
    private RadioButton mMail;
    private RadioButton mBing;


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    private View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton) view;
            switch(rb.getId()) {
                case R.id.rb_google_id :
                    saveSearchSystem(R.id.rb_google_id);
                    break;
                case R.id.rb_mail_id :
                    saveSearchSystem(R.id.rb_mail_id);
                    break;
                case R.id.rb_bing_id :
                    saveSearchSystem(R.id.rb_bing_id);
                    break;
                default: break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mGoogle = view.findViewById(R.id.rb_google_id);
        mMail = view.findViewById(R.id.rb_mail_id);
        mBing = view.findViewById(R.id.rb_bing_id);

        mGoogle.setOnClickListener(radioButtonClickListener);
        mMail.setOnClickListener(radioButtonClickListener);
        mBing.setOnClickListener(radioButtonClickListener);

        setRbChecked(getSearchSystem());

        super.onViewCreated(view, savedInstanceState);
    }

    private void setRbChecked(int sid) {
        switch(sid) {
            case R.id.rb_google_id :
                mGoogle.setChecked(true);
                break;
            case R.id.rb_mail_id :
                mMail.setChecked(true);
                break;
            case R.id.rb_bing_id :
                mBing.setChecked(true);
                break;
            default: break;
        }
    }

    private void saveSearchSystem(int sid) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putInt(BROWSER_KEY, sid)
                .commit();
    }

    private int getSearchSystem() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(BROWSER_KEY, 0);
    }
}
