package com.raisesail.base_ui.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.raisesail.base_ui.IBaseView;
import com.raisesail.base_ui.base.BasePresenter;

/**
 * presenter ->Fragment
 * @param <P>
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    protected P mPresenter;
    private View mRootView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();
    }

    protected abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(getLayoutId(),container,false);
        }else{
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null){
                viewGroup.removeView(mRootView);
            }
        }
        initRootView();
        return mRootView;
    }

    protected abstract void initRootView();

    protected abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unBindView();
    }

    @Override
    public Context getViewContext() {
        return getActivity();
    }

    @Override
    public void bindView() {
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.registerView(this);
        }
    }

    @Override
    public void unBindView() {
        if (mPresenter != null){
            mPresenter.unRegisterView();
        }
    }
}
