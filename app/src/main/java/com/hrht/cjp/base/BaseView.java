package com.hrht.cjp.base;

/**
 * Created by admin on 2017/10/11.
 */

public interface BaseView {

    void showMessage(String msg);

    void close();

    void showProgress(String msg);

    void showProgress(String msg, int progress);

    void hideProgress();

    void showErrorMessage(String msg,String content);

}
