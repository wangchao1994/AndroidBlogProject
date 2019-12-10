package com.raisesail.base_ui.ui;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.content.DialogInterface;
import com.raisesail.base_ui.R;

/**
 * 弹窗dialog基类
 */
public abstract class BaseDialogFragment extends DialogFragment implements DialogInterface.OnKeyListener{

    private View mView = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setDialogStyle() == 0) {
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.default_dialog_style);
        } else {
            setStyle(DialogFragment.STYLE_NO_FRAME, setDialogStyle());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getDialog().getWindow() != null){
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if (setWindowAnimationsStyle() != 0 && getDialog().getWindow() != null) {
            getDialog().getWindow().setWindowAnimations(setWindowAnimationsStyle());
        }
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup mViewGroup = (ViewGroup) mView.getParent();
            if (mViewGroup != null) {
                mViewGroup.removeView(mView);
            }
        }
        if(getDialog() != null) {
            getDialog().setOnKeyListener(this);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData(getArguments());
    }

    /**
     * 自定义时添加layout
     * @return int
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     * @param view View
     */
    protected abstract void initView(View view);

    /**
     * 加载数据
     * @param bundle 用这个Bundle对象接收传入时的Bundle对象
     */
    protected abstract void loadData(Bundle bundle);

    /**
     * 创建视图
     * @param context Context
     * @param resId int
     * @param <T> View
     * @return View
     */
    protected <T extends View> T createView(Context context, int resId) {
        return (T) LayoutInflater.from(context).inflate(resId, null);
    }

    /**
     * 设置Dialog点击外部区域是否隐藏
     * @param cancel boolean
     */
    protected void setCanceledOnTouchOutside(boolean cancel) {
        if (getDialog() != null) {
            getDialog().setCanceledOnTouchOutside(cancel);
        }
    }

    /**
     * 设置Dialog gravity
     * @param gravity int
     */
    protected void setGravity(int gravity) {
        if (getDialog() != null) {
            Window mWindow = getDialog().getWindow();
            WindowManager.LayoutParams params = mWindow.getAttributes();
            params.gravity = gravity;
            mWindow.setAttributes(params);
        }
    }

    /**
     * 设置Dialog窗口width
     * @param width int
     */
    protected void setDialogWidth(int width) {
        setDialogWidthAndHeight(width, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 设置Dialog窗口height
     * @param height int
     */
    protected void setDialogHeight(int height) {
        setDialogWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, height);
    }

    /**
     * 设置Dialog窗口width，height
     * @param width int
     * @param height int
     */
    protected void setDialogWidthAndHeight(int width, int height) {
        if (getDialog() != null && getDialog().getWindow()!=null) {
            getDialog().getWindow().setLayout(width, height);
        }
    }

    /**
     * 设置窗口转场动画
     * @return int
     */
    protected int setWindowAnimationsStyle() {
        return 0;
    }

    /**
     * 设置弹出框样式
     * @return int
     */
    protected int setDialogStyle() {
        return 0;
    }


    /**
     * 显示Dialog
     * @param activity
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, String tag) {
        show(activity, null, tag);
    }

    /**
     * 显示Dialog
     * @param activity
     * @param bundle   要传递给Dialog的Bundle对象
     * @param tag      设置一个标签用来标记Dialog
     */
    public void show(Activity activity, Bundle bundle, String tag) {
        if (activity == null || isShowing())return;
        FragmentTransaction mTransaction = activity.getFragmentManager().beginTransaction();
        Fragment mFragment = activity.getFragmentManager().findFragmentByTag(tag);
        if (mFragment != null) {
            mTransaction.remove(mFragment);
        }
        if (bundle != null) {
            setArguments(bundle);
        }
        show(mTransaction, tag);
    }

    /**
     * 是否显示
     * @return false:isHidden  true:isShowing
     */
    public boolean isShowing() {
        if (this.getDialog() != null) {
            return this.getDialog().isShowing();
        } else {
            return false;
        }
    }
}
