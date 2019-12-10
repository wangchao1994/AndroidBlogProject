package com.raisesail.base_ui.base;

import com.raisesail.base_ui.IBasePresenter;

import java.lang.ref.WeakReference;

/**
 * BasePresenter基类
 *
 * @param <V>
 */
public class BasePresenter<V> implements IBasePresenter<V> {

    private WeakReference<V> mWeakReferenceView;

    @Override
    public void registerView(V view) {
        mWeakReferenceView = new WeakReference<>(view);
    }

    @Override
    public void unRegisterView() {
        if (mWeakReferenceView != null) {
            mWeakReferenceView.clear();
            mWeakReferenceView = null;
        }
    }

    @Override
    public boolean isViewAttached() {
        return mWeakReferenceView != null && mWeakReferenceView.get() != null;
    }

    @Override
    public V getView() {
        return mWeakReferenceView != null ? mWeakReferenceView.get() : null;
    }
}
