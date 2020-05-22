package ru.ivadimn.fragmentsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SampleFragment extends Fragment {

    public static final String ARG_NAME = "ARG_NAME";
    public static final String ARG_COLOR = "ARG_COLOR";

    private String mName;
    private int mColor;
    private TextView mText;

    public static SampleFragment newInstance(String name, int color) {
        SampleFragment fragment = new SampleFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_NAME, name);
        bundle.putInt(ARG_COLOR, color);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mName = bundle.getString(ARG_NAME);
            mColor = bundle.getInt(ARG_COLOR);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dinamic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = view.findViewById(R.id.fragment_text_id);
        mText.setText(mName);
        view.setBackgroundColor(ContextCompat.getColor(getActivity(), mColor));
    }
}
