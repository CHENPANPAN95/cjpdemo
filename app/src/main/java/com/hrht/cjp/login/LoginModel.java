package com.hrht.cjp.login;

/**
 * 模拟登陆的操作的接口，实现类为LoginModelImpl.相当于MVP模式中的Model层
 * Created by admin on 2017/10/11.
 */

public interface LoginModel {
    void login(String username,String password,OnLoginFinishedListener listener);

}
