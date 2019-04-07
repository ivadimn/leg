package ru.ivadimn.authpart;

import java.io.Serializable;

public class User implements Serializable {

    private String mLogin;
    private String mPassword;
    private boolean mHasSuccessLogin;

    public boolean isHasSuccessLogin() {
        return mHasSuccessLogin;
    }

    public void setHasSuccessLogin(boolean mHasSuccessLogin) {
        this.mHasSuccessLogin = mHasSuccessLogin;
    }

    public User(String mLogin, String mPassword) {
        this.mLogin = mLogin;
        this.mPassword = mPassword;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
