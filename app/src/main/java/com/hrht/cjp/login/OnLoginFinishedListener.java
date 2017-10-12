package com.hrht.cjp.login;

/**
 * 登陆事件监听
 * Created by admin on 2017/10/11.
 */

public interface OnLoginFinishedListener {
    void onUsernameError();

    void onPasswordError();

    void onSuccess();
}
