package com.hrht.cjp.base;

/**
 * Created by admin on 2017/10/11.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
