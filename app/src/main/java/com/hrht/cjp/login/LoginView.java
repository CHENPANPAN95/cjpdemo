package com.hrht.cjp.login;

/**
 * 登陆View的接口，实现类也就是登陆的activity
 * Created by admin on 2017/10/11.
 */

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();
}
