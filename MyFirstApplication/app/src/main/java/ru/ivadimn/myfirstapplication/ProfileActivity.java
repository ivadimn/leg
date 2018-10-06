package ru.ivadimn.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewAnimator;

public class ProfileActivity extends AppCompatActivity {

    public static final String USER_KEY = "USER_KEY";


    private AppCompatImageView mPhoto;
    private TextView mLogin;
    private TextView mPassword;


    private View.OnClickListener mPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //////////////
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mPhoto = findViewById(R.id.ivPhoto);
        mLogin = findViewById(R.id.tvEmail);
        mPassword = findViewById(R.id.tvPassword);

        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.get(USER_KEY);
        mLogin.setText(user.getmLogin());
        mPassword.setText(user.getmPassword());

        mPhoto.setOnClickListener(mPhotoClickListener);
    }
}
