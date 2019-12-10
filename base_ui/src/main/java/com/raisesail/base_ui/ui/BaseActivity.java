package com.raisesail.base_ui.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.raisesail.base_ui.IBaseView;
import com.raisesail.base_ui.R;
import com.raisesail.base_ui.base.BasePresenter;
import com.raisesail.base_ui.status_bar.StatusBar;

/**
 * ui基类
 * @param <P>
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initStatusBar();
        initData();
    }

    protected abstract int getLayoutId();
    protected void initData(){}
    /**
     * 设置沉浸式状态栏
     */
    private void initStatusBar() {
        StatusBar.setStatusColor(getWindow(), ContextCompat.getColor(this,
                R.color.colorAccent), 1f);
    }
    @Override
    public void bindView() {
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.registerView(this);
        }
    }

    protected abstract P createPresenter();

    @Override
    public void unBindView() {
        if (mPresenter != null){
            mPresenter.unRegisterView();
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
