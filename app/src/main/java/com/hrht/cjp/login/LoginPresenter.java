package com.hrht.cjp.login;

/**
 * 登陆的Presenter 的接口，实现类为LoginPresenterImpl，完成登陆的验证，以及销毁当前view
 * Created by admin on 2017/10/11.
 */

public interface LoginPresenter {
    void validateCredentials(String username, String password);
    void onDestroy();
}
