package com.raisesail.base_ui;

/**
 * presenter接口基类
 */
public interface IBasePresenter<V> {
    void registerView(V view);
    void unRegisterView();
    boolean isViewAttached();
    V getView();
}
