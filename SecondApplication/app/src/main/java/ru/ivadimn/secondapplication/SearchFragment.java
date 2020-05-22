package ru.ivadimn.secondapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";

    private EditText mText;
    private Button mSearch;

    private View.OnClickListener mSearchClickListener = (view) ->
    {String text = mText.getText().toString();
        if (!text.isEmpty()) {
            search(text);
        }};


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mText = view.findViewById(R.id.input_text_id);
        mSearch = view.findViewById(R.id.buttom_search_id);
        mSearch.setOnClickListener(mSearchClickListener);
    }

    private void search(String text) {
        String url = "http://www.google.com/#q=";
        Intent intent  = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse(url + URLEncoder.encode(text, "UTF-8")));
            startActivity(intent);
        }
        catch (UnsupportedEncodingException e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
